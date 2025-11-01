package topicosAlbum.resource;

import java.time.LocalDate;

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
import topicosAlbum.dto.ComposicaoDTO;
import topicosAlbum.service.ComposicaoService;

@Path("/composicoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComposicaoResource {

    @Inject ComposicaoService service;

    @GET
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response create(@Valid ComposicaoDTO dto) {
        return Response.status(201).entity(service.create(dto)).build();
    }

    @PUT @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ComposicaoDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET @Path("/data/{data}")
    public Response findByData(@PathParam("data") String data) {
        return Response.ok(service.findByData(LocalDate.parse(data))).build();
    }

    @GET @Path("/projeto/{idProjeto}")
    public Response findByProjeto(@PathParam("idProjeto") Long idProjeto) {
        return Response.ok(service.findByProjetoMusical(idProjeto)).build();
    }

    @GET @Path("/faixa/{idFaixa}")
    public Response findByFaixaId(@PathParam("idFaixa") Long idFaixa) {
        return Response.ok(service.findByFaixaId(idFaixa)).build();
    }
}
