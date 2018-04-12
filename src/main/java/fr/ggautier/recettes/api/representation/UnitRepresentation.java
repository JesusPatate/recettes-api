package fr.ggautier.recettes.api.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnitRepresentation {

    private final int id;

    private final String name;

    /**
     * Creates a new representation of a unit.
     *
     * @param id
     *         the identifier of the unit
     * @param name
     *         the name of the unit
     */
    public UnitRepresentation(
            @JsonProperty("id") final int id,
            @JsonProperty("name") final String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
