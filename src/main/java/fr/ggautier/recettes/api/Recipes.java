package fr.ggautier.recettes.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.ggautier.recettes.core.db.RecipeDAO;
import fr.ggautier.recettes.core.domain.Recipe;
import fr.ggautier.recettes.api.representation.api.RecipeMapper;
import fr.ggautier.recettes.api.representation.api.RecipeRepresentation;
import fr.ggautier.recettes.core.services.RecipeManager;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/recipes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class Recipes {

    private final RecipeManager recipeManager;

    private final RecipeDAO dao;

    private final RecipeMapper mapper;

    @Inject
    public Recipes(final RecipeManager recipeManager, final RecipeDAO dao,
            final RecipeMapper mapper) {

        this.recipeManager = recipeManager;
        this.dao = dao;
        this.mapper = mapper;
    }

    @GET
    @UnitOfWork
    public Response getAll() {
        final List<Recipe> recipes = this.recipeManager.getAll();
        final List<RecipeRepresentation> representations = recipes.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toList());

        return Response.ok(representations, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @UnitOfWork
    public Response store(@NotNull @Valid final RecipeRepresentation representation) {
        final Recipe recipe = this.mapper.toRecipe(representation);
        this.recipeManager.store(recipe);

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
        final boolean deleted = this.dao.delete(UUID.fromString(id));
        final Response response;

        if (!deleted) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            response = Response.noContent().build();
        }

        return response;
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.TEXT_PLAIN)
    @UnitOfWork(transactional = false)
    public Response search(@QueryParam("value") final String value) {
        final List<Recipe> recipes = this.recipeManager.search(value);
        final List<RecipeRepresentation> representations = recipes.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toList());

        return Response.ok(representations, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
