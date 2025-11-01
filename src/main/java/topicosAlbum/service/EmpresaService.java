package topicosAlbum.service;

import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.EmpresaDTO;
import topicosAlbum.dto.EmpresaResponseDTO;
import topicosAlbum.dto.ArtistaResponseDTO;
import topicosAlbum.dto.GrupoMusicalResponseDTO;
import topicosAlbum.dto.AlbumResponseDTO;

public interface EmpresaService {
    
    // CRUD
    List<EmpresaResponseDTO> findAll();
    EmpresaResponseDTO findById(Long id);
    EmpresaResponseDTO create(@Valid EmpresaDTO dto);
    void update(Long id, @Valid EmpresaDTO dto);
    void delete(Long id);

    // Buscas básicas
    List<EmpresaResponseDTO> findByNome(String nomeEmpresa);
    EmpresaResponseDTO findByCnpj(String cnpj);
    List<EmpresaResponseDTO> findByLocalizacao(String localizacao);
    List<EmpresaResponseDTO> findByContato(String contato);

    // Domínio
    List<ArtistaResponseDTO> findArtistas(Long idEmpresa);
    List<GrupoMusicalResponseDTO> findGrupos(Long idEmpresa);
    List<AlbumResponseDTO> findAlbunsProduzidos(Long idEmpresa);
    List<AlbumResponseDTO> findAlbunsLancados(Long idEmpresa);
}
