package topicosAlbum.resource;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.GeneroDTO;
import topicosAlbum.service.GeneroService;

@Path("/generos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GeneroResource {

    private static final Logger LOG = Logger.getLogger(GeneroResource.class);

    @Inject
    GeneroService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() {
        LOG.info(">>> [GeneroResource] GET /generos chamado para listar todos os gêneros");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        LOG.info(">>> [GeneroResource] GET /generos/{id} chamado para buscar gênero por id");   
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/{nomeGenero}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByNome(@PathParam("nomeGenero") String nomeGenero) {
        LOG.info(">>> [GeneroResource] GET /generos/search/{nomeGenero} chamado para buscar gênero por nome");
        return Response.ok(service.findByNome(nomeGenero)).build();
    }

    @GET
    @Path("/album/{idAlbum}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbum(@PathParam("idAlbum") Long idAlbum) {
        LOG.info(">>> [GeneroResource] GET /generos/album/{idAlbum} chamado para buscar gêneros por álbum");
        return Response.ok(service.findByAlbum(idAlbum)).build();
    } /*verificar */


    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid GeneroDTO dto) {
        LOG.info(">>> [GeneroResource] POST /generos criando novo gênero");
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response update(@PathParam("id") Long id, @Valid GeneroDTO dto) {
        LOG.info(">>> [GeneroResource] PUT /generos/{id} atualizando gênero com id: ");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [GeneroResource] DELETE /generos/{id} apagando gênero com id: ");
        service.delete(id);
        return Response.noContent().build();
    }
}
