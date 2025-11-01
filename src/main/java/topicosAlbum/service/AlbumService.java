package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.AlbumDTO;
import topicosAlbum.dto.AlbumResponseDTO;

public interface AlbumService {
    
    List<AlbumResponseDTO> findAll();
    AlbumResponseDTO findById(Long id);
    List<AlbumResponseDTO> findByTitulo(String titulo);
    
    AlbumResponseDTO create(@Valid AlbumDTO dto);
    void update(Long id, @Valid AlbumDTO dto);
    void delete(Long id);

    // consultas específicas do domínio musical
    List<AlbumResponseDTO> findByAnoLancamento(int ano);
    List<AlbumResponseDTO> findByFormato(Long idFormato);
    List<AlbumResponseDTO> findByArtistaPrincipal(Long idProjetoMusical);
    List<AlbumResponseDTO> findColaboracoesEntre(Long idProjeto1, Long idProjeto2);
    List<AlbumResponseDTO> findByEmpresaProducao(Long idEmpresa);
    List<AlbumResponseDTO> findByProdutor(String produtor);
    List<AlbumResponseDTO> findByEngenheiroMixagem(String nome);
    List<AlbumResponseDTO> findByEngenheiroMasterizacao(String nome);
    List<AlbumResponseDTO> findByGenero(Long idGenero);
    List<AlbumResponseDTO> findByParticipacao(Long idProjetoMusical);
    List<AlbumResponseDTO> findByFaixaTitulo(String tituloFaixa);
}
