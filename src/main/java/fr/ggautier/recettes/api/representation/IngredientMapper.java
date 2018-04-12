package fr.ggautier.recettes.api.representation;

import javax.inject.Inject;

import fr.ggautier.recettes.core.db.UnitDAO;
import fr.ggautier.recettes.core.domain.Ingredient;
import fr.ggautier.recettes.core.domain.Unit;

/**
 * Allows to convert a {@link Ingredient} into a {@link IngredientRepresentation}.
 */
class IngredientMapper {

    private final Ingredient.Builder builder;
    private final UnitDAO unitDAO;

    @Inject
    IngredientMapper(final Ingredient.Builder builder, final UnitDAO unitDAO) {
        this.builder = builder;
        this.unitDAO = unitDAO;
    }

    /**
     * Builds a representation of an ingredient.
     *
     * @param ingredient The ingredient to convert into a representation
     */
    IngredientRepresentation toRepresentation(final Ingredient ingredient) {
        final Integer amount = ingredient.getAmount().orElse(null);
        final Integer unitId = ingredient.getUnit().map(Unit::getId).orElse(null);

        return new IngredientRepresentation(ingredient.getName(), amount, unitId);
    }

    /**
     * Builds an ingredient from its representation.
     *
     * @param representation The representation of the ingredient
     */
    Ingredient fromRepresentation(final IngredientRepresentation representation) {
        final String name = representation.getName();

        this.builder.setName(name);

        representation.getAmount().ifPresent(builder::setAmount);
        representation.getUnitId().map(this::getUnit).ifPresent(builder::setUnit);

        return this.builder.build();
    }

    /**
     * Retrieves a unit from its identifier.
     *
     * @param id The identifier of the unit
     */
    private Unit getUnit(final int id) {
        return this.unitDAO.get(id).orElseThrow(() ->
                new IllegalArgumentException("Unit not found (" + id + ")")
        );
    }
}
