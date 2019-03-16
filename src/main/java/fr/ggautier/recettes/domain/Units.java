package fr.ggautier.recettes.domain;

import java.util.Optional;
import java.util.Set;

import fr.ggautier.arch.annotations.Port;

/**
 * A collection of all available units.
 */
@Port(type = Port.Type.SPI)
public interface Units {

    /**
     * Returns all available units.
     */
    Set<Unit> getAll();

    /**
     * Retrieves a unit from its identifier.
     *
     * @param id The identifier of the sought unit
     */
    Optional<Unit> get(final int id);

    /**
     * Adds or updates a unit in the collection.
     *
     * @param unit The new or updated unit
     */
    void store(Unit unit);
}
