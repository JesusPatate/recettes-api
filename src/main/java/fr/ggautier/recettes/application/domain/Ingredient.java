package fr.ggautier.recettes.application.domain;

import java.io.Serializable;
import java.util.Optional;

/**
 * TODO Javadoc
 */
public interface Ingredient extends Serializable {

    String getName();

    Optional<Integer> getAmount();

    Optional<Unit> getUnit();
}
