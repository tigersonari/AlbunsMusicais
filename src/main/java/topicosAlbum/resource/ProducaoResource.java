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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.ProducaoDTO;
import topicosAlbum.service.ProducaoService;

@Path("/producoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProducaoResource {

    private static final Logger LOG = Logger.getLogger(ProducaoResource.class);

    @Inject
    ProducaoService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response buscarTodos() {
        LOG.info(">>> [ProducaoResource] GET /producoes chamado para buscar todas as produções");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info(">>> [ProducaoResource] GET /producoes/{id} chamado para buscar produção por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response incluir(@Valid ProducaoDTO dto) {
        LOG.info(">>> [ProducaoResource] POST /producoes chamado para criar nova produção");
        return Response.status(Status.CREATED)
                .entity(service.create(dto))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response alterar(@PathParam("id") Long id, @Valid ProducaoDTO dto) {
        LOG.info(">>> [ProducaoResource] PUT /producoes/{id} chamado para atualizar produção");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response apagar(@PathParam("id") Long id) {
        LOG.info(">>> [ProducaoResource] DELETE /producoes/{id} chamado para deletar produção");
        service.delete(id);
        return Response.noContent().build();
    }

   

    @GET
    @Path("/find/produtor/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorProdutor(@PathParam("nome") String nome) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/produtor/{nome} chamado para buscar produções por produtor");
        return Response.ok(service.findByProdutor(nome)).build();
    }

    @GET
    @Path("/find/gravacao/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorEngGravacao(@PathParam("nome") String nome) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/gravacao/{nome} chamado para buscar produções por engenheiro de gravação");
        return Response.ok(service.findByEngenheiroGravacao(nome)).build();
    }

    @GET
    @Path("/find/mixagem/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorMixagem(@PathParam("nome") String nome) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/mixagem/{nome} chamado para buscar produções por engenheiro de mixagem");
        return Response.ok(service.findByEngenheiroMixagem(nome)).build();
    }

    @GET
    @Path("/find/masterizacao/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorMasterizacao(@PathParam("nome") String nome) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/masterizacao/{nome} chamado para buscar produções por engenheiro de masterização");
        return Response.ok(service.findByEngenheiroMasterizacao(nome)).build();
    }

    @GET
    @Path("/find/data/{data}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorDataProducao(@PathParam("data") String data) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/data/{data} chamado para buscar produções por data de produção");
        return Response.ok(service.findByDataProducao(LocalDate.parse(data))).build();
    }

    @GET
    @Path("/find/periodo")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorPeriodoProducao(
        @QueryParam("inicio") String inicio,
        @QueryParam("fim") String fim
    ) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/periodo chamado para buscar produções por período de produção");
        return Response.ok(service.findByPeriodoProducao(
            LocalDate.parse(inicio),
            LocalDate.parse(fim)
        )).build();
    }

    @GET
    @Path("/find/empresa/{idEmpresa}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorEmpresa(@PathParam("idEmpresa") Long idEmpresa) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/empresa/{idEmpresa} chamado para buscar produções por empresa");
        return Response.ok(service.findByEmpresa(idEmpresa)).build();
    }

    @GET
    @Path("/find/album/{idAlbum}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorAlbum(@PathParam("idAlbum") Long idAlbum) {
        LOG.info(">>> [ProducaoResource] GET /producoes/find/album/{idAlbum} chamado para buscar produções por álbum");
        return Response.ok(service.findByAlbum(idAlbum)).build();
    }
}
