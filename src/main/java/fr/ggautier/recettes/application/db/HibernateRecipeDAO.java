package fr.ggautier.recettes.application.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import fr.ggautier.recettes.application.domain.Recipe;
import fr.ggautier.recettes.application.domain.RecipeRepository;
import io.dropwizard.hibernate.AbstractDAO;

/**
 * TODO Javadoc
 */
public class HibernateRecipeDAO extends AbstractDAO<HibernateRecipe> implements RecipeRepository {

    /**
     * Creates a new DAO.
     *
     * @param sessionFactory
     *         A session provider
     */
    @Inject
    public HibernateRecipeDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        final Criteria criteria = this.criteria();
        final List<HibernateRecipe> hibernateRecipes = this.list(criteria);

        return new ArrayList<>(hibernateRecipes);
    }

    @Override
    public void store(final Recipe recipe) {
        this.currentSession().save(recipe);
    }

    @Override
    public boolean delete(final UUID id) {
        final HibernateRecipe recipe = this.currentSession().find(HibernateRecipe.class, id);
        this.currentSession().delete(recipe);
        return true;
    }
}
