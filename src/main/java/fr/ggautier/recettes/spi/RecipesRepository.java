package fr.ggautier.recettes.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.arch.annotations.Repository;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.Recipes;
import fr.ggautier.recettes.spi.db.RecipeDAO;
import fr.ggautier.recettes.spi.es.RecipeIndexer;

/**
 * TODO Javadoc
 */
@Adapter
@Repository
@Singleton
public class RecipesRepository implements Recipes {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipesRepository.class);

    private final RecipeDAO dao;

    private final RecipeIndexer indexer;

    @Inject
    public RecipesRepository(final RecipeDAO dao, final RecipeIndexer indexer) {
        this.dao = dao;
        this.indexer = indexer;
    }

    @Override
    public List<Recipe> getAll() {
        return this.dao.getAllRecipes();
    }

    @Override
    public void store(final Recipe recipe) {
        this.dao.store(recipe);

        try {
            this.indexer.store(recipe);
        } catch (final Exception exception) {
            LOGGER.error("An error occurred while indexing the recipe {}",
                    recipe.getId(), exception);
        }
    }

    @Override
    public boolean remove(UUID id) {
        final boolean deleted = this.dao.delete(id);
        this.indexer.delete(id.toString());
        return deleted;
    }

    @Override
    public List<Recipe> search(final String value) {
        final List<Recipe> results = this.indexer.search(value);
        return results;
    }
}
