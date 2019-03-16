package fr.ggautier.recettes.api;

import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;
import java.util.stream.Collectors;

import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.domain.UnitManager;

@Path("/units")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class UnitsResource {

    private final UnitManager units;

    private final UnitMapper mapper;

    @Inject
    public UnitsResource(final UnitMapper mapper, final UnitManager units) {
        this.mapper = mapper;
        this.units = units;
    }

    @GET
    @UnitOfWork
    public Response getAll() {
        final Set<Unit> units = this.units.getAll();
        final Set<UnitRepresentation> representations = units.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toSet());

        return Response.ok(representations, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
