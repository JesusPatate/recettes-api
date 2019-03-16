package fr.ggautier.recettes.spi;

import com.google.inject.Inject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;
import java.util.Set;

import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.domain.Units;
import fr.ggautier.recettes.spi.db.UnitDAO;

public class UnitRepository implements Units {

    private final UnitDAO dao;

    @Inject
    public UnitRepository(final UnitDAO dao) {
        this.dao = dao;
    }

    @Override
    public Set<Unit> getAll() {
        return this.dao.getAll();
    }

    @Override
    public Optional<Unit> get(int id) {
        return this.dao.get(id);
    }

    @Override
    public void store(Unit unit) {
        throw new NotImplementedException();
    }
}
