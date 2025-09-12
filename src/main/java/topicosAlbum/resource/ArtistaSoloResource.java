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
import topicosAlbum.model.ArtistaSolo;
import topicosAlbum.repository.ArtistaSoloRepository;

@Path("/artistasolo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistaSoloResource {

    @Inject
    ArtistaSoloRepository repository;

    @GET
    public List<ArtistaSolo> buscarTodos() {
        return repository.listAll();
    }

    @GET
    @Path("/find/{nomeArtistico}")
    public List<ArtistaSolo> buscarPorNomeArtistico(@PathParam("nomeArtistico") String nomeArtistico) {
        return repository.findByNomeArtistico(nomeArtistico);
    }

    @POST
    @Transactional
    public ArtistaSolo incluir(ArtistaSolo artistaSolo) {
        ArtistaSolo novoArtistaSolo = new ArtistaSolo();
        novoArtistaSolo.setNomeArtistico(artistaSolo.getNomeArtistico());
        novoArtistaSolo.setDataInicioCarreira(artistaSolo.getDataInicioCarreira());

        repository.persist(novoArtistaSolo);

        return novoArtistaSolo;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public ArtistaSolo alterar(@PathParam("id") Long id, ArtistaSolo artistaSolo) {
        ArtistaSolo edicaoArtistaSolo = repository.findById(id);
        if (edicaoArtistaSolo != null) {
            edicaoArtistaSolo.setNomeArtistico(artistaSolo.getNomeArtistico());
            edicaoArtistaSolo.setDataInicioCarreira(artistaSolo.getDataInicioCarreira());;
        }
        return edicaoArtistaSolo;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagar(@PathParam("id") Long id) {
        repository.deleteById(id);
    }
}
