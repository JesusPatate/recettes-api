package fr.ggautier.recettes.domain;

import java.util.List;
import java.util.UUID;

import fr.ggautier.arch.annotations.Port;

/**
 * A collection of all saved recipes.
 */
@Port(type = Port.Type.SPI)
public interface Recipes {

    /**
     * Returns all recipes of the collection.
     */
    List<Recipe> getAll();

    /**
     * Searches for a recipe that matches a given term.
     *
     * @param value The term to be found
     */
    List<Recipe> search(final String value);

    /***
     * Adds or updates a recipe in the collection.
     *
     * @param recipe The new or updated recipe
     */
    void store(Recipe recipe);

    /**
     * Removes a recipe from the collection.
     *
     * @param id The identifier of the recipe to be removed
     */
    boolean remove(UUID id);
}
