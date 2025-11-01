package topicosAlbum.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import topicosAlbum.dto.ProducaoDTO;
import topicosAlbum.dto.ProducaoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Empresa;
import topicosAlbum.model.Producao;
import topicosAlbum.repository.EmpresaRepository;
import topicosAlbum.repository.ProducaoRepository;

@ApplicationScoped
public class ProducaoServiceImpl implements ProducaoService {

    @Inject
    ProducaoRepository producaoRepository;

    @Inject
    EmpresaRepository empresaRepository;

    @Override
    public List<ProducaoResponseDTO> findAll() {
        return producaoRepository.listAll()
                .stream().map(ProducaoResponseDTO::valueOf).toList();
    }

    @Override
    public ProducaoResponseDTO findById(Long id) {
        Producao p = producaoRepository.findById(id);
        if (p == null)
            throw ValidationException.of("id", "Produção não encontrada.");
        return ProducaoResponseDTO.valueOf(p);
    }

    @Override
    @Transactional
    public ProducaoResponseDTO create(@Valid ProducaoDTO dto) {
        Producao p = new Producao();
        updateEntity(p, dto);
        producaoRepository.persist(p);
        return ProducaoResponseDTO.valueOf(p);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid ProducaoDTO dto) {
        Producao p = producaoRepository.findById(id);
        if (p == null)
            throw ValidationException.of("id", "Produção não encontrada.");
        updateEntity(p, dto);
    }

    private void updateEntity(Producao p, ProducaoDTO dto) {
        p.setProdutor(dto.produtor());
        p.setEngenheiroGravacao(dto.engenheiroGravacao());
        p.setEngenheiroMixagem(dto.engenheiroMixagem());
        p.setEngenheiroMasterizacao(dto.engenheiroMasterizacao());
        p.setDataProducao(dto.dataProducao());

        Empresa empresa = empresaRepository.findById(dto.idEmpresa());
        if (empresa == null)
            throw ValidationException.of("empresa", "Empresa não encontrada.");
        p.setEmpresa(empresa);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!producaoRepository.deleteById(id))
            throw ValidationException.of("id", "Produção não encontrada.");
    }

   

    @Override
    public List<ProducaoResponseDTO> findByProdutor(String produtor) {
        return producaoRepository.findByProdutor(produtor)
            .stream().map(ProducaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ProducaoResponseDTO> findByEngenheiroGravacao(String nome) {
        return producaoRepository.findByEngenheiroGravacao(nome)
            .stream().map(ProducaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ProducaoResponseDTO> findByEngenheiroMixagem(String nome) {
        return producaoRepository.findByEngenheiroMixagem(nome)
            .stream().map(ProducaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ProducaoResponseDTO> findByEngenheiroMasterizacao(String nome) {
        return producaoRepository.findByEngenheiroMasterizacao(nome)
            .stream().map(ProducaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ProducaoResponseDTO> findByDataProducao(LocalDate data) {
        return producaoRepository.findByDataProducao(data)
            .stream().map(ProducaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ProducaoResponseDTO> findByPeriodoProducao(LocalDate inicio, LocalDate fim) {
        return producaoRepository.findByPeriodoProducao(inicio, fim)
            .stream().map(ProducaoResponseDTO::valueOf).toList();
    } /*dúvidas, verificar depois */

    @Override
    public List<ProducaoResponseDTO> findByEmpresa(Long idEmpresa) {
        return producaoRepository.findByEmpresaId(idEmpresa)
            .stream().map(ProducaoResponseDTO::valueOf).toList();
    }

    @Override
    public ProducaoResponseDTO findByAlbum(Long idAlbum) {
        try {
            Producao p = producaoRepository.findByAlbumId(idAlbum);
                return ProducaoResponseDTO.valueOf(p);
        } catch (Exception e) {
            throw ValidationException.of("album", "Nenhuma produção encontrada para o álbum informado.");
    }
}

    
}
