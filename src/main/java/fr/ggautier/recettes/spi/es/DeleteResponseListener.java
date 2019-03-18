package fr.ggautier.recettes.spi.es;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteResponseListener implements ActionListener<DeleteResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteResponseListener.class);

    private final DeleteRequest request;

    DeleteResponseListener(final DeleteRequest request) {
        this.request = request;
    }

    @Override
    public void onResponse(DeleteResponse response) {
        if (response.getResult() != DocWriteResponse.Result.CREATED ||
                response.getResult() != DocWriteResponse.Result.UPDATED) {

            LOGGER.warn("Failed to delete recipe {} from Elasticsearch : {}",
                    request.id(), response.toString());
        }
    }

    @Override
    public void onFailure(final Exception exception) {
        LOGGER.warn("Failed to delete recipe {} from Elasticsearch", request.id(), exception);
    }
}
