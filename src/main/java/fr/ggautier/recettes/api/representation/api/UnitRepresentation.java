package fr.ggautier.recettes.api.representation.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnitRepresentation {

    @JsonProperty
    private final int id;

    @JsonProperty
    private final String name;

    /**
     * Creates a new representation of a unit.
     *
     * @param id
     *         the identifier of the unit
     * @param name
     *         the name of the unit
     */
    UnitRepresentation(
            @JsonProperty("id") final int id,
            @JsonProperty("name") final String name) {

        this.id = id;
        this.name = name;
    }

    int getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }
}
