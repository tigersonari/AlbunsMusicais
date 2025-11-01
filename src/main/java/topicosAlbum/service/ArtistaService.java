package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.ArtistaDTO;
import topicosAlbum.dto.ArtistaResponseDTO;
import topicosAlbum.dto.AlbumResponseDTO;
import topicosAlbum.dto.GrupoMusicalResponseDTO;
import topicosAlbum.dto.FaixaResponseDTO;
import topicosAlbum.dto.ComposicaoResponseDTO;

public interface ArtistaService {

    List<ArtistaResponseDTO> findAll();
    ArtistaResponseDTO findById(Long id);
    ArtistaResponseDTO create(@Valid ArtistaDTO dto);
    void update(Long id, @Valid ArtistaDTO dto);
    void delete(Long id);

    List<ArtistaResponseDTO> findByNomeArtistico(String nomeArtistico);
    List<ArtistaResponseDTO> findByNomeCompleto(String nomeCompleto);
    List<ArtistaResponseDTO> findByNacionalidade(String nacionalidade);
    List<ArtistaResponseDTO> findByFuncaoPrincipal(String funcaoPrincipal);
    List<ArtistaResponseDTO> findByEmpresaId(Long idEmpresa);

    List<GrupoMusicalResponseDTO> findGruposByArtistaId(Long idArtista);
    List<AlbumResponseDTO> findAlbunsPrincipaisByArtistaId(Long idArtista);
    List<AlbumResponseDTO> findAlbunsComParticipacaoByArtistaId(Long idArtista);
    List<FaixaResponseDTO> findFaixasParticipadasByArtistaId(Long idArtista);
    List<ComposicaoResponseDTO> findComposicoesByArtistaId(Long idArtista);
    List<FaixaResponseDTO> findTodasFaixasRelacionadas(Long idArtista);
}
