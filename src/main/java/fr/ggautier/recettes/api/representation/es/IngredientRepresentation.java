package fr.ggautier.recettes.api.representation.es;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TODO Javadoc
 */
class IngredientRepresentation {

    @JsonProperty
    private final String name;

    @JsonProperty
    private final Integer amount;

    @JsonProperty
    private final Integer unitId;

    IngredientRepresentation(
            @JsonProperty("name") final String name,
            @JsonProperty("amount") final Integer amount,
            @JsonProperty("unitId") final Integer unitId) {

        this.name = name;
        this.amount = amount;
        this.unitId = unitId;
    }

    String getName() {
        return name;
    }

    Integer getAmount() {
        return amount;
    }

    Integer getUnitId() {
        return unitId;
    }
}
