package topicosAlbum.resource;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import topicosAlbum.dto.AvaliacaoProfissionalDTO;
import topicosAlbum.service.AvaliacaoProfissionalService;

@Path("/avaliacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvaliacaoProfissionalResource {

    private static final Logger LOG = Logger.getLogger(AvaliacaoProfissionalResource.class);

    @Inject AvaliacaoProfissionalService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() {
        LOG.info(">>> [AvaliacaoProfissionalResource] GET /avaliacoesProfissionais chamado para buscar todas as avaliacoes profissionais");
        return Response.ok(service.findAll()).build();
    }

    @GET @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        LOG.info(">>> [AvaliacaoProfissionalResource] GET /avaliacoesProfissionais chamado para buscar avaliacao profissional por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid AvaliacaoProfissionalDTO dto) {
        LOG.info(">>> [AvaliacaoProfissionalResource] POST /avaliacoesProfissionais criando nova avaliacao profissional");
        return Response.status(Response.Status.CREATED)
                .entity(service.create(dto))
                .build();
    }

    @DELETE @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [AvaliacaoProfissionalResource] DELETE /avaliacoesProfissionais deletando avaliacao profissional com id: ");
        service.delete(id);
        return Response.noContent().build();
    }

    // queries
    @GET @Path("/album/{idAlbum}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbum(@PathParam("idAlbum") Long idAlbum) {
        LOG.info(">>> [AvaliacaoProfissionalResource] GET /avaliacoesProfissionais chamado para buscar avaliacoes profissionais por album");
        return Response.ok(service.findByAlbumId(idAlbum)).build();
    }

    @GET @Path("/avaliador/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAvaliador(@PathParam("nome") String nome) {
        LOG.info(">>> [AvaliacaoProfissionalResource] GET /avaliacoesProfissionais chamado para buscar avaliacoes profissionais por avaliador");
        return Response.ok(service.findByAvaliador(nome)).build();
    }

    @GET @Path("/album/{idAlbum}/nota/{nota}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbumAndNota(@PathParam("idAlbum") Long idAlbum, @PathParam("nota") double nota) {
        LOG.info(">>> [AvaliacaoProfissionalResource] GET /avaliacoesProfissionais chamado para buscar avaliacoes profissionais por album e nota");
        return Response.ok(service.findByAlbumAndNota(idAlbum, nota)).build();
    }

    @GET @Path("/album/{idAlbum}/nota-minima/{nota}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbumAndNotaMinima(@PathParam("idAlbum") Long idAlbum, @PathParam("nota") double nota) {
        LOG.info(">>> [AvaliacaoProfissionalResource] GET /avaliacoesProfissionais chamado para buscar avaliacoes profissionais por album e nota minima");
        return Response.ok(service.findByAlbumAndNotaMinima(idAlbum, nota)).build();
    }
}
