package topicosAlbum.resource;

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

    @Inject AvaliacaoProfissionalService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET @Path("/{id}")
    @RolesAllowed("ADM")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid AvaliacaoProfissionalDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.create(dto))
                .build();
    }

    @DELETE @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    // queries
    @GET @Path("/album/{idAlbum}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbum(@PathParam("idAlbum") Long idAlbum) {
        return Response.ok(service.findByAlbumId(idAlbum)).build();
    }

    @GET @Path("/avaliador/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAvaliador(@PathParam("nome") String nome) {
        return Response.ok(service.findByAvaliador(nome)).build();
    }

    @GET @Path("/album/{idAlbum}/nota/{nota}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbumAndNota(@PathParam("idAlbum") Long idAlbum, @PathParam("nota") double nota) {
        return Response.ok(service.findByAlbumAndNota(idAlbum, nota)).build();
    }

    @GET @Path("/album/{idAlbum}/nota-minima/{nota}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByAlbumAndNotaMinima(@PathParam("idAlbum") Long idAlbum, @PathParam("nota") double nota) {
        return Response.ok(service.findByAlbumAndNotaMinima(idAlbum, nota)).build();
    }
}
