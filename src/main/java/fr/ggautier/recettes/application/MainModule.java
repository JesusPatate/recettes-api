package fr.ggautier.recettes.application;

import org.hibernate.SessionFactory;

import fr.ggautier.recettes.RecipesConfiguration;
import fr.ggautier.recettes.application.db.HibernateRecipeBuilder;
import fr.ggautier.recettes.application.db.HibernateRecipeDAO;
import fr.ggautier.recettes.application.db.HibernateUnitDAO;
import fr.ggautier.recettes.application.domain.RecipeBuilder;
import fr.ggautier.recettes.application.domain.RecipeRepository;
import fr.ggautier.recettes.application.domain.UnitRepository;
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
        this.bind(RecipeRepository.class).to(HibernateRecipeDAO.class);
        this.bind(UnitRepository.class).to(HibernateUnitDAO.class);
        this.bind(RecipeBuilder.class).to(HibernateRecipeBuilder.class);

        this.bind(SessionFactory.class).toInstance(this.hibernateBundle.getSessionFactory());
    }
}
