package fr.ggautier.recettes.core.config;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TODO Javadoc
 */
public class ElasticsearchConfiguration {

    @NotBlank(message = "Le host du serveur Elasticsearch ne doit pas être vide")
    private final String host;

    @Range(max = 65535, message = "Le port d'écoute du serveur Elasticsearch n'est pas valide")
    private final Integer port;

    public ElasticsearchConfiguration(
            @JsonProperty("host") final String host,
            @JsonProperty("port") final Integer port) {

        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
