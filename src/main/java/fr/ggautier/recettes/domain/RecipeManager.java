package fr.ggautier.recettes.domain;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import fr.ggautier.arch.annotations.Port;

@Port(type = Port.Type.API)
public class RecipeManager {

    private final Recipes recipes;

    private final Units units;

    @Inject
    public RecipeManager(final Recipes recipes, final Units units) {
        this.recipes = recipes;
        this.units = units;
    }

    public List<Recipe> getAll() {
        return this.recipes.getAll();
    }

    public List<Recipe> search(final String term) {
        return this.recipes.search(term);
    }

    public void store(final RecipeDto dto) {
        final Recipe recipe = this.fromDto(dto);
        this.recipes.store(recipe);
    }

    public boolean delete(final UUID id) {
        return this.recipes.remove(id);
    }

    private Recipe fromDto(final RecipeDto dto) {
        final Recipe.Builder builder = new Recipe.Builder()
                .setTitle(dto.getTitle())
                .isHot(dto.isHot())
                .isADessert(dto.isDessert())
                .setPreparationTime(dto.getPreparationTime())
                .setCookingTime(dto.getCookingTime())
                .setServings(dto.getServings());

        dto.getId()
                .map(UUID::fromString)
                .ifPresent(builder::setId);

        dto.getIngredients()
                .stream()
                .map(this::fromDto)
                .forEach(builder::setIngredient);

        dto.getSource().ifPresent(builder::setSource);

        return builder.build();
    }

    private Ingredient fromDto(final IngredientDto representation) {
        final String name = representation.getName();

        final Ingredient.Builder builder = new Ingredient.Builder().setName(name);
        representation.getAmount().ifPresent(builder::setAmount);
        representation.getUnitId().map(this::getUnit).ifPresent(builder::setUnit);
        return builder.build();
    }

    /**
     * Retrieves a unit from its identifier.
     *
     * @param id The identifier of the unit
     */
    private Unit getUnit(final int id) {
        return this.units.get(id).orElseThrow(() ->
                new IllegalArgumentException("Unit not found (" + id + ")")
        );
    }
}
