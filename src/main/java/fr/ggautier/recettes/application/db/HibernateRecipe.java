package fr.ggautier.recettes.application.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import fr.ggautier.recettes.application.domain.Ingredient;
import fr.ggautier.recettes.application.domain.Recipe;

@Entity
@Table(name = "recipe")
@SuppressWarnings("WeakerAccess")
public class HibernateRecipe implements Recipe {

    /**
     * The identifier of the recipe.
     */
    @Id
    @Column
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
    @OneToMany(mappedBy = "recipe")
    @Cascade(CascadeType.ALL)
    List<HibernateIngredient> ingredients = new ArrayList<>();

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
    HibernateRecipe() {

    }

    public HibernateRecipe(final UUID id, final String title, final boolean hot, final boolean dessert,
            final Integer servings, final Integer preparationTime, final int cookingTime,
            final List<HibernateIngredient> ingredients, final String source) {

        this.id = id;
        this.title = title;
        this.hot = hot;
        this.dessert = dessert;
        this.servings = servings;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.source = source;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean isHot() {
        return this.hot;
    }

    @Override
    public boolean isDessert() {
        return this.dessert;
    }

    @Override
    public int getServings() {
        return this.servings;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(this.ingredients);
    }

    @Override
    public int getPreparationTime() {
        return this.preparationTime;
    }

    @Override
    public int getCookingTime() {
        return Optional.of(this.cookingTime).orElse(0);
    }

    @Override
    public Optional<String> getSource() {
        return Optional.ofNullable(this.source);
    }

    @Override
    public List<String> getComments() {
        return Collections.unmodifiableList(this.comments);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof HibernateRecipe)) {
            return false;
        }

        final HibernateRecipe other = (HibernateRecipe) object;

        return Objects.equals(this.id, other.id);
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
     * @param id
     *         Unique identifier
     */
    protected void setId(final UUID id) {
        this.id = id;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param title
     *         Name of the dish
     */
    protected void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param hot
     *         Is the dish hot ?
     */
    protected void setHot(final Boolean hot) {
        this.hot = hot;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param dessert
     *         Is the dish a dessert ?
     */
    protected void setDessert(final Boolean dessert) {
        this.dessert = dessert;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param servings
     *         Number of servings that the recipe provides
     */
    protected void setServings(final Integer servings) {
        this.servings = servings;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param ingredients
     *         Ingredients needed to make the recipe
     */
    protected void setIngredients(final Collection<HibernateIngredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    /**
     * Setter used by Hibernate.
     *
     * @param preparationTime
     *         Preparation time in minutes
     */
    protected void setPreparationTime(final Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param cookingTime
     *         Cooking time in minutes
     */
    protected void setCookingTime(final Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param source
     *         Where the recipe can be found ?
     */
    protected void setSource(final String source) {
        this.source = source;
    }

    /**
     * Setter used by Hibernate.
     *
     * @param comments
     *         Comments on the recipe
     */
    protected void setComments(final List<String> comments) {
        this.comments = comments;
    }
}
