package fr.ggautier.recettes;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import fr.ggautier.recettes.application.MainModule;
import fr.ggautier.recettes.application.db.HibernateIngredient;
import fr.ggautier.recettes.application.db.HibernateRecipe;
import fr.ggautier.recettes.application.db.HibernateUnit;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

/**
 * Application's entry point.
 */
public final class RecipesApplication extends Application<RecipesConfiguration> {

    @Override
    public String getName() {
        return "Recipes";
    }

    @Override
    public void initialize(final Bootstrap<RecipesConfiguration> bootstrap) {
        final HibernateBundle<RecipesConfiguration> hibernateBundle =
                new HibernateBundle<RecipesConfiguration>(
                        HibernateRecipe.class,
                        HibernateIngredient.class,
                        HibernateUnit.class) {

                    @Override
                    public PooledDataSourceFactory getDataSourceFactory(final RecipesConfiguration configuration) {
                        return configuration.getDataSourceFactory();
                    }
                };

        final FlywayBundle<RecipesConfiguration> flywayBundle = new FlywayBundle<RecipesConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(final RecipesConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }

            @Override
            public FlywayFactory getFlywayFactory(final RecipesConfiguration configuration) {
                return configuration.getFlywayFactory();
            }
        };

        final GuiceBundle<Configuration> guiceBundle = GuiceBundle.builder()
                .enableAutoConfig(RecipesApplication.class.getPackage().getName())
                .modules(new MainModule(hibernateBundle))
                .build();

        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(flywayBundle);
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(final RecipesConfiguration configuration, final Environment environment) throws Exception {
        this.configureCors(environment);
    }

    public static void main(final String[] args) throws Exception {
        new RecipesApplication().run(args);
    }

    private void configureCors(final Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
