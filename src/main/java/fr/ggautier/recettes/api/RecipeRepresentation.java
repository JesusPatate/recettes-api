package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import fr.ggautier.recettes.domain.RecipeDto;

public class RecipeRepresentation implements RecipeDto {

    @SuppressWarnings("UnusedReturnValue")
    public static class Builder {

        private String id = null;
        private String title = null;
        private Boolean hot = null;
        private Boolean dessert = null;
        private Integer servings = null;
        private Set<IngredientRepresentation> ingredients = new HashSet<>();
        private Integer preparationTime = null;
        private Integer cookingTime = null;
        private String source = null;

        public Builder setId(final String id) {
            this.id = id;
            return this;
        }

        Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        Builder setHot(final boolean hot) {
            this.hot = hot;
            return this;
        }

        Builder setDessert(final boolean dessert) {
            this.dessert = dessert;
            return this;
        }

        Builder setServings(final int servings) {
            this.servings = servings;
            return this;
        }

        Builder setIngredient(final IngredientRepresentation ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        Builder setPreparationTime(final int preparationTime) {
            this.preparationTime = preparationTime;
            return this;
        }

        Builder setCookingTime(final int cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        Builder setSource(final String source) {
            this.source = source;
            return this;
        }

        public RecipeRepresentation build() {
            return new RecipeRepresentation(this);
        }
    }

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
    @SuppressWarnings("unused") // Required for Jackson
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

    private RecipeRepresentation(final Builder builder) {
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

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Boolean isHot() {
        return this.hot;
    }

    @Override
    public Boolean isDessert() {
        return this.dessert;
    }

    @Override
    public Integer getServings() {
        return this.servings;
    }

    @Override
    public Set<IngredientRepresentation> getIngredients() {
        return this.ingredients;
    }

    @Override
    public Integer getPreparationTime() {
        return this.preparationTime;
    }

    @Override
    public Integer getCookingTime() {
        return this.cookingTime;
    }

    @Override
    public Optional<String> getSource() {
        return Optional.ofNullable(this.source);
    }
}
