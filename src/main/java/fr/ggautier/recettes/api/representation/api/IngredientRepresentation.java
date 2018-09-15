package fr.ggautier.recettes.api.representation.api;

import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A representation of an ingredient used in a recipe.
 */
class IngredientRepresentation {

    @NotBlank
    @JsonProperty
    private final String name;

    @Min(0)
    @JsonProperty
    private final Integer amount;

    @JsonProperty
    private final Integer unitId;

    /**
     * Creates a new representation of an ingredient used in a recipe.
     *
     * @param name The name of the ingredient
     * @param amount The amount used in the recipe
     * @param unitId The identifier of the unit in which the amount is expressed
     */
    IngredientRepresentation(
            @JsonProperty("name") final String name,
            @JsonProperty("amount") final Integer amount,
            @JsonProperty("unit") final Integer unitId) {

        this.name = name != null ? name.trim() : null;
        this.amount = amount;
        this.unitId = unitId;
    }

    String getName() {
        return this.name;
    }

    Optional<Integer> getAmount() {
        return Optional.ofNullable(this.amount);
    }

    Optional<Integer> getUnitId() {
        return Optional.ofNullable(this.unitId);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof IngredientRepresentation)) {
            return false;
        }

        final IngredientRepresentation other = (IngredientRepresentation) object;

        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
