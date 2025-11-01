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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.ProducaoDTO;
import topicosAlbum.service.ProducaoService;

@Path("/producoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProducaoResource {

    @Inject
    ProducaoService service;

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
    public Response incluir(@Valid ProducaoDTO dto) {
        return Response.status(Status.CREATED)
                .entity(service.create(dto))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid ProducaoDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

   

    @GET
    @Path("/find/produtor/{nome}")
    public Response buscarPorProdutor(@PathParam("nome") String nome) {
        return Response.ok(service.findByProdutor(nome)).build();
    }

    @GET
    @Path("/find/gravacao/{nome}")
    public Response buscarPorEngGravacao(@PathParam("nome") String nome) {
        return Response.ok(service.findByEngenheiroGravacao(nome)).build();
    }

    @GET
    @Path("/find/mixagem/{nome}")
    public Response buscarPorMixagem(@PathParam("nome") String nome) {
        return Response.ok(service.findByEngenheiroMixagem(nome)).build();
    }

    @GET
    @Path("/find/masterizacao/{nome}")
    public Response buscarPorMasterizacao(@PathParam("nome") String nome) {
        return Response.ok(service.findByEngenheiroMasterizacao(nome)).build();
    }

    @GET
    @Path("/find/data/{data}")
    public Response buscarPorDataProducao(@PathParam("data") String data) {
        return Response.ok(service.findByDataProducao(LocalDate.parse(data))).build();
    }

    @GET
    @Path("/find/periodo")
    public Response buscarPorPeriodoProducao(
        @QueryParam("inicio") String inicio,
        @QueryParam("fim") String fim
    ) {
        return Response.ok(service.findByPeriodoProducao(
            LocalDate.parse(inicio),
            LocalDate.parse(fim)
        )).build();
    }

    @GET
    @Path("/find/empresa/{idEmpresa}")
    public Response buscarPorEmpresa(@PathParam("idEmpresa") Long idEmpresa) {
        return Response.ok(service.findByEmpresa(idEmpresa)).build();
    }

    @GET
    @Path("/find/album/{idAlbum}")
    public Response buscarPorAlbum(@PathParam("idAlbum") Long idAlbum) {
        return Response.ok(service.findByAlbum(idAlbum)).build();
    }
}
