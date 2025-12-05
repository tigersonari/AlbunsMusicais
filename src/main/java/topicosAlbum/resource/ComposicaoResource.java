package topicosAlbum.resource;

import java.time.LocalDate;

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
import topicosAlbum.dto.ComposicaoDTO;
import topicosAlbum.service.ComposicaoService;

@Path("/composicoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComposicaoResource {

    private static final Logger LOG = Logger.getLogger(ComposicaoResource.class);

    @Inject ComposicaoService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() {
        LOG.info(">>> [ComposicaoResource] GET /composicoes chamado para buscar todas as composicoes");
        return Response.ok(service.findAll()).build();
    }

    @GET @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        LOG.info(">>> [ComposicaoResource] GET /composicoes chamado para buscar composicao por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid ComposicaoDTO dto) {
        LOG.info(">>> [ComposicaoResource] POST /composicoes criando nova composicao");
        return Response.status(201).entity(service.create(dto)).build();
    }

    @PUT @Path("/{id}")
    @RolesAllowed("ADM")
    public Response update(@PathParam("id") Long id, @Valid ComposicaoDTO dto) {
        LOG.info(">>> [ComposicaoResource] PUT /composicoes atualizando composicao com id: ");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [ComposicaoResource] DELETE /composicoes deletando composicao com id: ");
        service.delete(id);
        return Response.noContent().build();
    }

    @GET @Path("/data/{data}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByData(@PathParam("data") String data) {
        LOG.info(">>> [ComposicaoResource] GET /composicoes chamado para buscar composicoes por data");
        return Response.ok(service.findByData(LocalDate.parse(data))).build();
    }

    @GET @Path("/projeto/{idProjeto}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByProjeto(@PathParam("idProjeto") Long idProjeto) {
        LOG.info(">>> [ComposicaoResource] GET /composicoes chamado para buscar composicoes por projeto musical");
        return Response.ok(service.findByProjetoMusical(idProjeto)).build();
    }

    @GET @Path("/faixa/{idFaixa}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByFaixaId(@PathParam("idFaixa") Long idFaixa) {
        LOG.info(">>> [ComposicaoResource] GET /composicoes chamado para buscar composicoes por faixa");
        return Response.ok(service.findByFaixaId(idFaixa)).build();
    }
}
