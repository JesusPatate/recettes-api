package fr.ggautier.recettes.core.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import fr.ggautier.recettes.core.domain.Recipe;
import io.dropwizard.hibernate.AbstractDAO;

/**
 * Allows to manage recipes in the database.
 */
public class RecipeDAO extends AbstractDAO<Recipe> {

    /**
     * Creates a new DAO.
     *
     * @param sessionFactory
     *         A session provider
     */
    @Inject
    public RecipeDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Recipe> getAllRecipes() {
        final Criteria criteria = this.criteria();
        final List<Recipe> Recipes = this.list(criteria);

        return new ArrayList<>(Recipes);
    }

    public void store(final Recipe recipe) {
        this.currentSession().saveOrUpdate(recipe);
    }

    public boolean delete(final UUID id) {
        final Recipe recipe = this.currentSession().find(Recipe.class, id);
        this.currentSession().delete(recipe);
        return true;
    }
}
