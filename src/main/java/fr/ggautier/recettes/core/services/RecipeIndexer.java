package fr.ggautier.recettes.core.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ggautier.recettes.api.representation.es.RecipeMapper;
import fr.ggautier.recettes.api.representation.es.RecipeRepresentation;
import fr.ggautier.recettes.core.domain.Recipe;
import fr.ggautier.recettes.core.es.ESClientProvider;

/**
 * TODO Javadoc
 */
public class RecipeIndexer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeIndexer.class);

    private RestHighLevelClient client;

    private final RecipeMapper recipeMapper;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Inject
    public RecipeIndexer(final ESClientProvider provider, final RecipeMapper recipeMapper) {
        this.client = provider.getClient();
        this.recipeMapper = recipeMapper;
    }

    void store(final Recipe recipe) throws IOException {
        final String json = this.toJson(recipe);
        final String id = recipe.getId().toString();
        final IndexRequest request = new IndexRequest("recipes", "recipe", id)
                .source(json, XContentType.JSON);
        final IndexResponse response = this.client.index(request, RequestOptions.DEFAULT);

        LOGGER.debug(response.toString());
    }

    List<Recipe> search(final String value) throws IOException {
        final SearchRequest request = new SearchRequest("recipes");
        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchQuery("title_ingredients", value));
        request.source(sourceBuilder);

        final SearchResponse response = this.client.search(request, RequestOptions.DEFAULT);
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

    private String toJson(final Recipe recipe) throws JsonProcessingException {
        final RecipeRepresentation representation = this.recipeMapper.toRepresentation(recipe);

        return this.jsonMapper.writeValueAsString(representation);
    }

    private Recipe toRecipe(final String json, final String id) throws IOException {
        final RecipeRepresentation representation = this.jsonMapper.readValue(json,
                RecipeRepresentation.class);
        final Recipe recipe = this.recipeMapper.toRecipe(representation, id);

        return recipe;
    }
}
