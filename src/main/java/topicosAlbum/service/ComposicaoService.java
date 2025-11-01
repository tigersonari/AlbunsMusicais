package topicosAlbum.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.ComposicaoDTO;
import topicosAlbum.dto.ComposicaoResponseDTO;

public interface ComposicaoService {

    List<ComposicaoResponseDTO> findAll();
    ComposicaoResponseDTO findById(Long id);
    ComposicaoResponseDTO create(@Valid ComposicaoDTO dto);
    void update(Long id, @Valid ComposicaoDTO dto);
    void delete(Long id);

    List<ComposicaoResponseDTO> findByData(LocalDate data);
    List<ComposicaoResponseDTO> findByProjetoMusical(Long idProjetoMusical);
    ComposicaoResponseDTO findByFaixaId(Long idFaixa);
}
