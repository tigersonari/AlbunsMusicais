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
import topicosAlbum.dto.GrupoMusicalDTO;
import topicosAlbum.service.GrupoMusicalService;

@Path("/grupos-musicais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GrupoMusicalResource {

    private static final Logger LOG = Logger.getLogger(GrupoMusicalResource.class);

    @Inject 
    GrupoMusicalService service;

    // ---- CRUD ----
    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais chamado para listar todos os grupos musicais");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/{id} chamado para buscar grupo musical por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid GrupoMusicalDTO dto) {
        LOG.info(">>> [GrupoMusicalResource] POST /grupos-musicais criando novo grupo musical");
        return Response.status(Response.Status.CREATED)
            .entity(service.create(dto))
            .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response update(@PathParam("id") Long id, @Valid GrupoMusicalDTO dto) {
        LOG.info(">>> [GrupoMusicalResource] PUT /grupos-musicais/{id} atualizando grupo musical com id: ");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [GrupoMusicalResource] DELETE /grupos-musicais/{id} apagando grupo musical com id: ");
        service.delete(id);
        return Response.noContent().build();
    }

    // ---- Consultas ----

    @GET
    @Path("/nome/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByNomeGrupo(@PathParam("nome") String nome) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/nome/{nome} chamado para buscar grupo musical por nome");
        return Response.ok(service.findByNomeGrupo(nome)).build();
    }

    @GET
    @Path("/empresa/{idEmpresa}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByEmpresa(@PathParam("idEmpresa") Long idEmpresa) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/empresa/{idEmpresa} chamado para buscar grupos musicais por empresa");
        return Response.ok(service.findByEmpresaId(idEmpresa)).build();
    }

    @GET 
    @Path("/ativos")
    @RolesAllowed({"ADM", "USER"})
    public Response findAtivos() {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/ativos chamado para buscar grupos musicais ativos");
        return Response.ok(service.findAtivos()).build();
    }

    @GET 
    @Path("/inativos")
    @RolesAllowed({"ADM", "USER"})
    public Response findInativos() {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/inativos chamado para buscar grupos musicais inativos");
        return Response.ok(service.findInativos()).build();
    }

    // ---- Relacionamentos ----

    @GET 
    @Path("/{id}/membros")
    @RolesAllowed({"ADM", "USER"})
    public Response findMembros(@PathParam("id") Long id) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/{id}/membros chamado para buscar membros do grupo musical");
        return Response.ok(service.findMembrosByGrupoId(id)).build();
    }

    @GET 
    @Path("/{id}/albuns")
    @RolesAllowed({"ADM", "USER"})
    public Response findAlbuns(@PathParam("id") Long id) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/{id}/albuns chamado para buscar álbuns do grupo musical");
        return Response.ok(service.findAlbunsByGrupoId(id)).build();
    }

    @GET 
    @Path("/{id}/albuns/participacoes")
    @RolesAllowed({"ADM", "USER"})
    public Response findAlbunsComParticipacao(@PathParam("id") Long id) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/{id}/albuns/participacoes chamado para buscar álbuns com participação do grupo musical");
        return Response.ok(service.findAlbunsComParticipacaoByGrupoId(id)).build();
    }

    @GET 
    @Path("/{id}/faixas")
    @RolesAllowed({"ADM", "USER"})
    public Response findFaixas(@PathParam("id") Long id) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/{id}/faixas chamado para buscar faixas do grupo musical");
        return Response.ok(service.findFaixasByGrupoId(id)).build();
    }

    @GET 
    @Path("/{id}/composicoes")
    @RolesAllowed({"ADM", "USER"})
    public Response findComposicoes(@PathParam("id") Long id) {
        LOG.info(">>> [GrupoMusicalResource] GET /grupos-musicais/{id}/composicoes chamado para buscar composições do grupo musical");
        return Response.ok(service.findComposicoesDoGrupo(id)).build();
    }
}
