package fr.ggautier.recettes.api.representation;

import fr.ggautier.recettes.core.domain.Unit;

/**
 * Allows to convert a {@link Unit} into a {@link UnitRepresentation}.
 */
public class UnitMapper {

    /**
     * Returns a representation of a unit.
     *
     * @param unit
     *         The unit to convert into a representation
     */
    public UnitRepresentation toRepresentation(final Unit unit) {
        return new UnitRepresentation(unit.getId(), unit.getName());
    }
}
