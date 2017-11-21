package fr.ggautier.recettes.application.domain;

import javax.inject.Inject;

/**
 * TODO Javadoc
 */
public class RecipeFactory {

    private final RecipeBuilder builder;

    @Inject
    public RecipeFactory(final RecipeBuilder builder) {
        this.builder = builder;
    }

    public RecipeBuilder buildRecipe() {
        return this.builder;
    }
}
