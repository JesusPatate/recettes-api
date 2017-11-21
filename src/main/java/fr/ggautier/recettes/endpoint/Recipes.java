package fr.ggautier.recettes.endpoint;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.ggautier.recettes.application.domain.Recipe;
import fr.ggautier.recettes.application.domain.RecipeRepository;
import fr.ggautier.recettes.endpoint.representation.RecipeMapper;
import fr.ggautier.recettes.endpoint.representation.RecipeRepresentation;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/recipes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class Recipes {

    private final RecipeRepository repository;

    private final RecipeMapper mapper;

    @Inject
    public Recipes(final RecipeRepository repository, final RecipeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GET
    @UnitOfWork
    public Response getAll() {
        final Collection<Recipe> recipes = this.repository.getAllRecipes();
        final Set<RecipeRepresentation> representations = recipes.stream()
                .map(this.mapper::toRepresentation)
                .collect(Collectors.toSet());

        return Response.ok(recipes, MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @UnitOfWork
    public Response store(@NotNull @Valid final RecipeRepresentation representation) {
        final Recipe recipe = this.mapper.toRecipe(representation);
        this.repository.store(recipe);

        return Response.status(Response.Status.CREATED)
                .entity(representation)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    @DELETE
    @Path("/{recipeId}")
    @UnitOfWork
    public Response delete(
            @PathParam("recipeId")
            @Pattern(regexp = "([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})")
            final String id) {

        final boolean deleted = this.repository.delete(UUID.fromString(id));
        final Response response;

        if (!deleted) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            response = Response.noContent().build();
        }

        return response;
    }
}
