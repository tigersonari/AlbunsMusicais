package topicosAlbum.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.EnderecoDTO;
import topicosAlbum.service.EnderecoService;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    @Inject
    EnderecoService service;

    private Long getUserId() {
        return Long.valueOf(jwt.getClaim("idUsuario").toString());
    }

    private boolean isAdmin() {
        return jwt.getGroups().contains("ADM");
    }

    // ----------- LISTAR ENDEREÇOS DO USUÁRIO -----------

    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorUsuario(@PathParam("idUsuario") Long idUsuario) {

        Long idUsuarioToken = getUserId();

        if (!isAdmin() && !idUsuario.equals(idUsuarioToken)) {
            return Response.status(Status.FORBIDDEN).entity("Você não pode ver endereços de outros usuários.").build();
        }

        return Response.ok(service.findByUsuario(idUsuario)).build();
    }

    // ----------- BUSCAR POR ID (ENFORCE OWNER) -----------

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {

        Long idUsuarioToken = getUserId();

        return Response.ok(service.findByIdSeguro(id, idUsuarioToken, isAdmin())).build();
    }

    // ----------- CRIAR ENDEREÇO (sempre para o usuário logado) -----------

    @POST
    @RolesAllowed("USER")
    public Response incluir(@Valid EnderecoDTO dto) {

        Long idUsuarioToken = getUserId();

        return Response.status(Status.CREATED)
                .entity(service.createParaUsuario(dto, idUsuarioToken))
                .build();
    }

    // ----------- ATUALIZAR (somente dono) -----------

    @PUT
    @Path("/{id}")
    @RolesAllowed("USER")
    public Response alterar(@PathParam("id") Long id, @Valid EnderecoDTO dto) {

        Long idUsuarioToken = getUserId();

        service.updateSeguro(id, dto, idUsuarioToken);

        return Response.noContent().build();
    }

    // ----------- REMOVER (somente dono) -----------

    @DELETE
    @Path("/{id}")
    @RolesAllowed("USER")
    public Response apagar(@PathParam("id") Long id) {

        Long idUsuarioToken = getUserId();

        service.deleteSeguro(id, idUsuarioToken);

        return Response.noContent().build();
    }
}
