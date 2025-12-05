package topicosAlbum.service;

import java.util.List;

import topicosAlbum.dto.EnderecoDTO;
import topicosAlbum.dto.EnderecoResponseDTO;

public interface EnderecoService {

    List<EnderecoResponseDTO> findByUsuario(Long idUsuario);

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO create(EnderecoDTO dto);

    void update(Long id, EnderecoDTO dto);

    void delete(Long id);
}
