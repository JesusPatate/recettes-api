package fr.ggautier.recettes.application.domain;

import java.util.List;
import java.util.UUID;

/**
 * A repository of recipes.
 */
public interface RecipeRepository {
    /**
     * Returns all the recipes in the repository.
     */
    List<Recipe> getAllRecipes();

    /**
     * Inserts or updates a recipe in the repository.
     *
     * @param recipe
     *         The recipe to store in the repository
     */
    void store(Recipe recipe);

    /**
     * Removes a recipe from the repository.
     *
     * @param id
     *         The identifier of the recipe
     */
    boolean delete(UUID id);
}
