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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.AlbumDTO;
import topicosAlbum.service.AlbumService;

@Path("/albums")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlbumResource {

    private static final Logger LOG = Logger.getLogger(AlbumResource.class);

    @Inject
    AlbumService service;
    

    //crud

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response buscarTodos() {
        LOG.info(">>> [AlbumResource] GET /albums chamado para buscar todos os álbuns");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info(">>> [AlbumResource] GET /albums chamado para buscar álbum por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response incluir(@Valid AlbumDTO dto) {
        LOG.info(">>> [AlbumResource] POST /albums postando album");
        return Response
            .status(Status.CREATED)
            .entity(service.create(dto))
            .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response alterar(@PathParam("id") Long id, @Valid AlbumDTO dto) {
        LOG.info(">>> [AlbumResource] PUT /albums atualizando álbum");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response apagar(@PathParam("id") Long id) {
        LOG.info(">>> [AlbumResource] DELETE /albums apagando álbum");
        service.delete(id);
        return Response.noContent().build();
    }

    // query
    @GET
    @Path("/find/titulo/{titulo}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorTitulo(@PathParam("titulo") String titulo) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por título");
        return Response.ok(service.findByTitulo(titulo)).build();
    }

    @GET
    @Path("/find/ano/{ano}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorAnoLancamento(@PathParam("ano") int ano) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por ano de lançamento");
        return Response.ok(service.findByAnoLancamento(ano)).build();
    }

    @GET
    @Path("/find/formato/{idFormato}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorFormato(@PathParam("idFormato") Long idFormato) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por formato");
        return Response.ok(service.findByFormato(idFormato)).build();
    }


   @GET
@Path("/find/projeto/{idProjetoMusical}")
@RolesAllowed({"ADM", "USER"})
public Response buscarPorArtistaPrincipal(@PathParam("idProjetoMusical") Long idProjetoMusical) {
    LOG.info(">>> [AlbumResource] GET /albums buscando álbum por artista principal");
    return Response.ok(service.findByArtistaPrincipal(idProjetoMusical)).build();
}

@GET
@Path("/find/participacao/{idProjetoMusical}")
@RolesAllowed({"ADM", "USER"})
public Response buscarPorParticipacao(@PathParam("idProjetoMusical") Long idProjetoMusical) {
    LOG.info(">>> [AlbumResource] GET /albums buscando álbum por participação");
    return Response.ok(service.findByParticipacao(idProjetoMusical)).build();
}


    @GET
    @Path("/find/colaboracao")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarColaboracoesEntre(
        @QueryParam("idArtista1") Long id1,
        @QueryParam("idArtista2") Long id2) {
            LOG.info(">>> [AlbumResource] GET /albums buscando álbuns com colaborações entre dois artistas");
        return Response.ok(service.findColaboracoesEntre(id1, id2)).build();
    }

    @GET
    @Path("/find/producao/empresa/{idEmpresa}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorEmpresaProducao(@PathParam("idEmpresa") Long idEmpresa) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por empresa de produção");
        return Response.ok(service.findByEmpresaProducao(idEmpresa)).build();
    }

    @GET
    @Path("/find/produtor/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorProdutor(@PathParam("nome") String nome) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por produtor");
        return Response.ok(service.findByProdutor(nome)).build();
    }

    @GET
    @Path("/find/mixagem/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorEngMixagem(@PathParam("nome") String nome) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por engenheiro de mixagem");
        return Response.ok(service.findByEngenheiroMixagem(nome)).build();
    }

    @GET
    @Path("/find/masterizacao/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorEngMasterizacao(@PathParam("nome") String nome) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por engenheiro de masterização");
        return Response.ok(service.findByEngenheiroMasterizacao(nome)).build();
    }

    @GET
    @Path("/find/genero/{idGenero}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorGenero(@PathParam("idGenero") Long idGenero) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por gênero");
        return Response.ok(service.findByGenero(idGenero)).build();
    }

    @GET
    @Path("/find/faixa/{tituloFaixa}")
    @RolesAllowed({"ADM", "USER"})
    public Response buscarPorTituloFaixa(@PathParam("tituloFaixa") String tituloFaixa) {
        LOG.info(">>> [AlbumResource] GET /albums buscando álbum por título de faixa");
        return Response.ok(service.findByFaixaTitulo(tituloFaixa)).build();
    }
}
