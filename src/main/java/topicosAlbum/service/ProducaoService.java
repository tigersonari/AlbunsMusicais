package topicosAlbum.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import topicosAlbum.dto.ProducaoDTO;
import topicosAlbum.dto.ProducaoResponseDTO;

public interface ProducaoService {
    List<ProducaoResponseDTO> findAll();
    ProducaoResponseDTO findById(Long id);
    ProducaoResponseDTO create(@Valid ProducaoDTO dto);
    void update(Long id, @Valid ProducaoDTO dto);
    void delete(Long id);

    List<ProducaoResponseDTO> findByProdutor(String produtor);
    List<ProducaoResponseDTO> findByEngenheiroGravacao(String nome);
    List<ProducaoResponseDTO> findByEngenheiroMixagem(String nome);
    List<ProducaoResponseDTO> findByEngenheiroMasterizacao(String nome);
    List<ProducaoResponseDTO> findByDataProducao(LocalDate data);
    List<ProducaoResponseDTO> findByPeriodoProducao(LocalDate inicio, LocalDate fim);
    List<ProducaoResponseDTO> findByEmpresa(Long idEmpresa);
    ProducaoResponseDTO findByAlbum(Long idAlbum);
}
