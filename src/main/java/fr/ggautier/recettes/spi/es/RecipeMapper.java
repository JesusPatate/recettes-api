package fr.ggautier.recettes.spi.es;

import javax.inject.Inject;
import java.util.UUID;

import fr.ggautier.recettes.domain.Recipe;

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

    RecipeRepresentation toRepresentation(final Recipe recipe) {
        final RecipeRepresentation.Builder builder = new RecipeRepresentation.Builder();

        builder.setTitle(recipe.getTitle())
                .setHot(recipe.isForAHotDish())
                .setDessert(recipe.isForADessert())
                .setPreparationTime(recipe.getPreparationTime())
                .setCookingTime(recipe.getCookingTime())
                .setServings(recipe.getServings())
                .setSource(recipe.getSource().orElse(null));

        recipe.getIngredients().stream()
                .map(this.ingredientMapper::toRepresentation)
                .forEach(builder::addIngredient);

        final RecipeRepresentation representation = builder.build();
        return representation;
    }

    Recipe toRecipe(final RecipeRepresentation representation, final String id) {
        this.builder
                .setId(UUID.fromString(id))
                .setTitle(representation.getTitle())
                .isHot(representation.getHot())
                .isADessert(representation.getDessert())
                .setPreparationTime(representation.getPreparationTime())
                .setCookingTime(representation.getCookingTime())
                .setServings(representation.getServings())
                .setSource(representation.getSource());

        representation.getIngredients()
                .stream()
                .map(this.ingredientMapper::fromRepresentation)
                .forEach(builder::setIngredient);

        return builder.build();
    }
}
