package fr.ggautier.recettes.endpoint.representation;

import fr.ggautier.recettes.application.domain.Ingredient;
import fr.ggautier.recettes.application.domain.Unit;

/**
 * Allows to convert a {@link Ingredient} into a {@link IngredientRepresentation}.
 */
public class IngredientMapper {

    /**
     * Returns a representation of an ingredient.
     *
     * @param ingredient The ingredient to convert into a representation
     */
    public IngredientRepresentation toRepresentation(final Ingredient ingredient) {
        final Integer amount = ingredient.getAmount().orElse(null);
        final Integer unitId = ingredient.getUnit().map(Unit::getId).orElse(null);

        return new IngredientRepresentation(ingredient.getName(), amount, unitId);
    }
}
