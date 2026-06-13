package topicosAlbum.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
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

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @Inject
    PedidoService service;

    // ---------------- CRIAR PEDIDO ----------------
    @POST
    @RolesAllowed("USER")
    public Response criar(@Valid PedidoDTO dto) {
        LOG.info(">>> POST /pedidos");

        Long idUsuarioToken = Long.valueOf(jwt.getClaim("idUsuario").toString());


        return Response
            .status(Status.CREATED)
            .entity(service.createParaUsuario(dto, idUsuarioToken))
            .build();
    }

    @GET
@RolesAllowed("ADM")
public Response buscarTodos() {
    return Response.ok(service.findAll()).build();
}

    // ---------------- BUSCAR POR ID ----------------
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info(">>> GET /pedidos/{id}");

        Long idUsuarioToken = Long.valueOf(jwt.getClaim("idUsuario").toString());
        boolean isAdmin = jwt.getGroups().contains("ADM");

        return Response.ok(
            service.findByIdSeguro(id, idUsuarioToken, isAdmin)
        ).build();
    }

    // ---------------- HISTÓRICO DO USUÁRIO ----------------
    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorUsuario(@PathParam("idUsuario") Long idUsuario) {
        LOG.info(">>> GET /pedidos/usuario/{idUsuario}");

        Long idUsuarioToken = Long.valueOf(jwt.getClaim("idUsuario").toString());

        boolean isAdmin = jwt.getGroups().contains("ADM");

        if (!isAdmin && !idUsuario.equals(idUsuarioToken)) {
            return Response.status(Status.FORBIDDEN).build();
        }

        return Response.ok(service.findByUsuario(idUsuario)).build();
    }

    // ---------------- CANCELAR PEDIDO ----------------
    @PUT
    @Path("/{id}/cancelar")
    @RolesAllowed("USER")
    public Response cancelar(@PathParam("id") Long id) {
        LOG.info(">>> PUT /pedidos/{id}/cancelar");

        Long idUsuarioToken = Long.valueOf(jwt.getClaim("idUsuario").toString());


        service.cancelarSeguro(id, idUsuarioToken);

        return Response.noContent().build();
    }


    @GET
@Path("/count")
@RolesAllowed("ADM")
public Response count() {
    return Response.ok(service.count()).build();
}

@GET
@Path("/count/status/{status}")
@RolesAllowed("ADM")
public Response countByStatus(@PathParam("status") String status) {
    return Response.ok(service.countByStatus(status)).build();
}

@GET
@Path("/faturamento")
@RolesAllowed("ADM")
public Response faturamentoTotal() {
    return Response.ok(service.faturamentoTotal()).build();
}

}
