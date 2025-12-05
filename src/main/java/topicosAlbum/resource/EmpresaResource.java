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
import jakarta.ws.rs.core.Response.Status;
import topicosAlbum.dto.EmpresaDTO;
import topicosAlbum.service.EmpresaService;

@Path("/empresas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpresaResource {

    private static final Logger LOG = Logger.getLogger(EmpresaResource.class);

    @Inject
    EmpresaService service;

    @GET
    @RolesAllowed({"ADM", "USER"})
    public Response getAll() {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar todas as empresas");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response getById(@PathParam("id") Long id) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar empresa por id");
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @RolesAllowed("ADM")
    public Response create(@Valid EmpresaDTO dto) {
        LOG.info(">>> [EmpresaResource] POST /empresas criando nova empresa");
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response update(@PathParam("id") Long id, @Valid EmpresaDTO dto) {
        LOG.info(">>> [EmpresaResource] PUT /empresas atualizando empresa com id: ");
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADM")
    public Response delete(@PathParam("id") Long id) {
        LOG.info(">>> [EmpresaResource] DELETE /empresas deletando empresa com id: ");
        service.delete(id);
        return Response.noContent().build();
    }

    // ---------- FINDERS ----------
    
    @GET
    @Path("/find/nome/{nome}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar empresas por nome");
        return Response.ok(service.findByNome(nome)).build();
    }

    @GET
    @Path("/find/cnpj/{cnpj}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByCnpj(@PathParam("cnpj") String cnpj) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar empresas por cnpj");
        return Response.ok(service.findByCnpj(cnpj)).build();
    }

    @GET
    @Path("/find/localizacao/{loc}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByLocalizacao(@PathParam("loc") String loc) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar empresas por localizacao");
        return Response.ok(service.findByLocalizacao(loc)).build();
    }

    @GET
    @Path("/find/contato/{contato}")
    @RolesAllowed({"ADM", "USER"})
    public Response findByContato(@PathParam("contato") String contato) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar empresas por contato");
        return Response.ok(service.findByContato(contato)).build();
    }

    // ---------- DOMÍNIO ----------
    
    @GET
    @Path("/{id}/artistas")
    @RolesAllowed({"ADM", "USER"})
    public Response artistas(@PathParam("id") Long id) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar artistas de uma empresa");
        return Response.ok(service.findArtistas(id)).build();
    }

    @GET
    @Path("/{id}/grupos")
    @RolesAllowed({"ADM", "USER"})
    public Response grupos(@PathParam("id") Long id) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar grupos de uma empresa");
        return Response.ok(service.findGrupos(id)).build();
    }

    @GET
    @Path("/{id}/albuns/producao")
    @RolesAllowed({"ADM", "USER"})
    public Response albunsProduzidos(@PathParam("id") Long id) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar albuns produzidos por uma empresa");
        return Response.ok(service.findAlbunsProduzidos(id)).build();
    }

    @GET
    @Path("/{id}/albuns/lancados")
    @RolesAllowed({"ADM", "USER"})
    public Response albunsLancados(@PathParam("id") Long id) {
        LOG.info(">>> [EmpresaResource] GET /empresas chamado para buscar albuns lancados por uma empresa");
        return Response.ok(service.findAlbunsLancados(id)).build();
    }
}
