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
import topicosAlbum.dto.ParticipacaoDTO;
import topicosAlbum.service.ParticipacaoService;

@Path("/participacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParticipacaoResource {

    private static final Logger LOG = Logger.getLogger(ParticipacaoResource.class);

    @Inject ParticipacaoService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() { 
        LOG.info(">>> [ParticipacaoResource] GET /participacoes chamado para buscar todas as participações");
        return Response.ok(service.findAll()).build(); }

    @GET @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        LOG.info(">>> [ParticipacaoResource] GET /participacoes/{id} chamado para buscar participação por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid ParticipacaoDTO dto) {
        LOG.info(">>> [ParticipacaoResource] POST /participacoes chamado para criar nova participação");
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT 
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response update(@PathParam("id") Long id, @Valid ParticipacaoDTO dto) {
        LOG.info(">>> [ParticipacaoResource] PUT /participacoes/{id} chamado para atualizar participação");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE 
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [ParticipacaoResource] DELETE /participacoes/{id} chamado para deletar participação");
        service.delete(id);
        return Response.noContent().build();
    }

    @GET 
    @Path("/faixa/{idFaixa}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByFaixa(@PathParam("idFaixa") Long idFaixa) { 
        LOG.info(">>> [ParticipacaoResource] GET /participacoes/faixa/{idFaixa} chamado para buscar participações por faixa");
        return Response.ok(service.findByFaixaId(idFaixa)).build();
    }

    @GET 
    @Path("/projeto/{idProjetoMusical}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByProjeto(@PathParam("idProjetoMusical") Long idProjetoMusical) {
        LOG.info(">>> [ParticipacaoResource] GET /participacoes/projeto/{idProjetoMusical} chamado para buscar participações por projeto musical");
        return Response.ok(service.findByProjetoMusicalId(idProjetoMusical)).build();
    }

    @GET 
    @Path("/papel/{papel}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByPapel(@PathParam("papel") String papel) {
        LOG.info(">>> [ParticipacaoResource] GET /participacoes/papel/{papel} chamado para buscar participações por papel");
        return Response.ok(service.findByPapel(papel)).build();
    }

    @GET 
    @Path("/destaque")
    @RolesAllowed({"ADM", "USER"})
    public Response principais() {
        LOG.info(">>> [ParticipacaoResource] GET /participacoes/destaque chamado para buscar principais participações");
        return Response.ok(service.findPrincipais()).build();
    }

    @GET 
    @Path("/album/{idAlbum}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbum(@PathParam("idAlbum") Long idAlbum) {
        LOG.info(">>> [ParticipacaoResource] GET /participacoes/album/{idAlbum} chamado para buscar participações por álbum");
        return Response.ok(service.findByAlbumId(idAlbum)).build();
    }
}
