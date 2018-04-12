package fr.ggautier.recettes.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "recipe")
@SuppressWarnings("WeakerAccess")
public final class Recipe {

    public static class Builder {

        private UUID id = null;
        private String title = null;
        private Boolean hot = null;
        private Boolean dessert = null;
        private Integer preparationTime = null;
        private Integer cookingTime = null;
        private Integer servings = null;
        private String source = null;
        private List<Ingredient> ingredients = null;

        /**
         * Sets the identifier of the recipe.
         *
         * @param id The unique identifier of the recipe
         *
         * @return The builder
         */
        public Builder setId(final UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the recipe.
         *
         * @param title The name of the recipe
         *
         * @return The builder
         */
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets whether the recipe is for a hot dish.
         *
         * @param hot Whether the recipe allows to make a hot dish
         *
         * @return The builder
         */
        public Builder isHot(final boolean hot) {
            this.hot = hot;
            return this;
        }

        /**
         * Sets whether the recipe is for a dessert.
         *
         * @param dessert Whether the recipe allows to make a dessert
         *
         * @return The builder
         */
        public Builder isADessert(final boolean dessert) {
            this.dessert = dessert;
            return this;
        }

        /**
         * Sets the preparation time needed to make the recipe.
         *
         * @param preparationTime The preparation time required by the recipe
         *
         * @return The builder
         */
        public Builder setPreparationTime(final int preparationTime) {
            this.preparationTime = preparationTime;
            return this;
        }

        /**
         * Sets the cooking time needed to make the recipe.
         *
         * @param cookingTime The cooking time required by the recipe
         *
         * @return The builder
         */
        public Builder setCookingTime(final int cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        /**
         * Sets the number of servings the recipe yields.
         *
         * @param servings The number of servings the recipe yields
         *
         * @return The builder
         */
        public Builder setServings(final Integer servings) {
            this.servings = servings;
            return this;
        }

        /**
         * Sets the source from which the recipe was taken.
         *
         * @param source A title of a book, a URL of a website...
         *
         * @return The builder
         */
        public Builder setSource(final String source) {
            this.source = source;
            return this;
        }

        /**
         * Adds an ingredient required in the recipe.
         *
         * @param ingredient An ingredient required in the recipe
         *
         * @return The builder
         */
        public synchronized Builder setIngredient(final Ingredient ingredient) {
            if (this.ingredients == null) {
                this.ingredients = new ArrayList<>();
            }

            this.ingredients.add(ingredient);

            return this;
        }

        /**
         * Returns the new instance.
         */
        public Recipe build() {
            // TODO: validate fields before building the new instance
            final Recipe recipe = new Recipe(this);
            this.clear();
            return recipe;
        }

        /**
         * Resets the builder's fields.
         */
        private void clear() {
            this.title = null;
            this.hot = null;
            this.dessert = null;
            this.preparationTime = null;
            this.cookingTime = null;
            this.servings = null;
            this.source = null;
            this.ingredients = null;
        }
    }

    /**
     * The identifier of the recipe.
     */
    @Id
    @GeneratedValue
    @NotNull
    UUID id;

    /**
     * The name of the dish.
     */
    @Column
    @NotBlank
    String title;

    /**
     * Whether the dish is hot.
     */
    @Column
    @NotNull
    Boolean hot;

    /**
     * Whether the dish is a dessert.
     */
    @Column
    @NotNull
    Boolean dessert;

    /**
     * The number of servings that the recipe provides.
     */
    @NotNull
    @Min(1)
    Integer servings;

    /**
     * The ingredients that are needed to make the recipe.
     */
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "ingredient")
    Set<Ingredient> ingredients = new HashSet<>();

    /**
     * The preparation time (in minutes) of the recipe.
     */
    @Column(name = "preparation_time")
    @NotNull
    @Min(1)
    Integer preparationTime;

    /**
     * The cooking time (in minutes) of the recipe.
     */
    @Column(name = "cooking_time")
    @Min(0)
    Integer cookingTime;

    /**
     * Where the recipe can was found.
     */
    @Column
    String source;

    /**
     * Comments on the recipe.
     */
    @Column(name = "contents")
    @ElementCollection
//    @CollectionTable(name = "comment", joinColumns = @JoinColumn(name = "recipe_id"))
    @JoinTable(name = "comment", joinColumns = @JoinColumn(name = "recipe_id"))
    @Cascade(CascadeType.ALL)
    List<String> comments = new ArrayList<>();

    /**
     * No-arg constructor.
     *
     * <p>
     * Required by Hibernate.
     * </p>
     */
    @SuppressWarnings("unused")
    Recipe() {

    }

    private Recipe(final Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.hot = builder.hot;
        this.dessert = builder.dessert;
        this.servings = builder.servings;
        this.preparationTime = builder.preparationTime;
        this.cookingTime = builder.cookingTime;
        this.source = builder.source;

        this.ingredients.addAll(builder.ingredients);
    }

    /**
     * Returns the unique identifier of the recipe.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Returns the name of the recipe.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the name of the recipe.
     *
     * @param title The title of the recipe
     */
    public void setTitle(final String title) {
        Objects.requireNonNull(title);
        this.title = title;
    }

    public boolean isForAHotDish() {
        return this.hot;
    }

    /**
     * Sets whether the recipe is for a hot dish.
     *
     * @param hot Whether the recipe allows to make a hot dish
     */
    public void isForAHotDish(final boolean hot) {
        this.hot = hot;
    }

    public boolean isForADessert() {
        return this.dessert;
    }

    /**
     * Sets whether the recipe is for a dessert.
     *
     * @param dessert Whether the recipe allows to make a dessert
     */
    public void isForADessert(final boolean dessert) {
        this.dessert = dessert;
    }

    public int getServings() {
        return this.servings;
    }

    /**
     * Sets the number of servings the recipe yields.
     *
     * @param servings The number of servings the recipe yields
     */
    public void setServings(final int servings) {
        this.servings = servings;
    }

    /**
     * Returns a list of the ingredients required in the recipe.
     */
    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(new ArrayList<>(this.ingredients));
    }

    /**
     * Returns the preparation time of the recipe.
     */
    public int getPreparationTime() {
        return this.preparationTime;
    }

    /**
     * Sets the preparation time of the recipe.
     *
     * @param preparationTime The preparation time in minutes
     */
    public void setPreparationTime(final int preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * Returns the cooking time of the recipe.
     */
    public int getCookingTime() {
        return this.cookingTime;
    }

    /**
     * Sets the cooking time of the recipe.
     *
     * @param cookingTime The cooking time in minutes
     */
    public void setCookingTime(final int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Optional<String> getSource() {
        return Optional.ofNullable(this.source);
    }

    /**
     * Sets the source from which the recipe was taken.
     *
     * @param source A book title, a website URL...
     */
    public void setSource(final String source) {
        Objects.requireNonNull(source);
        this.source = source;
    }

    /**
     * Returns a list of the comments on the recipe.
     */
    public List<String> getComments() {
        return Collections.unmodifiableList(this.comments);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Recipe)) {
            return false;
        }

        final Recipe other = (Recipe) object;

        return (this.id != null) && (other.id != null) &&
                Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "HibernateRecipe {" +
                "id=" + this.id + ", " +
                "title='" + this.title + '\'' + ", " +
                "hot=" + this.hot + ", " +
                "dessert=" + this.dessert + ", " +
                "servings=" + this.servings + ", " +
                "ingredients=" + this.ingredients + ", " +
                "preparationTime=" + this.preparationTime + ", " +
                "cookingTime=" + this.cookingTime + ", " +
                "source='" + this.source + '\'' + ", " +
                "comments=" + this.comments + '}';
    }

    /**
     * Setter used by Hibernate.
     *
     * @param id Unique identifier
     */
    protected void setId(final UUID id) {
        this.id = id;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param ingredients Ingredients needed to make the recipe
     */
    protected void setIngredients(final Collection<Ingredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    /**
     * Setter used by Hibernate.
     *
     * @param comments Comments on the recipe
     */
    protected void setComments(final List<String> comments) {
        this.comments = comments;
    }
}
