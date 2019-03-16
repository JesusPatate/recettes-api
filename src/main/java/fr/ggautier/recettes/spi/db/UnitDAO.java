package fr.ggautier.recettes.spi.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.ggautier.recettes.domain.Unit;

/**
 * Allows to manage units in the database.
 */
public class UnitDAO extends AbstractDAO<Unit>{

    /**
     * Creates a new DAO.
     *
     * @param sessionFactory
     *         A session provider
     */
    @Inject
    UnitDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Set<Unit> getAll() {
        final Criteria criteria = this.criteria();
        final List<Unit> results = this.list(criteria);

        return new HashSet<>(results);
    }

    public Optional<Unit> get(final int id) {
        final Criteria criteria = this.criteria()
                .add(Restrictions.eq("id", id));

        return Optional.ofNullable(this.uniqueResult(criteria));
    }
}
