package fr.ggautier.recettes.spi.es;

import com.google.inject.Inject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.inject.Singleton;

import fr.ggautier.recettes.spi.dw.ElasticsearchConfiguration;
import fr.ggautier.recettes.spi.dw.RecipesConfiguration;

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

    synchronized RestHighLevelClient getClient() {
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
