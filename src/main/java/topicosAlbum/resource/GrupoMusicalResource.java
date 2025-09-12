package topicosAlbum.resource;


import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import topicosAlbum.model.GrupoMusical;
import topicosAlbum.repository.GrupoMusicalRepository;

@Path("/grupos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GrupoMusicalResource {

    @Inject
    GrupoMusicalRepository repository;

    @GET
    public List<GrupoMusical> buscarTodos() {
        return repository.listAll();
    }

    @GET
    @Path("/find/{nome}")
    public List<GrupoMusical> buscarPorNome(@PathParam("nome") String nome) {
        return repository.findByNome(nome);
    }

    @POST
    @Transactional
    public GrupoMusical incluir(GrupoMusical grupo) {
        GrupoMusical novoGrupo = new GrupoMusical();
        novoGrupo.setNomeGrupo(grupo.getNomeGrupo());
        novoGrupo.setNumeroIntegrantes(grupo.getNumeroIntegrantes());
        novoGrupo.setMembros(grupo.getMembros());
        novoGrupo.setInfo(grupo.getInfo());
        novoGrupo.setDataFormacao(grupo.getDataFormacao());
    
        novoGrupo.setGeneroMusical(grupo.getGeneroMusical());

        repository.persist(novoGrupo);

        return novoGrupo;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public GrupoMusical alterar(@PathParam("id") Long id, GrupoMusical grupo) {
        GrupoMusical edicaoGrupo = repository.findById(id);
        if (edicaoGrupo != null) {
            edicaoGrupo.setNomeGrupo(grupo.getNomeGrupo());
            edicaoGrupo.setNumeroIntegrantes(grupo.getNumeroIntegrantes());
            edicaoGrupo.setMembros(grupo.getMembros());
            edicaoGrupo.setInfo(grupo.getInfo());
            edicaoGrupo.setDataFormacao(grupo.getDataFormacao());
            edicaoGrupo.setGeneroMusical(grupo.getGeneroMusical());
        }
        return edicaoGrupo;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagar(@PathParam("id") Long id) {
        repository.deleteById(id);
    }
}
