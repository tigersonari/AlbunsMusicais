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
import topicosAlbum.dto.FaixaDTO;
import topicosAlbum.service.FaixaService;

@Path("/faixas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FaixaResource {

    private static final Logger LOG = Logger.getLogger(FaixaResource.class);

    @Inject 
    FaixaService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() {
        LOG.info(">>> [FaixaResource] GET /faixas chamado para listar todas as faixas");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        LOG.info(">>> [FaixaResource] GET /faixas/{id} chamado para buscar faixa por id");
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/titulo/{titulo}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByTitulo(@PathParam("titulo") String titulo) {
        LOG.info(">>> [FaixaResource] GET /faixas/titulo/{titulo} chamado para buscar faixa por título");
        return Response.ok(service.findByTitulo(titulo)).build();
    }

    @GET
    @Path("/album/{idAlbum}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbum(@PathParam("idAlbum") Long idAlbum) {
        LOG.info(">>> [FaixaResource] GET /faixas/album/{idAlbum} chamado para buscar faixas por álbum");
        return Response.ok(service.findByAlbum(idAlbum)).build();
    }

    @GET
    @Path("/participacao/{idProjeto}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByArtistaParticipante(@PathParam("idProjeto") Long idProjeto) {
        LOG.info(">>> [FaixaResource] GET /faixas/participacao/{idProjeto} chamado para buscar faixas por artista participante");
        return Response.ok(service.findByArtistaParticipante(idProjeto)).build();
    }

    @GET
    @Path("/compositor/{idProjeto}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByCompositor(@PathParam("idProjeto") Long idProjeto) {
        LOG.info(">>> [FaixaResource] GET /faixas/compositor/{idProjeto} chamado para buscar faixas por compositor");
        return Response.ok(service.findByCompositor(idProjeto)).build();
    }

    @GET
    @Path("/genero/{idGenero}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByGenero(@PathParam("idGenero") Long idGenero) {
        LOG.info(">>> [FaixaResource] GET /faixas/genero/{idGenero} chamado para buscar faixas por gênero");
        return Response.ok(service.findByGenero(idGenero)).build();
    }

    @GET
    @Path("/idioma/{idioma}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByIdioma(@PathParam("idioma") String idioma) {
        LOG.info(">>> [FaixaResource] GET /faixas/idioma/{idioma} chamado para buscar faixas por idioma");
        return Response.ok(service.findByIdioma(idioma)).build();
    }

    @GET
    @Path("/find/tipoVersao/{idTipoVersao}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByTipoVersao(@PathParam("idTipoVersao") Long idTipoVersao) {
        LOG.info(">>> [FaixaResource] GET /faixas/find/tipoVersao/{idTipoVersao} chamado para buscar faixas por tipo de versão");
        return Response.ok(service.findByTipoVersao(idTipoVersao)).build();
    }


    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid FaixaDTO dto) {
        LOG.info(">>> [FaixaResource] POST /faixas criando nova faixa");
        return Response.status(Response.Status.CREATED)
            .entity(service.create(dto))
            .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response update(@PathParam("id") Long id, @Valid FaixaDTO dto) {
        LOG.info(">>> [FaixaResource] PUT /faixas/{id} atualizando faixa com id: ");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [FaixaResource] DELETE /faixas/{id} apagando faixa com id: ");
        service.delete(id);
        return Response.noContent().build();
    }
}
