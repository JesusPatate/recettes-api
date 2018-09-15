package fr.ggautier.recettes.core.domain;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * An ingredient required in a recipe.
 */
@Embeddable
@SuppressWarnings("WeakerAccess")
public class Ingredient {

    /**
     * Allows to build new {@link Ingredient} instances.
     */
    public static class Builder {

        private String name = null;
        private Integer amount = null;
        private Unit unit = null;

        /**
         * Sets the name of the ingredient.
         */
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the amount required in the recipe.
         */
        public Builder setAmount(final int amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Sets the unit in which the amount is expressed.
         */
        public Builder setUnit(final Unit unit) {
            this.unit = unit;
            return this;
        }

        /**
         * Returns the new instance.
         */
        public Ingredient build() {
            // TODO: validate fields before building the new instance
            final Ingredient result = new Ingredient(this);
            this.clear();
            return result;
        }

        /**
         * Resets all the builder's fields.
         */
        private void clear() {
            this.name = null;
            this.amount = null;
            this.unit = null;
        }
    }

    /**
     * The name of the ingredient.
     */
    @NotBlank
    String name;

    /**
     * The amount of the ingredient required for the recipe.
     */
    @Min(1)
    Integer amount;

    /**
     * The unit in which the amount is expressed.
     */
    @ManyToOne
    @JoinColumn(name = "unit_id",
            foreignKey = @ForeignKey(name = "ingredient_unit_id_fkey")
    )
    Unit unit;

    /**
     * No-arg constructor.
     *
     * <p>
     * Required by Hibernate.
     * </p>
     */
    @SuppressWarnings("unused")
    Ingredient() {

    }

    /**
     * Creates a new {@link Ingredient} instance from a builder.
     *
     * @param builder The builder used to build the instance
     */
    @SuppressWarnings("unused")
    private Ingredient(final Builder builder) {
        this.name = builder.name;
        this.amount = builder.amount;
        this.unit = builder.unit;
    }

    public String getName() {
        return this.name;
    }

    @SuppressWarnings("unused")
    void setName(final String name) {
        this.name = name;
    }

    public Optional<Integer> getAmount() {
        return Optional.ofNullable(this.amount);
    }

    @SuppressWarnings("unused")
    void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public Optional<Unit> getUnit() {
        return Optional.ofNullable(this.unit);
    }

    @SuppressWarnings("unused")
    void setUnit(final Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (!(object instanceof Ingredient)) {
            return false;
        }

        final Ingredient other = (Ingredient) object;

        if (!Objects.equals(this.name, other.name)) {
            return false;
        }

        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }

        return Objects.equals(this.unit, other.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.amount, this.unit);
    }

    @Override
    public String toString() {
        return "HibernateIngredient {" +
                "name='" + this.name + "', " +
                "amount=" + this.amount + ", " +
                "unit='" + this.unit + "'}";
    }
}
