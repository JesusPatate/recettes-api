package fr.ggautier.recettes.api.representation.api;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import fr.ggautier.recettes.core.domain.Recipe;

/**
 * Allows to convert recipes into their representations and conversely.
 */
public class RecipeMapper {

    private final Recipe.Builder builder;
    private final IngredientMapper ingredientMapper;

    /**
     * Creates a new mapper.
     *
     * @param builder to build {@link Recipe} instances
     * @param ingredientMapper to convert ingredients into their representations and conversely
     */
    @Inject
    public RecipeMapper(final Recipe.Builder builder, final IngredientMapper ingredientMapper) {
        this.builder = builder;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * Builds a new recipe from a representation.
     *
     * @param representation A representation of the new recipe
     */
    public Recipe toRecipe(final RecipeRepresentation representation) {
        this.builder.setTitle(representation.getTitle())
                .isHot(representation.isHot())
                .isADessert(representation.isDessert())
                .setPreparationTime(representation.getPreparationTime())
                .setCookingTime(representation.getCookingTime())
                .setServings(representation.getServings());

        representation.getId()
                .map(UUID::fromString)
                .ifPresent(this.builder::setId);

        representation.getIngredients()
                .stream()
                .map(this.ingredientMapper::fromRepresentation)
                .forEach(builder::setIngredient);

        representation.getSource().ifPresent(this.builder::setSource);

        return builder.build();
    }

    /**
     * Returns a representation of a recipe.
     *
     * @param recipe The recipe to convert into a representation
     */
    public RecipeRepresentation toRepresentation(final Recipe recipe) {
        final String id = recipe.getId().toString();
        final String source = recipe.getSource().orElse(null);
        final Set<IngredientRepresentation> ingredients = recipe.getIngredients().stream()
                .map(this.ingredientMapper::toRepresentation)
                .collect(Collectors.toSet());

        final RecipeRepresentation representation = new RecipeRepresentation(id, recipe.getTitle(),
                recipe.isForAHotDish(), recipe.isForADessert(), recipe.getServings(), recipe.getPreparationTime(),
                recipe.getCookingTime(), ingredients, source);

        return representation;
    }
}
