package fr.ggautier.recettes.spi;

import com.google.inject.Singleton;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

import fr.ggautier.recettes.domain.Recipes;
import fr.ggautier.recettes.domain.Units;
import fr.ggautier.recettes.spi.dw.RecipesConfiguration;

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
        this.bind(Recipes.class).to(RecipesRepository.class).in(Singleton.class);
        this.bind(Units.class).to(UnitRepository.class).in(Singleton.class);
    }
}
