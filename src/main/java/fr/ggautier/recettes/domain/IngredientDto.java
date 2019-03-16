package fr.ggautier.recettes.domain;

import java.util.Optional;

public interface IngredientDto {
    String getName();

    Optional<Integer> getAmount();

    Optional<Integer> getUnitId();
}
