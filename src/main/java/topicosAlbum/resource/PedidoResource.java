package topicosAlbum.resource;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
import topicosAlbum.dto.PedidoDTO;
import topicosAlbum.service.PedidoService;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @Inject
    PedidoService service;

    // ---------------- CRIAR PEDIDO ----------------

    @POST
    @RolesAllowed("USER")
    public Response criar(@Valid PedidoDTO dto) {
        LOG.info(">>> [PedidoResource] POST /pedidos chamado para criar novo pedido");
        return Response
            .status(Status.CREATED)
            .entity(service.create(dto))
            .build();
    }

    // ---------------- BUSCAR POR ID ----------------

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info(">>> [PedidoResource] GET /pedidos/{id} chamado para buscar pedido por id");
        return Response.ok(service.findById(id)).build();
    }

    // ---------------- HISTÓRICO DO USUÁRIO ----------------

    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorUsuario(@PathParam("idUsuario") Long idUsuario) {
        LOG.info(">>> [PedidoResource] GET /pedidos/usuario/{idUsuario} chamado para buscar pedidos por usuário");
        return Response.ok(service.findByUsuario(idUsuario)).build();
    }

    // ---------------- CANCELAR PEDIDO ----------------

    @PUT
    @Path("/{id}/cancelar")
    @RolesAllowed("USER")
    public Response cancelar(@PathParam("id") Long id) {
        LOG.info(">>> [PedidoResource] PUT /pedidos/{id}/cancelar chamado para cancelar pedido");
        service.cancelar(id);
        return Response.noContent().build();
    }
}
