package fr.ggautier.recettes.spi.es;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.spi.db.UnitDAO;

/**
 * Allows to convert an {@link Ingredient} into a {@link IngredientRepresentation}.
 */
class IngredientMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientMapper.class);

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
        final Integer amount = representation.getAmount();
        final Integer unitId = representation.getUnitId();

        this.builder.setName(name);

        if (amount != null) {
            this.builder.setAmount(amount);
        }

        Unit unit = null;

        if (unitId != null) {
            try {
                unit = this.getUnit(unitId);
            } catch (final IllegalArgumentException exception) {
                LOGGER.error("Unknown unit id ({})", unitId, exception);
            }
        }

        this.builder.setUnit(unit);

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
