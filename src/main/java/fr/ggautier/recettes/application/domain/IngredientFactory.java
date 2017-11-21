package fr.ggautier.recettes.application.domain;

/**
 * TODO Javadoc
 */
public interface IngredientFactory {

    /**
     * Creates a new ingredient of a recipe with no unit nor amount.
     *
     * @param name
     *         The name of the ingredient
     */
    Ingredient build(String name);

    /**
     * Creates a new ingredient of a recipe with no unit.
     *
     * @param name
     *         The name of the ingredient
     * @param amount
     *         The amount of the ingredient required for the recipe
     */
    Ingredient build(String name, int amount);

    /**
     * Creates a new ingredient of a recipe with no unit.
     *
     * @param name
     *         The name of the ingredient
     * @param amount
     *         The amount of the ingredient required for the recipe
     * @param unit
     *         The unit in which the amount is expressed
     */
    Ingredient build(String name, int amount, Unit unit);
}
