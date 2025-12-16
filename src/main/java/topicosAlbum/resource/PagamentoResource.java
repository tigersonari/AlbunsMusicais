package topicosAlbum.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import topicosAlbum.service.PagamentoService;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    PagamentoService service;

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {

        Long idToken = Long.valueOf(jwt.getClaim("idUsuario").toString());
        boolean admin = jwt.getGroups().contains("ADM");

        return Response.ok(service.findByIdSeguro(id, idToken, admin)).build();
    }

    @POST
    @Path("/pix/{idPedido}")
    @RolesAllowed("USER")
    public Response gerarPix(@PathParam("idPedido") Long idPedido) {

        Long idToken = Long.valueOf(jwt.getClaim("idUsuario").toString());
        boolean admin = jwt.getGroups().contains("ADM");

        return Response.ok(service.gerarPixParaPedidoSeguro(idPedido, idToken, admin)).build();
    }

    @PUT
    @Path("/{id}/confirmar")
    @RolesAllowed("USER")
    public Response solicitarConfirmacao(@PathParam("id") Long idPagamento) {

        Long idToken = Long.valueOf(jwt.getClaim("idUsuario").toString());

        service.solicitarConfirmacao(idPagamento, idToken);

        return Response.accepted().entity("Pagamento enviado para processamento.").build();
    }
}
