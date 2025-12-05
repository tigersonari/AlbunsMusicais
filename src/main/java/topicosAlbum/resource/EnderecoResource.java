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
import topicosAlbum.dto.EnderecoDTO;
import topicosAlbum.service.EnderecoService;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    
    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    @Inject
    EnderecoService service;

    // ---------------- LISTAR POR USUÁRIO ----------------

    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorUsuario(@PathParam("idUsuario") Long idUsuario) {
        LOG.info(">>> [EnderecoResource] GET /enderecos chamado para buscar endereços por usuário");
        return Response.ok(service.findByUsuario(idUsuario)).build();
    }

    // ---------------- BUSCAR POR ID ----------------

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info(">>> [EnderecoResource] GET /enderecos chamado para buscar endereço por id");
        return Response.ok(service.findById(id)).build();
    }

    // ---------------- CRIAR ----------------

    @POST
    @RolesAllowed("USER")
    public Response incluir(@Valid EnderecoDTO dto) {
        LOG.info(">>> [EnderecoResource] POST /enderecos criando novo endereço");
        return Response
            .status(Status.CREATED)
            .entity(service.create(dto))
            .build();
    }

    // ---------------- ATUALIZAR ----------------

    @PUT
    @Path("/{id}")
    @RolesAllowed("USER")
    public Response alterar(@PathParam("id") Long id, @Valid EnderecoDTO dto) {
        LOG.info(">>> [EnderecoResource] PUT /enderecos atualizando endereço com id: ");
        service.update(id, dto);
        return Response.noContent().build();
    }

    // ---------------- REMOVER ----------------

    @DELETE
    @Path("/{id}")
    @RolesAllowed("USER")
    public Response apagar(@PathParam("id") Long id) {
        LOG.info(">>> [EnderecoResource] DELETE /enderecos apagando endereço com id: ");
        service.delete(id);
        return Response.noContent().build();
    }
}
