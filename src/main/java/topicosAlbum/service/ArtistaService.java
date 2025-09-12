package topicosAlbum.service;

// o service é onde fica a lógica de negócio, ou seja, o que a aplicação faz.

import java.util.List;

import topicosAlbum.dto.ArtistaDTO;
import topicosAlbum.model.Artista;

public interface ArtistaService {
    
    List<Artista> findAll();
    List<Artista> findByNome(String nome);
    Artista findById(Long id);
    Artista create(ArtistaDTO dto);
    void update(Long id, ArtistaDTO dto);
    void delete(Long id);

}
