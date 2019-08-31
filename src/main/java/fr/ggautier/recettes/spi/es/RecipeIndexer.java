package fr.ggautier.recettes.spi.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.spi.exceptions.InternalErrorException;

/**
 * Provides full-text search on recipes.
 */
public class RecipeIndexer {

    private final ESClientProvider esClientProvider;

    private final RecipeMapper recipeMapper;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private RestHighLevelClient esClient = null;

    @Inject
    public RecipeIndexer(final ESClientProvider provider, final RecipeMapper recipeMapper) {
        esClientProvider = provider;
        this.recipeMapper = recipeMapper;
    }

    public void store(final Recipe recipe) {
        final RestHighLevelClient client = this.getEsClient();
        final IndexRequest request = this.buildIndexRequest(recipe);
        client.indexAsync(request, RequestOptions.DEFAULT, new IndexResponseListener(request));
    }

    public void delete(final String id) {
        final RestHighLevelClient client = this.getEsClient();
        final DeleteRequest request = new DeleteRequest("recipes", "recipe", id);
        client.deleteAsync(request, RequestOptions.DEFAULT, new DeleteResponseListener(request));
    }

    public List<Recipe> search(final String value) {
        final RestHighLevelClient client = this.getEsClient();
        final SearchRequest request = this.buildSearchRequest(value);
        final SearchResponse response;

        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (final IOException exception) {
            throw new InternalErrorException("Failed to request Elasticsearch", exception);
        }

        if (response.status() != RestStatus.OK) {
            throw new ElasticsearchException("Unexpected response from Elasticsearch : " + response.toString());
        }

        final List<Recipe> recipes = extractResults(response);
        return recipes;
    }

    private synchronized RestHighLevelClient getEsClient() {
        if (this.esClient == null) {
            this.esClient = this.esClientProvider.getClient();
        }

        return this.esClient;
    }

    private IndexRequest buildIndexRequest(Recipe recipe) {
        final String json = this.toJson(recipe);
        final String id = recipe.getId().toString();
        final IndexRequest request = new IndexRequest("recipes", "recipe", id)
                .source(json, XContentType.JSON);

        return request;
    }

    private SearchRequest buildSearchRequest(String value) {
        final SearchRequest request = new SearchRequest("recipes");
        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchQuery("title_ingredients", value));

        request.source(sourceBuilder);

        return request;
    }

    private List<Recipe> extractResults(final SearchResponse response) {
        final SearchHits hits = response.getHits();
        final List<Recipe> recipes = new ArrayList<>();

        for (final SearchHit hit : hits.getHits()) {
            final String id = hit.getId();
            final String source = hit.getSourceAsString();
            final Recipe recipe = this.toRecipe(source, id);
            recipes.add(recipe);
        }

        return recipes;
    }

    private String toJson(final Recipe recipe) {
        final RecipeRepresentation representation = this.recipeMapper.toRepresentation(recipe);
        try {
            return this.jsonMapper.writeValueAsString(representation);
        } catch (final JsonProcessingException exception) {
            throw new InternalErrorException("Failed to serialize recipe " + recipe.getId(), exception);
        }
    }

    private Recipe toRecipe(final String json, final String id) {
        final Recipe recipe;

        try {
            final RecipeRepresentation representation;
            representation = this.jsonMapper.readValue(json, RecipeRepresentation.class);
            recipe = this.recipeMapper.toRecipe(representation, id);
        } catch (final IOException exception) {
            throw new InternalErrorException("Failed to deserialize recipe " + id, exception);
        }

        return recipe;
    }
}
