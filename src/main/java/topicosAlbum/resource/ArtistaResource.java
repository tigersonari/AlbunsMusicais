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
import topicosAlbum.dto.ArtistaDTO;
import topicosAlbum.service.ArtistaService;

@Path("/artistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistaResource {

    private static final Logger LOG = Logger.getLogger(ArtistaResource.class);

    @Inject 
    ArtistaService service;

    // ---------- CRUD ----------
    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar todos os artistas");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar artista por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid ArtistaDTO dto) {
        LOG.info(">>> [ArtistaResource] POST /artistas criando novo artista");
        return Response.status(Response.Status.CREATED)
                .entity(service.create(dto))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response update(@PathParam("id") Long id, @Valid ArtistaDTO dto) {
        LOG.info(">>> [ArtistaResource] PUT /artistas atualizando artista com id: ");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [ArtistaResource] DELETE /artistas deletando artista com id: ");
        service.delete(id);
        return Response.noContent().build();
    }


    // ---------- CONSULTAS ----------

    @GET
    @Path("/nome-artistico/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByNomeArtistico(@PathParam("nome") String nome) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar artista por nome artistico");
        return Response.ok(service.findByNomeArtistico(nome)).build();
    }

    @GET
    @Path("/nome/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByNomeCompleto(@PathParam("nome") String nome) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar artista por nome completo");
        return Response.ok(service.findByNomeCompleto(nome)).build();
    }

    @GET
    @Path("/nacionalidade/{n}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByNacionalidade(@PathParam("n") String nacionalidade) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar artista por nacionalidade");
        return Response.ok(service.findByNacionalidade(nacionalidade)).build();
    }

    @GET
    @Path("/funcao/{f}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByFuncaoPrincipal(@PathParam("f") String funcao) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar artista por funcao principal");
        return Response.ok(service.findByFuncaoPrincipal(funcao)).build();
    }

    @GET
    @Path("/empresa/{idEmpresa}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByEmpresa(@PathParam("idEmpresa") Long idEmpresa) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar artista por empresa");
        return Response.ok(service.findByEmpresaId(idEmpresa)).build();
    }


    // ---------- RELACIONAMENTOS ----------

    @GET
    @Path("/{id}/grupos")
    @RolesAllowed({"ADM", "USER"})
    public Response findGruposByArtista(@PathParam("id") Long id) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar grupos do artista");
        return Response.ok(service.findGruposByArtistaId(id)).build();
    }

    @GET
    @Path("/{id}/albuns")
    @RolesAllowed({"ADM", "USER"})
    public Response findAlbunsPrincipais(@PathParam("id") Long id) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar albuns principais do artista");
        return Response.ok(service.findAlbunsPrincipaisByArtistaId(id)).build();
    }

    @GET
    @Path("/{id}/albuns/participacoes")
    @RolesAllowed({"ADM", "USER"})
    public Response findAlbunsComParticipacao(@PathParam("id") Long id) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar albuns com participacao do artista");
        return Response.ok(service.findAlbunsComParticipacaoByArtistaId(id)).build();
    }


    @GET
    @Path("/{id}/faixas/todas")
    @RolesAllowed({"ADM", "USER"})
    public Response findTodasFaixasRelacionadas(@PathParam("id") Long id) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar todas as faixas relacionadas ao artista");
        return Response.ok(service.findTodasFaixasRelacionadas(id)).build();
    }

    @GET
    @Path("/{id}/composicoes")
    @RolesAllowed({"ADM", "USER"})
    public Response findComposicoes(@PathParam("id") Long id) {
        LOG.info(">>> [ArtistaResource] GET /artistas chamado para buscar composicoes do artista");
        return Response.ok(service.findComposicoesByArtistaId(id)).build();
    }
}
