package fr.ggautier.recettes.api;

import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.RecipeManager;

@Path("/recipes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class RecipesResource {

    private final RecipeManager recipes;

    private final RecipeMapper mapper;

    @Inject
    public RecipesResource(final RecipeManager recipes, final RecipeMapper mapper) {
        this.recipes = recipes;
        this.mapper = mapper;
    }

    @GET
    @UnitOfWork
    public Response getAll() {
        final List<Recipe> recipes = this.recipes.getAll();
        final List<RecipeRepresentation> representations = recipes.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toList());

        return Response.ok(representations, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    @PUT
    @UnitOfWork
    public Response store(@NotNull @Valid final RecipeRepresentation representation) {
        this.recipes.store(representation);

        return Response.status(Response.Status.OK)
                .entity(representation)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    @DELETE
    @Path("/{recipeId}")
    @UnitOfWork
    public Response delete(@PathParam("recipeId") final String id) {
        Response response;

        try {
            final UUID uuid = UUID.fromString(id);
            final boolean deleted = this.recipes.delete(uuid);

            if (!deleted) {
                response = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                response = Response.noContent().build();
            }
        } catch (final IllegalArgumentException exception) {
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid identifier")
                    .build();
        }

        return response;
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.TEXT_PLAIN)
    @UnitOfWork(transactional = false)
    public Response search(@QueryParam("value") final String value) {
        final List<Recipe> recipes = this.recipes.search(value);
        final List<RecipeRepresentation> representations = recipes.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toList());

        return Response.ok(representations, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
