package fr.ggautier.recettes.application.db;

import javax.inject.Inject;

import fr.ggautier.recettes.application.domain.Ingredient;
import fr.ggautier.recettes.application.domain.IngredientFactory;
import fr.ggautier.recettes.application.domain.Unit;

/**
 * TODO Javadoc
 */
public class HibernateIngredientFactory implements IngredientFactory {

    private final HibernateUnitDAO unitRepository;

    @Inject
    public HibernateIngredientFactory(final HibernateUnitDAO unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public Ingredient build(final String name) {
        return new HibernateIngredient(name);
    }

    @Override
    public Ingredient build(final String name, final int amount) {
        return new HibernateIngredient(name, amount);
    }

    @Override
    public Ingredient build(final String name, final int amount, final Unit unit) {
        final HibernateUnit hibernateUnit = this.getHibernateUnit(unit);
        return new HibernateIngredient(name, amount, hibernateUnit);
    }

    /**
     * Returns the representation in the database of a unit.
     *
     * @param unit
     *         The unit we want the representation in the database
     */
    private HibernateUnit getHibernateUnit(final Unit unit) {
        final HibernateUnit hibernateUnit;

        if (unit instanceof HibernateUnit) {
            hibernateUnit = (HibernateUnit) unit;
        } else {
            hibernateUnit = this.unitRepository.get(unit.getId()).orElseThrow(() ->
                    new IllegalArgumentException("Unit not found (" + unit.getName() + ")"));
        }

        return hibernateUnit;
    }
}
