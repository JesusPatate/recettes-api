package fr.ggautier.recettes.api;


import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.ggautier.recettes.api.representation.api.UnitMapper;
import fr.ggautier.recettes.api.representation.api.UnitRepresentation;
import fr.ggautier.recettes.core.db.UnitDAO;
import fr.ggautier.recettes.core.domain.Unit;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/units")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class Units {

    private final UnitDAO dao;

    private final UnitMapper mapper;

    /**
     * Creates a new resource.
     *
     * @param dao
     *         to retrieve units
     * @param mapper
     *         to build representations of units
     */
    @Inject
    public Units(final UnitDAO dao, final UnitMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GET
    @UnitOfWork
    public Response getAll() {
        final Set<Unit> units = this.dao.getAll();
        final Set<UnitRepresentation> representations = units.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toSet());

        return Response.ok(representations, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
