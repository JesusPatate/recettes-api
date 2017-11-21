package fr.ggautier.recettes.application.db;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import fr.ggautier.recettes.application.domain.Unit;
import fr.ggautier.recettes.application.domain.UnitRepository;
import io.dropwizard.hibernate.AbstractDAO;

/**
 * TODO Javadoc
 */
public class HibernateUnitDAO extends AbstractDAO<HibernateUnit> implements UnitRepository {

    /**
     * Creates a new DAO.
     *
     * @param sessionFactory
     *         A session provider
     */
    @Inject
    HibernateUnitDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Set<Unit> getAll() {
        final Criteria criteria = this.criteria();
        final List<HibernateUnit> results = this.list(criteria);

        return new HashSet<>(results);
    }

    @Override
    public Optional<HibernateUnit> get(final int id) {
        final Criteria criteria = this.criteria()
                .add(Restrictions.eq("id", id));

        return Optional.ofNullable(this.uniqueResult(criteria));
    }
}
