package fr.ggautier.recettes.application.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Allows to build instances of {@link Recipe}.
 */
public abstract class RecipeBuilder {

    protected UUID id = null;
    protected String title;
    protected boolean hot = false;
    protected boolean dessert = false;
    protected Integer preparationTime = null;
    protected Integer cookingTime = null;
    protected Integer servings = null;
    protected final List<Ingredient> ingredients = new ArrayList<>();
    protected String source = null;

    protected final IngredientFactory ingredientFactory;

    @Inject
    public RecipeBuilder(final IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    /**
     * Sets the identifier of the new recipe.
     *
     * @param id
     *         The identifier of the recipe
     */
    public RecipeBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the title of the new recipe.
     *
     * @param title
     *         The title of the recipe
     */
    public RecipeBuilder withTitle(final String title) {
        this.title = title;
        return this;
    }

    /**
     * States whether the new recipe is for a hot dish.
     *
     * @param hot
     *         Whether the recipe is for a hot dish
     */
    public RecipeBuilder thatIsHot(final boolean hot) {
        this.hot = hot;
        return this;
    }

    /**
     * States whether the new recipe is for a dessert.
     *
     * @param dessert
     *         Whether the recipe is for a dessert
     */
    public RecipeBuilder thatIsADessert(final boolean dessert) {
        this.dessert = dessert;
        return this;
    }

    /**
     * Sets the preparation time of the new recipe.
     *
     * @param preparationTime
     *         The preparation time of the recipe
     */
    public RecipeBuilder withPreparationTime(final int preparationTime) {
        this.preparationTime = preparationTime;
        return this;
    }

    /**
     * Sets the cooking time of the new recipe.
     *
     * @param cookingTime
     *         The cooking time of the recipe
     */
    public RecipeBuilder withCookingTime(final int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    /**
     * Sets the number of servings the new recipes provides.
     *
     * @param servings
     *         The number of servings the recipe provides
     */
    public RecipeBuilder withServings(final int servings) {
        this.servings = servings;
        return this;
    }

    /**
     * Adds an ingredient with no amount nor unit to the recipe.
     *
     * @param name
     *         The name of the ingredient
     */
    public RecipeBuilder withIngredient(final String name) {
        this.ingredients.add(this.ingredientFactory.build(name));
        return this;
    }

    /**
     * Adds an ingredient with no unit to the recipe.
     *
     * @param name
     *         The name of the ingredient
     * @param amount
     *         The amount required in the recipe
     */
    public RecipeBuilder withIngredient(final String name, final int amount) {
        this.ingredients.add(this.ingredientFactory.build(name, amount));
        return this;
    }

    /**
     * Adds an ingredient to the recipe.
     *
     * @param name
     *         The name of the ingredient
     * @param amount
     *         The amount required in the recipe
     * @param unit
     *         The unit in which the amount is expressed
     */
    public RecipeBuilder withIngredient(final String name, final int amount, final Unit unit) {
        this.ingredients.add(this.ingredientFactory.build(name, amount, unit));
        return this;
    }

    /**
     * Sets the source of the new recipe.
     *
     * @param source The source of the recipe
     */
    public RecipeBuilder withSource(final String source) {
        this.source = source;
        return this;
    }

    /**
     * Returns the new recipe.
     */
    public abstract Recipe build();
}
