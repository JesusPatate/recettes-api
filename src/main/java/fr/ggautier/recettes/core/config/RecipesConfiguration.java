package fr.ggautier.recettes.core.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayFactory;

/**
 * La configuration de l'application.
 */
public class RecipesConfiguration extends Configuration {

    @NotNull
    @Valid
    private final DataSourceFactory database;

    @NotNull
    @Valid
    private final FlywayFactory flyway;

    @Valid
    private final ElasticsearchConfiguration elasticsearch;

    /**
     * Instancie une nouvelle configuration.
     *
     * @param database Les paramètres liés à la base de données
     * @param flyway Les paramètres liés à Flyway
     */
    public RecipesConfiguration(
            @JsonProperty("database") final DataSourceFactory database,
            @JsonProperty("flyway") final FlywayFactory flyway,
            @JsonProperty("elasticsearch") final ElasticsearchConfiguration elasticsearch) {

        this.database = database != null ? database : new DataSourceFactory();
        this.flyway = flyway != null ? flyway : new FlywayFactory();
        this.elasticsearch = elasticsearch;
    }

    public DataSourceFactory getDataSourceFactory() {
        return this.database;
    }

    public FlywayFactory getFlywayFactory() {
        return this.flyway;
    }

    public ElasticsearchConfiguration getElasticsearch() {
        return elasticsearch;
    }
}
