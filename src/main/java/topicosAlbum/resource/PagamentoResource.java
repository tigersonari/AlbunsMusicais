package topicosAlbum.resource;

import org.jboss.logging.Logger;

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
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.service.PagamentoService;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @Inject
    PagamentoService service;

    // ---------------- BUSCAR PAGAMENTO ----------------

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info(">>> [PagamentoResource] GET /pagamentos/{id} chamado para buscar pagamento por id");
        return Response.ok(service.findById(id)).build();
    }

    // ---------------- GERAR PIX PARA UM PEDIDO ----------------

    @POST
    @Path("/pix/{idPedido}")
    @RolesAllowed("USER")
    public Response gerarPix(@PathParam("idPedido") Long idPedido) {
        LOG.info(">>> [PagamentoResource] POST /pagamentos/pix/{idPedido} chamado para gerar pix para pedido");
        return Response
            .status(Status.CREATED)
            .entity(service.gerarPixParaPedido(idPedido))
            .build();
    }

    // ---------------- CONFIRMAR PAGAMENTO ----------------

    @PUT
    @Path("/{id}/confirmar")
    @RolesAllowed("USER")
    public Response confirmar(@PathParam("id") Long idPagamento) {
        LOG.info(">>> [PagamentoResource] PUT /pagamentos/{id}/confirmar chamado para confirmar pagamento");
        service.confirmarPagamento(idPagamento);
        return Response.noContent().build();
    }
}
