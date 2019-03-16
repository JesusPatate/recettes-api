package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Unit;

/**
 * Allows to convert a {@link Unit} into a {@link UnitRepresentation}.
 */
class UnitMapper {

    /**
     * Returns a representation of a unit.
     *
     * @param unit
     *         The unit to convert into a representation
     */
    UnitRepresentation toRepresentation(final Unit unit) {
        return new UnitRepresentation(unit.getId(), unit.getName());
    }
}
