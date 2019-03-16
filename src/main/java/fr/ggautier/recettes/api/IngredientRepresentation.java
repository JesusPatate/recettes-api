package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Optional;

import fr.ggautier.recettes.domain.IngredientDto;

/**
 * A representation of an ingredient used in a recipe.
 */
class IngredientRepresentation implements IngredientDto {

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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Optional<Integer> getAmount() {
        return Optional.ofNullable(this.amount);
    }

    @Override
    public Optional<Integer> getUnitId() {
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
