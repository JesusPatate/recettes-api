package fr.ggautier.recettes.core.es;

import javax.inject.Singleton;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import io.dropwizard.lifecycle.Managed;

/**
 * TODO Javadoc
 */
@Singleton
public class ESClientManager implements Managed {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientManager.class);

    private final ESClientProvider provider;

    private RestHighLevelClient client = null;

    @Inject
    public ESClientManager(final ESClientProvider provider) {
        this.provider = provider;
    }

    @Override
    public void start() {
        LOGGER.info("Creating Elasticsearch client");
        this.client = provider.getClient();
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("Shutting down Elasticsearch client");
        this.client.close();
    }
}
