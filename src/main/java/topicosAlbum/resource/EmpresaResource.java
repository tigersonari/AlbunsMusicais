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
import topicosAlbum.dto.EmpresaDTO;
import topicosAlbum.service.EmpresaService;

@Path("/empresas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpresaResource {

    @Inject
    EmpresaService service;

    @GET
    public Response getAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response create(@Valid EmpresaDTO dto) {
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid EmpresaDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    // ---------- FINDERS ----------
    
    @GET
    @Path("/find/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(service.findByNome(nome)).build();
    }

    @GET
    @Path("/find/cnpj/{cnpj}")
    public Response findByCnpj(@PathParam("cnpj") String cnpj) {
        return Response.ok(service.findByCnpj(cnpj)).build();
    }

    @GET
    @Path("/find/localizacao/{loc}")
    public Response findByLocalizacao(@PathParam("loc") String loc) {
        return Response.ok(service.findByLocalizacao(loc)).build();
    }

    @GET
    @Path("/find/contato/{contato}")
    public Response findByContato(@PathParam("contato") String contato) {
        return Response.ok(service.findByContato(contato)).build();
    }

    // ---------- DOMÍNIO ----------
    
    @GET
    @Path("/{id}/artistas")
    public Response artistas(@PathParam("id") Long id) {
        return Response.ok(service.findArtistas(id)).build();
    }

    @GET
    @Path("/{id}/grupos")
    public Response grupos(@PathParam("id") Long id) {
        return Response.ok(service.findGrupos(id)).build();
    }

    @GET
    @Path("/{id}/albuns/producao")
    public Response albunsProduzidos(@PathParam("id") Long id) {
        return Response.ok(service.findAlbunsProduzidos(id)).build();
    }

    @GET
    @Path("/{id}/albuns/lancados")
    public Response albunsLancados(@PathParam("id") Long id) {
        return Response.ok(service.findAlbunsLancados(id)).build();
    }
}
