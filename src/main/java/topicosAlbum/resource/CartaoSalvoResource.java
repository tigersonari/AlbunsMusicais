package topicosAlbum.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import topicosAlbum.dto.NovoCartaoDTO;
import topicosAlbum.service.CartaoSalvoService;

@Path("/cartoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartaoSalvoResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    CartaoSalvoService service;

    @GET
    @RolesAllowed("USER")
    public Response listar() {
        Long idUsuario = Long.valueOf(jwt.getClaim("idUsuario").toString());
        return Response.ok(service.listarDoUsuario(idUsuario)).build();
    }

    @POST
    @RolesAllowed("USER")
    public Response criar(NovoCartaoDTO dto) {
        Long idUsuario = Long.valueOf(jwt.getClaim("idUsuario").toString());
        return Response.ok(service.criarParaUsuario(dto, idUsuario)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("USER")
    public Response buscar(@PathParam("id") Long id) {
        Long idUsuario = Long.valueOf(jwt.getClaim("idUsuario").toString());
        return Response.ok(service.buscarDoUsuario(id, idUsuario)).build();
    }
}
