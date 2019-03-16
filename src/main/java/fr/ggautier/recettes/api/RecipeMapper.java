package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.Unit;

/**
 * Builds representations of recipes.
 */
class RecipeMapper {

    /**
     * Returns a representation of a recipe.
     *
     * @param recipe The recipe to convert into a representation
     */
    RecipeRepresentation toRepresentation(final Recipe recipe) {
        final String id = recipe.getId().toString();

        final RecipeRepresentation.Builder builder = new RecipeRepresentation.Builder()
                .setId(id)
                .setTitle(recipe.getTitle())
                .setHot(recipe.isForAHotDish())
                .setDessert(recipe.isForADessert())
                .setServings(recipe.getServings())
                .setPreparationTime(recipe.getPreparationTime())
                .setCookingTime(recipe.getCookingTime());

        recipe.getIngredients().stream()
                .map(this::toRepresentation)
                .forEach(builder::setIngredient);

        recipe.getSource().ifPresent(builder::setSource);

        return builder.build();
    }

    /**
     * Builds a representation of an ingredient.
     *
     * @param ingredient The ingredient to convert into a representation
     */
    private IngredientRepresentation toRepresentation(final Ingredient ingredient) {
        final Integer amount = ingredient.getAmount().orElse(null);
        final Integer unitId = ingredient.getUnit().map(Unit::getId).orElse(null);
        return new IngredientRepresentation(ingredient.getName(), amount, unitId);
    }
}
