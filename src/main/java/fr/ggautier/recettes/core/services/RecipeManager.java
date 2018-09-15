package fr.ggautier.recettes.core.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ggautier.recettes.core.db.RecipeDAO;
import fr.ggautier.recettes.core.domain.Recipe;

/**
 * TODO Javadoc
 */
@Singleton
public class RecipeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeManager.class);

    private final RecipeDAO dao;

    private final RecipeIndexer indexer;

    @Inject
    public RecipeManager(final RecipeDAO dao, final RecipeIndexer indexer) {
        this.dao = dao;
        this.indexer = indexer;
    }

    public List<Recipe> getAll() {
        return this.dao.getAllRecipes();
    }

    public Recipe store(final Recipe recipe) {
        this.dao.store(recipe);

        try {
            this.indexer.store(recipe);
        } catch (final Exception exception) {
            LOGGER.error("An error occurred while indexing the recipe {}",
                    recipe.getId(), exception);
        }

        return recipe;
    }

    public List<Recipe> search(final String value) {
        final List<Recipe> recipes = new ArrayList<>();

        try {
            final List<Recipe> results = this.indexer.search(value);
            recipes.addAll(results);
        } catch (final IOException exception) {
            LOGGER.error("An error occurred while searching for recipes with the term \"{}\"",
                    value, exception);
        }

        return recipes;
    }
}
