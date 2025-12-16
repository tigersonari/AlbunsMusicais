package topicosAlbum.service;

import java.util.List;

import topicosAlbum.dto.EnderecoDTO;
import topicosAlbum.dto.EnderecoResponseDTO;

public interface EnderecoService {

    // ---------- MÉTODOS ORIGINAIS (mantidos para compatibilidade) ----------

    List<EnderecoResponseDTO> findByUsuario(Long idUsuario);

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO create(EnderecoDTO dto);

    void update(Long id, EnderecoDTO dto);

    void delete(Long id);

    // ---------- MÉTODOS SEGUROS (usados pelo Resource) ----------

    EnderecoResponseDTO findByIdSeguro(Long id, Long idUsuarioToken, boolean isAdmin);

    EnderecoResponseDTO createParaUsuario(EnderecoDTO dto, Long idUsuarioToken);

    void updateSeguro(Long id, EnderecoDTO dto, Long idUsuarioToken);

    void deleteSeguro(Long id, Long idUsuarioToken);
}
