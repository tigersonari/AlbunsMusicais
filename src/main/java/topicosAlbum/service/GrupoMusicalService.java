package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.GrupoMusicalDTO;
import topicosAlbum.dto.GrupoMusicalResponseDTO;
import topicosAlbum.dto.ArtistaResponseDTO;
import topicosAlbum.dto.AlbumResponseDTO;
import topicosAlbum.dto.FaixaResponseDTO;
import topicosAlbum.dto.ComposicaoResponseDTO;

public interface GrupoMusicalService {

    List<GrupoMusicalResponseDTO> findAll();
    GrupoMusicalResponseDTO findById(Long id);
    GrupoMusicalResponseDTO create(@Valid GrupoMusicalDTO dto);
    void update(Long id, @Valid GrupoMusicalDTO dto);
    void delete(Long id);

    // Consultas específicas
    List<GrupoMusicalResponseDTO> findByNomeGrupo(String nome);
    List<GrupoMusicalResponseDTO> findByEmpresaId(Long idEmpresa);
    List<GrupoMusicalResponseDTO> findAtivos();
    List<GrupoMusicalResponseDTO> findInativos();

    // Relacionamentos
    List<ArtistaResponseDTO> findMembrosByGrupoId(Long idGrupo);
    List<AlbumResponseDTO> findAlbunsByGrupoId(Long idGrupo);
    List<AlbumResponseDTO> findAlbunsComParticipacaoByGrupoId(Long idGrupo);
    List<FaixaResponseDTO> findFaixasByGrupoId(Long idGrupo);
    List<ComposicaoResponseDTO> findComposicoesDoGrupo(Long idGrupo);
}
