package fr.ggautier.recettes.application.db;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import fr.ggautier.recettes.application.domain.Ingredient;
import fr.ggautier.recettes.application.domain.Unit;

@Entity
@Table(name = "ingredient")
@IdClass(HibernateIngredient.HibernateIngredientPK.class)
@SuppressWarnings("WeakerAccess")
public class HibernateIngredient implements Ingredient {

    /**
     * @linkplain http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#identifiers-composite-nonaggregated
     */
    @SuppressWarnings("unused")
    public static class HibernateIngredientPK implements Serializable {

        HibernateRecipe recipe;

        String name;

        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }

            if (!(object instanceof HibernateIngredientPK)) {
                return false;
            }

            final HibernateIngredientPK other = (HibernateIngredientPK) object;

            return Objects.equals(this.recipe, other.recipe) &&
                    Objects.equals(this.name, other.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.recipe, this.name);
        }
    }

    /**
     * The recipe for which the ingredient is needed.
     */
    @Id
    @ManyToOne
    HibernateRecipe recipe;

    /**
     * s
     * The name of the ingredient.
     */
    @Column
    @Id
    @NotEmpty
    String name;

    /**
     * The amount of the ingredient required for the recipe.
     */
    @Column
    @Min(1)
    Integer amount;

    /**
     * The unit in which the amount is expressed.
     */
    @ManyToOne
    @JoinColumn(name = "unit_id",
            foreignKey = @ForeignKey(name = "ingredient_unit_id_fkey")
    )
    HibernateUnit unit;

    /**
     * No-arg constructor.
     *
     * <p>
     * Required by Hibernate.
     * </p>
     */
    @SuppressWarnings("unused")
    HibernateIngredient() {

    }

    /**
     * Creates a new {@link HibernateIngredient} instance with no unit nor amount.
     *
     * @param name
     *         The name of the ingredient
     */
    @SuppressWarnings("unused")
    protected HibernateIngredient(final String name) {
        this.name = name;
    }

    /**
     * Creates a new {@link HibernateIngredient} instance with no unit.
     *
     * @param name
     *         The name of the ingredient
     * @param amount
     *         The amount of the ingredient required for the recipe
     */
    @SuppressWarnings("unused")
    protected HibernateIngredient(final String name, final Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Creates a new {@link HibernateIngredient} instance.
     *
     * @param name
     *         The name of the ingredient
     * @param amount
     *         The amount of the ingredient required for the recipe
     * @param unit
     *         The unit in which the amount is expressed
     */
    @SuppressWarnings("unused")
    protected HibernateIngredient(final String name, final Integer amount, final HibernateUnit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    HibernateRecipe getRecipe() {
        return this.recipe;
    }

    void setRecipe(final HibernateRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("unused")
    void setName(final String name) {
        this.name = name;
    }

    @Override
    public Optional<Integer> getAmount() {
        return Optional.ofNullable(this.amount);
    }

    @SuppressWarnings("unused")
    void setAmount(final Integer amount) {
        this.amount = amount;
    }

    @Override
    public Optional<Unit> getUnit() {
        return Optional.ofNullable(this.unit);
    }

    @SuppressWarnings("unused")
    void setUnit(final HibernateUnit unit) {
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

        if (!(object instanceof HibernateIngredient)) {
            return false;
        }

        final HibernateIngredient other = (HibernateIngredient) object;

        if (!Objects.equals(this.recipe, other.recipe)) {
            return false;
        }

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
        return Objects.hash(this.recipe, this.name, this.amount, this.unit);
    }

    @Override
    public String toString() {
        return "HibernateIngredient {" +
                "name='" + this.name + "', " +
                "amount=" + this.amount + ", " +
                "unit='" + this.unit + "'}";
    }
}
