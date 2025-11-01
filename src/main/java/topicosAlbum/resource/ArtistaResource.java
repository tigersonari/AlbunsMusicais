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
import topicosAlbum.dto.ArtistaDTO;
import topicosAlbum.service.ArtistaService;

@Path("/artistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistaResource {

    @Inject 
    ArtistaService service;

    // ---------- CRUD ----------
    @GET
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response create(@Valid ArtistaDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.create(dto))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ArtistaDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }


    // ---------- CONSULTAS ----------

    @GET
    @Path("/nome-artistico/{nome}")
    public Response findByNomeArtistico(@PathParam("nome") String nome) {
        return Response.ok(service.findByNomeArtistico(nome)).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response findByNomeCompleto(@PathParam("nome") String nome) {
        return Response.ok(service.findByNomeCompleto(nome)).build();
    }

    @GET
    @Path("/nacionalidade/{n}")
    public Response findByNacionalidade(@PathParam("n") String nacionalidade) {
        return Response.ok(service.findByNacionalidade(nacionalidade)).build();
    }

    @GET
    @Path("/funcao/{f}")
    public Response findByFuncaoPrincipal(@PathParam("f") String funcao) {
        return Response.ok(service.findByFuncaoPrincipal(funcao)).build();
    }

    @GET
    @Path("/empresa/{idEmpresa}")
    public Response findByEmpresa(@PathParam("idEmpresa") Long idEmpresa) {
        return Response.ok(service.findByEmpresaId(idEmpresa)).build();
    }


    // ---------- RELACIONAMENTOS ----------

    @GET
    @Path("/{id}/grupos")
    public Response findGruposByArtista(@PathParam("id") Long id) {
        return Response.ok(service.findGruposByArtistaId(id)).build();
    }

    @GET
    @Path("/{id}/albuns")
    public Response findAlbunsPrincipais(@PathParam("id") Long id) {
        return Response.ok(service.findAlbunsPrincipaisByArtistaId(id)).build();
    }

    @GET
    @Path("/{id}/albuns/participacoes")
    public Response findAlbunsComParticipacao(@PathParam("id") Long id) {
        return Response.ok(service.findAlbunsComParticipacaoByArtistaId(id)).build();
    }

    @GET
    @Path("/{id}/faixas")
    public Response findFaixasParticipadas(@PathParam("id") Long id) {
        return Response.ok(service.findFaixasParticipadasByArtistaId(id)).build();
    }

    @GET
    @Path("/{id}/faixas/todas")
    public Response findTodasFaixasRelacionadas(@PathParam("id") Long id) {
        return Response.ok(service.findTodasFaixasRelacionadas(id)).build();
    }

    @GET
    @Path("/{id}/composicoes")
    public Response findComposicoes(@PathParam("id") Long id) {
        return Response.ok(service.findComposicoesByArtistaId(id)).build();
    }
}
