package fr.ggautier.recettes.application.domain;

import java.util.Optional;
import java.util.Set;

/**
 * Repository of units.
 */
public interface UnitRepository {

    /**
     * Retrieves all units.
     */
    Set<Unit> getAll();

    /**
     * Retrieves a unit from its identifier.
     *
     * @param id
     *         The identifier of the unit
     */
    Optional<? extends Unit> get(int id);
}
