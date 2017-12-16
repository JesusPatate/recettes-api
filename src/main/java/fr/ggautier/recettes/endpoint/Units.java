package fr.ggautier.recettes.endpoint;


import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.ggautier.recettes.application.domain.Unit;
import fr.ggautier.recettes.application.domain.UnitRepository;
import fr.ggautier.recettes.endpoint.representation.UnitMapper;
import fr.ggautier.recettes.endpoint.representation.UnitRepresentation;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/units")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class Units {

    private final UnitRepository repository;

    private final UnitMapper mapper;

    /**
     * Creates a new resource.
     *
     * @param repository
     *         to retrieve units
     * @param mapper
     *         to build representations of units
     */
    @Inject
    public Units(final UnitRepository repository, final UnitMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GET
    @UnitOfWork
    public Response getAll() {
        final Set<? extends Unit> units = this.repository.getAll();
        final Set<UnitRepresentation> representations = units.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toSet());

        return Response.ok(representations, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
