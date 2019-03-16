package fr.ggautier.recettes.spi.es;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexResponseListener implements ActionListener<IndexResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexResponseListener.class);

    private final IndexRequest request;

    IndexResponseListener(final IndexRequest request) {
        this.request = request;
    }

    @Override
    public void onResponse(IndexResponse response) {
        if (response.getResult() != DocWriteResponse.Result.CREATED ||
                response.getResult() != DocWriteResponse.Result.UPDATED) {

            LOGGER.error("Failed to index recipe in Elasticsearch (request={}) : {}",
                    request.toString(), response.toString());
        }
    }

    @Override
    public void onFailure(final Exception exception) {
        LOGGER.error("Failed to index recipe {) in Elasticsearch", request.id(), exception);
    }
}
