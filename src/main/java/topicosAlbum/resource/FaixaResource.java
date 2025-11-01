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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import topicosAlbum.dto.FaixaDTO;
import topicosAlbum.service.FaixaService;

@Path("/faixas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FaixaResource {

    @Inject 
    FaixaService service;

    @GET
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/titulo/{titulo}")
    public Response findByTitulo(@PathParam("titulo") String titulo) {
        return Response.ok(service.findByTitulo(titulo)).build();
    }

    @GET
    @Path("/album/{idAlbum}")
    public Response findByAlbum(@PathParam("idAlbum") Long idAlbum) {
        return Response.ok(service.findByAlbum(idAlbum)).build();
    }

    @GET
    @Path("/participacao/{idProjeto}")
    public Response findByArtistaParticipante(@PathParam("idProjeto") Long idProjeto) {
        return Response.ok(service.findByArtistaParticipante(idProjeto)).build();
    }

    @GET
    @Path("/compositor/{idProjeto}")
    public Response findByCompositor(@PathParam("idProjeto") Long idProjeto) {
        return Response.ok(service.findByCompositor(idProjeto)).build();
    }

    @GET
    @Path("/genero/{idGenero}")
    public Response findByGenero(@PathParam("idGenero") Long idGenero) {
        return Response.ok(service.findByGenero(idGenero)).build();
    }

    @GET
    @Path("/idioma/{idioma}")
    public Response findByIdioma(@PathParam("idioma") String idioma) {
        return Response.ok(service.findByIdioma(idioma)).build();
    }

    @GET
    @Path("/find/tipoVersao/{idTipoVersao}")
    public Response findByTipoVersao(@PathParam("idTipoVersao") Long idTipoVersao) {
        return Response.ok(service.findByTipoVersao(idTipoVersao)).build();
    }


    @POST
    public Response create(@Valid FaixaDTO dto) {
        return Response.status(Response.Status.CREATED)
            .entity(service.create(dto))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid FaixaDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
