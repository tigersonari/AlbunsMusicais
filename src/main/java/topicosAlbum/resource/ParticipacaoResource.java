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
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.ParticipacaoDTO;
import topicosAlbum.service.ParticipacaoService;

@Path("/participacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParticipacaoResource {

    @Inject ParticipacaoService service;

    @GET
    public Response findAll() { return Response.ok(service.findAll()).build(); }

    @GET @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response create(@Valid ParticipacaoDTO dto) {
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ParticipacaoDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET @Path("/faixa/{idFaixa}")
    public Response findByFaixa(@PathParam("idFaixa") Long idFaixa) {
        return Response.ok(service.findByFaixaId(idFaixa)).build();
    }

    @GET @Path("/projeto/{idProjetoMusical}")
    public Response findByProjeto(@PathParam("idProjetoMusical") Long idProjetoMusical) {
        return Response.ok(service.findByProjetoMusicalId(idProjetoMusical)).build();
    }

    @GET @Path("/papel/{papel}")
    public Response findByPapel(@PathParam("papel") String papel) {
        return Response.ok(service.findByPapel(papel)).build();
    }

    @GET @Path("/destaque")
    public Response principais() {
        return Response.ok(service.findPrincipais()).build();
    }

    @GET @Path("/album/{idAlbum}")
    public Response findByAlbum(@PathParam("idAlbum") Long idAlbum) {
        return Response.ok(service.findByAlbumId(idAlbum)).build();
    }
}
