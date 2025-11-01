package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.FaixaDTO;
import topicosAlbum.dto.FaixaResponseDTO;

public interface FaixaService {

    List<FaixaResponseDTO> findAll();
    FaixaResponseDTO findById(Long id);
    List<FaixaResponseDTO> findByTitulo(String titulo);
    List<FaixaResponseDTO> findByAlbum(Long idAlbum);
    List<FaixaResponseDTO> findByArtistaParticipante(Long idProjeto);
    List<FaixaResponseDTO> findByCompositor(Long idProjeto);
    List<FaixaResponseDTO> findByGenero(Long idGenero);
    List<FaixaResponseDTO> findByIdioma(String idioma);
    List<FaixaResponseDTO> findByTipoVersao(Long idTipoVersao);


    FaixaResponseDTO create(@Valid FaixaDTO dto);
    void update(Long id, @Valid FaixaDTO dto);
    void delete(Long id);
}
