package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.ArtistaSolo;

@ApplicationScoped
public class ArtistaSoloRepository implements PanacheRepository<ArtistaSolo> {

    public List<ArtistaSolo> findByNomeArtistico(String nomeArtistico) {
        return find("SELECT a FROM ArtistaSolo a WHERE a.nomeArtistico LIKE ?1", "%" + nomeArtistico + "%").list();
    }
}
