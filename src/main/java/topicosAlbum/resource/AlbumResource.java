package topicosAlbum.resource;

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

    @Inject
    AlbumService service;

    //crud

    @GET
    public Response buscarTodos() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response incluir(@Valid AlbumDTO dto) {
        return Response
            .status(Status.CREATED)
            .entity(service.create(dto))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid AlbumDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    // query
    @GET
    @Path("/find/titulo/{titulo}")
    public Response buscarPorTitulo(@PathParam("titulo") String titulo) {
        return Response.ok(service.findByTitulo(titulo)).build();
    }

    @GET
    @Path("/find/ano/{ano}")
    public Response buscarPorAnoLancamento(@PathParam("ano") int ano) {
        return Response.ok(service.findByAnoLancamento(ano)).build();
    }

    @GET
    @Path("/find/formato/{idFormato}")
    public Response buscarPorFormato(@PathParam("idFormato") Long idFormato) {
        return Response.ok(service.findByFormato(idFormato)).build();
    }


   @GET
@Path("/find/projeto/{idProjetoMusical}")
public Response buscarPorArtistaPrincipal(@PathParam("idProjetoMusical") Long idProjetoMusical) {
    return Response.ok(service.findByArtistaPrincipal(idProjetoMusical)).build();
}

@GET
@Path("/find/participacao/{idProjetoMusical}")
public Response buscarPorParticipacao(@PathParam("idProjetoMusical") Long idProjetoMusical) {
    return Response.ok(service.findByParticipacao(idProjetoMusical)).build();
}


    @GET
    @Path("/find/colaboracao")
    public Response buscarColaboracoesEntre(
        @QueryParam("idArtista1") Long id1,
        @QueryParam("idArtista2") Long id2) {
        return Response.ok(service.findColaboracoesEntre(id1, id2)).build();
    }

    @GET
    @Path("/find/producao/empresa/{idEmpresa}")
    public Response buscarPorEmpresaProducao(@PathParam("idEmpresa") Long idEmpresa) {
        return Response.ok(service.findByEmpresaProducao(idEmpresa)).build();
    }

    @GET
    @Path("/find/produtor/{nome}")
    public Response buscarPorProdutor(@PathParam("nome") String nome) {
        return Response.ok(service.findByProdutor(nome)).build();
    }

    @GET
    @Path("/find/mixagem/{nome}")
    public Response buscarPorEngMixagem(@PathParam("nome") String nome) {
        return Response.ok(service.findByEngenheiroMixagem(nome)).build();
    }

    @GET
    @Path("/find/masterizacao/{nome}")
    public Response buscarPorEngMasterizacao(@PathParam("nome") String nome) {
        return Response.ok(service.findByEngenheiroMasterizacao(nome)).build();
    }

    @GET
    @Path("/find/genero/{idGenero}")
    public Response buscarPorGenero(@PathParam("idGenero") Long idGenero) {
        return Response.ok(service.findByGenero(idGenero)).build();
    }

    @GET
    @Path("/find/faixa/{tituloFaixa}")
    public Response buscarPorTituloFaixa(@PathParam("tituloFaixa") String tituloFaixa) {
        return Response.ok(service.findByFaixaTitulo(tituloFaixa)).build();
    }
}
