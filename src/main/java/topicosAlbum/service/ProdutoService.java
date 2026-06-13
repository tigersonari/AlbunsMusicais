package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.ProdutoDTO;
import topicosAlbum.dto.ProdutoResponseDTO;

public interface ProdutoService {

    List<ProdutoResponseDTO> findAll();

    ProdutoResponseDTO findById(Long id);

    ProdutoResponseDTO create(@Valid ProdutoDTO dto);

    void update(Long id, @Valid ProdutoDTO dto);

    void delete(Long id);

    ProdutoResponseDTO findByAlbum(Long idAlbum);
}