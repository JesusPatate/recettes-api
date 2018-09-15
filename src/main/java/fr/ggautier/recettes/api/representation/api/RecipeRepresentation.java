package fr.ggautier.recettes.api.representation.api;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecipeRepresentation {

    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$") // UUID v4
    @JsonProperty
    private final String id;

    @NotBlank
    @JsonProperty
    private final String title;

    @NotNull
    @JsonProperty
    private final Boolean hot;

    @NotNull
    @JsonProperty
    private final Boolean dessert;

    @NotNull
    @Min(1)
    @JsonProperty
    private final Integer servings;

    @NotEmpty
    @Valid
    @JsonProperty
    private final Set<IngredientRepresentation> ingredients = new HashSet<>();

    @NotNull
    @Min(0)
    @JsonProperty
    private final Integer preparationTime;

    @NotNull
    @Min(0)
    @JsonProperty
    private final Integer cookingTime;

    @JsonProperty
    private final String source;

    /**
     * Creates a new representation of a recipe.
     *
     * @param id a UUID that identifies the recipe
     * @param title the name of the recipe
     * @param hot whether the recipe allows to make a hot dish
     * @param dessert whether the recipe allows to make a desert
     * @param servings how many persons the recipe satisfies
     * @param preparationTime the preparation time required to make the recipe
     * @param cookingTime the cooking time required to make the recipe
     * @param ingredients the ingredients required in the recipe
     * @param source where the recipe was found (book title, URL...)
     */
    RecipeRepresentation(
            @JsonProperty("id") final String id,
            @JsonProperty("title") final String title,
            @JsonProperty("hot") final Boolean hot,
            @JsonProperty("dessert") final Boolean dessert,
            @JsonProperty("servings") final Integer servings,
            @JsonProperty("preparationTime") final Integer preparationTime,
            @JsonProperty("cookingTime") final Integer cookingTime,
            @JsonProperty("ingredients") final Set<IngredientRepresentation> ingredients,
            @JsonProperty("source") final String source) {

        this.id = id != null ? id.trim() : null;
        this.title = title != null ? title.trim() : null;
        this.hot = hot;
        this.dessert = dessert;
        this.servings = servings;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.source = source != null ? source.trim() : null;

        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
    }

    Optional<String> getId() {
        return Optional.ofNullable(this.id);
    }

    String getTitle() {
        return this.title;
    }

    Boolean isHot() {
        return this.hot;
    }

    Boolean isDessert() {
        return this.dessert;
    }

    Integer getServings() {
        return this.servings;
    }

    Set<IngredientRepresentation> getIngredients() {
        return this.ingredients;
    }

    Integer getPreparationTime() {
        return this.preparationTime;
    }

    Integer getCookingTime() {
        return this.cookingTime;
    }

    Optional<String> getSource() {
        return Optional.ofNullable(this.source);
    }
}
