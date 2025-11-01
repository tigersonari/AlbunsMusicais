package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.ParticipacaoDTO;
import topicosAlbum.dto.ParticipacaoResponseDTO;

public interface ParticipacaoService {
    List<ParticipacaoResponseDTO> findAll();
    ParticipacaoResponseDTO findById(Long id);
    ParticipacaoResponseDTO create(@Valid ParticipacaoDTO dto);
    void update(Long id, @Valid ParticipacaoDTO dto);
    void delete(Long id);

    List<ParticipacaoResponseDTO> findByFaixaId(Long idFaixa);
    List<ParticipacaoResponseDTO> findByProjetoMusicalId(Long idProjetoMusical);
    List<ParticipacaoResponseDTO> findByPapel(String papel);
    List<ParticipacaoResponseDTO> findPrincipais();
    List<ParticipacaoResponseDTO> findByAlbumId(Long idAlbum);
}
