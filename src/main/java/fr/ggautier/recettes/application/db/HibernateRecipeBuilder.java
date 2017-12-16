package fr.ggautier.recettes.application.db;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import fr.ggautier.recettes.application.domain.Ingredient;
import fr.ggautier.recettes.application.domain.Recipe;
import fr.ggautier.recettes.application.domain.RecipeBuilder;
import fr.ggautier.recettes.application.domain.Unit;

/**
 * TODO Javadoc
 */
public class HibernateRecipeBuilder extends RecipeBuilder {

    @Inject
    public HibernateRecipeBuilder(final HibernateIngredientFactory ingredientFactory) {
        super(ingredientFactory);
    }

    @Override
    public RecipeBuilder withIngredient(final String name) {
        final Ingredient ingredient = this.ingredientFactory.build(name);
        this.ingredients.add(ingredient);
        return this;
    }

    @Override
    public RecipeBuilder withIngredient(final String name, final int amount) {
        final Ingredient ingredient = this.ingredientFactory.build(name, amount);
        this.ingredients.add(ingredient);
        return this;
    }

    @Override
    public RecipeBuilder withIngredient(final String name, final int amount, final Unit unit) {
        final Ingredient ingredient = this.ingredientFactory.build(name, amount, unit);
        this.ingredients.add(ingredient);
        return this;
    }

    @Override
    public Recipe build() {
        final List<HibernateIngredient> hibernateIngredients = this.ingredients.stream()
                .map(ingredient -> (HibernateIngredient) ingredient)
                .collect(Collectors.toList());

        final HibernateRecipe recipe = new HibernateRecipe(this.id, this.title, this.hot, this.dessert, this.servings,
                this.preparationTime, this.cookingTime, hibernateIngredients, this.source);

        hibernateIngredients.forEach(ingredient -> ingredient.setRecipe(recipe));

        this.reset();

        return recipe;
    }

    private void reset() {
        this.id = null;
        this.title = null;
        this.hot = false;
        this.dessert = false;
        this.servings = null;
        this.preparationTime = null;
        this.cookingTime = null;
        this.ingredients.clear();
        this.source = null;
    }
}
