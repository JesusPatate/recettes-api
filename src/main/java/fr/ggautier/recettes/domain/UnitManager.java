package fr.ggautier.recettes.domain;

import com.google.inject.Inject;

import java.util.Set;

import fr.ggautier.arch.annotations.Port;

@Port(type = Port.Type.API)
public class UnitManager {

    private final Units units;

    @Inject
    public UnitManager(final Units units) {
        this.units = units;
    }

    public final Set<Unit> getAll() {
        return this.units.getAll();
    }
}
