package fr.ggautier.recettes.endpoint.representation;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import fr.ggautier.recettes.application.domain.Recipe;
import fr.ggautier.recettes.application.domain.RecipeBuilder;
import fr.ggautier.recettes.application.domain.RecipeFactory;
import fr.ggautier.recettes.application.domain.Unit;
import fr.ggautier.recettes.application.domain.UnitRepository;

/**
 * Allows to convert a {@link Recipe} into a {@link RecipeRepresentation} and conversely.
 */
public class RecipeMapper {

    private final RecipeFactory factory;

    private final IngredientMapper ingredientMapper;

    private final UnitRepository unitRepository;

    /**
     * Creates a new mapper.
     *
     * @param factory
     *         a factory of recipes
     * @param ingredientMapper
     *         to convert ingredients into representations
     * @param unitRepository
     *         to retrieve units from their ids
     */
    @Inject
    public RecipeMapper(final RecipeFactory factory, final IngredientMapper ingredientMapper,
            final UnitRepository unitRepository) {

        this.factory = factory;
        this.ingredientMapper = ingredientMapper;
        this.unitRepository = unitRepository;
    }

    /**
     * Builds a new recipe from a representation.
     *
     * @param representation
     *         A representation of the new recipe
     */
    public Recipe toRecipe(final RecipeRepresentation representation) {
        final RecipeBuilder builder = this.factory.buildRecipe()
                .withId(this.getId(representation))
                .withTitle(representation.getTitle())
                .thatIsHot(representation.isHot())
                .thatIsADessert(representation.isDessert())
                .withPreparationTime(representation.getPreparationTime())
                .withCookingTime(representation.getCookingTime())
                .withServings(representation.getServings())
                .withSource(representation.getSource());

        this.addIngredients(representation, builder);

        final Recipe recipe = builder.build();

        return recipe;
    }

    /**
     * Returns a representation of a recipe.
     *
     * @param recipe
     *         The recipe to convert into a representation
     */
    public RecipeRepresentation toRepresentation(final Recipe recipe) {
        final String id = recipe.getId().toString();

        final Set<IngredientRepresentation> ingredients = recipe.getIngredients()
                .stream()
                .map(this.ingredientMapper::toRepresentation)
                .collect(Collectors.toSet());

        final String source = recipe.getSource().orElse(null);

        final RecipeRepresentation representation = new RecipeRepresentation(id, recipe.getTitle(), recipe.isHot(),
                recipe.isDessert(), recipe.getServings(), recipe.getPreparationTime(), recipe.getCookingTime(),
                ingredients, source);

        return representation;
    }

    private UUID getId(final RecipeRepresentation representation) {
        return UUID.fromString(representation.getId());
    }

    private void addIngredients(final RecipeRepresentation representation, final RecipeBuilder builder) {
        representation.getIngredients().forEach(ingredient -> {
            final String name = ingredient.getName();
            final Optional<Integer> amount = ingredient.getAmount();
            final Optional<Integer> unitId = ingredient.getUnitId();

            if (amount.isPresent()) {
                if (unitId.isPresent()) {
                    final Unit unit = this.getUnit(unitId.get());
                    builder.withIngredient(name, amount.get(), unit);
                } else {
                    builder.withIngredient(name, amount.get());
                }
            } else {
                builder.withIngredient(name);
            }
        });
    }

    private Unit getUnit(final int id) {
        return this.unitRepository.get(id).orElseThrow(() ->
                new IllegalArgumentException("Unit not found (" + id + ")")
        );
    }
}
