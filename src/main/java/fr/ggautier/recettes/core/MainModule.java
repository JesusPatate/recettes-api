package fr.ggautier.recettes.core;

import org.hibernate.SessionFactory;

import fr.ggautier.recettes.core.config.RecipesConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

/**
 * Dependency injection setup.
 */
public class MainModule extends DropwizardAwareModule<RecipesConfiguration> {

    /**
     * Bundle for the integration of Hibernate.
     */
    private final HibernateBundle hibernateBundle;

    /**
     * Instanciates a new module.
     *
     * @param hibernateBundle
     *         Bundle for the integration of Hibernate
     */
    public MainModule(final HibernateBundle hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    protected void configure() {
        this.bind(SessionFactory.class).toInstance(this.hibernateBundle.getSessionFactory());
    }
}
