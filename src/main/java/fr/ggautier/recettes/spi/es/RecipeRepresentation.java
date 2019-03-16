package fr.ggautier.recettes.spi.es;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO Javadoc
 */
public class RecipeRepresentation {

    public static class Builder {

        private String title = null;
        private Boolean hot = null;
        private Boolean dessert = null;
        private Integer servings = null;
        private Integer preparationTime = null;
        private Integer cookingTime = null;
        private final Set<IngredientRepresentation> ingredients = new HashSet<>();
        private String source = null;

        Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        Builder setHot(final Boolean hot) {
            this.hot = hot;
            return this;
        }

        Builder setDessert(final Boolean dessert) {
            this.dessert = dessert;
            return this;
        }

        Builder setServings(final Integer servings) {
            this.servings = servings;
            return this;
        }

        Builder setPreparationTime(final Integer preparationTime) {
            this.preparationTime = preparationTime;
            return this;
        }

        Builder setCookingTime(final Integer cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        Builder addIngredient(final IngredientRepresentation ingredient) {
            this.ingredients.add(ingredient);
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

    @JsonProperty
    private final String title;

    @JsonProperty
    private final Boolean hot;

    @JsonProperty
    private final Boolean dessert;

    @JsonProperty
    private final Integer servings;

    @JsonProperty
    private final Set<IngredientRepresentation> ingredients = new HashSet<>();

    @JsonProperty
    private final Integer preparationTime;

    @JsonProperty
    private final Integer cookingTime;

    @JsonProperty
    private final String source;

    private RecipeRepresentation(final Builder builder) {
        this.title = builder.title;
        this.hot = builder.hot;
        this.dessert = builder.dessert;
        this.servings = builder.servings;
        this.preparationTime = builder.preparationTime;
        this.cookingTime = builder.cookingTime;
        this.ingredients.addAll(builder.ingredients);
        this.source = builder.source;
    }

    /**
     * @deprecated Préférer {@link Builder}.
     */
    public RecipeRepresentation(
            @JsonProperty("title") final String title,
            @JsonProperty("hot") final Boolean hot,
            @JsonProperty("dessert") final Boolean dessert,
            @JsonProperty("servings") final Integer servings,
            @JsonProperty("preparationTime") final Integer preparationTime,
            @JsonProperty("cookingTime") final Integer cookingTime,
            @JsonProperty("ingredients") final Set<IngredientRepresentation> ingredients,
            @JsonProperty("source") final String source) {

        this.title = title;
        this.hot = hot;
        this.dessert = dessert;
        this.servings = servings;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.ingredients.addAll(ingredients);
        this.source = source;
    }

    String getTitle() {
        return this.title;
    }

    Boolean getHot() {
        return this.hot;
    }

    Boolean getDessert() {
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
