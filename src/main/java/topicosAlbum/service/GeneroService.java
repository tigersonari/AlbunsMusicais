package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.GeneroDTO;
import topicosAlbum.dto.GeneroResponseDTO;

public interface GeneroService {

    //List<GeneroResponseDTO> findAll( );
    List<GeneroResponseDTO> findAll(int page, int pageSize); //adição de paginação
    GeneroResponseDTO findById(Long id);
    List<GeneroResponseDTO> findByNome(String nomeGenero);
    List<GeneroResponseDTO> findByAlbum(Long idAlbum);
    long count();

    GeneroResponseDTO create(@Valid GeneroDTO dto);
    void update(Long id, @Valid GeneroDTO dto);
    void delete(Long id);
}
