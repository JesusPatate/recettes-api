package fr.ggautier.recettes.endpoint.representation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecipeRepresentation {

    @NotNull
    @Pattern(regexp = "([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})")
    private final String id;

    @NotBlank
    private final String title;

    @NotNull
    private final Boolean hot;

    @NotNull
    private final Boolean dessert;

    @NotNull
    @Min(1)
    private final Integer servings;

    @NotEmpty
    @Valid
    private final Set<IngredientRepresentation> ingredients = new HashSet<>();

    @NotNull
    @Min(0)
    private final Integer preparationTime;

    @NotNull
    @Min(0)
    private final Integer cookingTime;

    private final String source;

    /**
     * Creates a new representation of a recipe.
     *
     * @param id
     *         a UUID that identifies the recipe
     * @param title
     *         the name of the recipe
     * @param hot
     * @param dessert
     * @param servings
     *         how many persons the recipe satisfies
     * @param preparationTime
     * @param cookingTime
     * @param ingredients
     *         the ingredients used in the recipe
     * @param source
     *         where the recipe was found (book title, URL...)
     */
    public RecipeRepresentation(
            @JsonProperty("id") final String id,
            @JsonProperty("title") final String title,
            @JsonProperty("hot") final Boolean hot,
            @JsonProperty("dessert") final Boolean dessert,
            @JsonProperty("servings") final Integer servings,
            @JsonProperty("preparationTime") final Integer preparationTime,
            @JsonProperty("cookingTime") final Integer cookingTime,
            @JsonProperty("ingredients") final Set<IngredientRepresentation> ingredients,
            @JsonProperty("source") final String source) {

        this.id = id;
        this.title = title;
        this.hot = hot;
        this.dessert = dessert;
        this.servings = servings;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.source = source;

        if (this.ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
    }

    public String getId() {
        return this.id;
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

    String getSource() {
        return this.source;
    }
}
