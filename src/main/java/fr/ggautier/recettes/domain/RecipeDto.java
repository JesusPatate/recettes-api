package fr.ggautier.recettes.domain;

import java.util.Optional;
import java.util.Set;

public interface RecipeDto {

    String getId();

    String getTitle();

    Boolean isHot();

    Boolean isDessert();

    Integer getServings();

    Set<? extends IngredientDto> getIngredients();

    Integer getPreparationTime();

    Integer getCookingTime();

    Optional<String> getSource();
}
