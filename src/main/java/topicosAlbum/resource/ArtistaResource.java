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
import topicosAlbum.model.Artista;
import topicosAlbum.repository.ArtistaRepository;

@Path("/artistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistaResource {

    @Inject
    ArtistaRepository repository;

    @GET
    public List<Artista> buscarTodos() {
        return repository.listAll();
    }

    @GET
    @Path("/find/{nome}")
    public List<Artista> buscarPorNome(@PathParam("nome") String nome) {
        return repository.findByNome(nome);
    }

    @POST
    @Transactional
    public Artista incluir(Artista artista) {
        Artista novoArtista = new Artista();
        novoArtista.setNome(artista.getNome());
        novoArtista.setDataNascimento(artista.getDataNascimento());
        novoArtista.setNacionalidade(artista.getNacionalidade());
        novoArtista.setInstrumentoPrincipal(artista.getInstrumentoPrincipal());
        novoArtista.setInfo(artista.getInfo());

        repository.persist(novoArtista);

        return novoArtista;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Artista alterar(@PathParam("id") Long id, Artista artista) {
        Artista edicaoArtista = repository.findById(id);
        if (edicaoArtista != null) {
            edicaoArtista.setNome(artista.getNome());
            edicaoArtista.setDataNascimento(artista.getDataNascimento());
            edicaoArtista.setNacionalidade(artista.getNacionalidade());
            edicaoArtista.setInstrumentoPrincipal(artista.getInstrumentoPrincipal());
            edicaoArtista.setInfo(artista.getInfo());
        }
        return edicaoArtista;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagar(@PathParam("id") Long id) {
        repository.deleteById(id);
    }
}

