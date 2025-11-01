package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.AvaliacaoProfissionalDTO;
import topicosAlbum.dto.AvaliacaoProfissionalResponseDTO;

public interface AvaliacaoProfissionalService {

    List<AvaliacaoProfissionalResponseDTO> findAll();
    AvaliacaoProfissionalResponseDTO findById(Long id);

    AvaliacaoProfissionalResponseDTO create(@Valid AvaliacaoProfissionalDTO dto);
    void delete(Long id);

    List<AvaliacaoProfissionalResponseDTO> findByAlbumId(Long idAlbum);
    List<AvaliacaoProfissionalResponseDTO> findByAvaliador(String avaliador);
    List<AvaliacaoProfissionalResponseDTO> findByAlbumAndNota(Long idAlbum, double nota);
    List<AvaliacaoProfissionalResponseDTO> findByAlbumAndNotaMinima(Long idAlbum, double notaMinima);
}
