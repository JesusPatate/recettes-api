package fr.ggautier.recettes.core.es;

import javax.inject.Singleton;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.google.inject.Inject;

import fr.ggautier.recettes.core.config.ElasticsearchConfiguration;
import fr.ggautier.recettes.core.config.RecipesConfiguration;

/**
 * TODO Javadoc
 */
@Singleton
public class ESClientProvider  {

    private static RestHighLevelClient client = null;

    private RecipesConfiguration configuration;

    @Inject
    public ESClientProvider(final RecipesConfiguration configuration) {
        this.configuration = configuration;
    }

    public synchronized RestHighLevelClient getClient() {
        if (client == null) {
            client = this.buildClient();
        }

        return client;
    }

    private synchronized RestHighLevelClient buildClient() {
        final ElasticsearchConfiguration config = this.configuration.getElasticsearch();
        final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost(config.getHost(), config.getPort())));

        return client;
    }
}
