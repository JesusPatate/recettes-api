package fr.ggautier.recettes.application.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Recipe extends Serializable {

    UUID getId();

    String getTitle();

    boolean isHot();

    boolean isDessert();

    int getServings();

    List<Ingredient> getIngredients();

    int getPreparationTime();

    int getCookingTime();

    Optional<String> getSource();

    List<String> getComments();
}
