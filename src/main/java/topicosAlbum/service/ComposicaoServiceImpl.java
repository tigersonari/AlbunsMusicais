package topicosAlbum.service;

import java.time.LocalDate;
import java.util.List;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.dto.ComposicaoDTO;
import topicosAlbum.dto.ComposicaoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.*;
import topicosAlbum.repository.*;

@ApplicationScoped
public class ComposicaoServiceImpl implements ComposicaoService {

    @Inject ComposicaoRepository composicaoRepository;
    @Inject ArtistaRepository artistaRepository;
    @Inject GrupoMusicalRepository grupoRepository;

    @Override
    public List<ComposicaoResponseDTO> findAll() {
        return composicaoRepository.listAll()
            .stream().map(ComposicaoResponseDTO::valueOf).toList();
    }

    @Override
    public ComposicaoResponseDTO findById(Long id) {
        Composicao c = composicaoRepository.findById(id);
        if (c == null) throw ValidationException.of("id", "Composição não encontrada.");
        return ComposicaoResponseDTO.valueOf(c);
    }

    @Override
    @Transactional
    public ComposicaoResponseDTO create(ComposicaoDTO dto) {
        Composicao c = new Composicao();
        updateEntity(c, dto);
        composicaoRepository.persist(c);
        return ComposicaoResponseDTO.valueOf(c);
    }

    @Override
    @Transactional
    public void update(Long id, ComposicaoDTO dto) {
        Composicao c = composicaoRepository.findById(id);
        if (c == null) throw ValidationException.of("id", "Composição não encontrada.");
        updateEntity(c, dto);
    }

    private void updateEntity(Composicao c, ComposicaoDTO dto) {
        c.setData(dto.data());

        List<ProjetoMusical> projetos = dto.idsProjetoMusical().stream()
            .map(id -> {
                ProjetoMusical p = artistaRepository.findById(id);
                if (p == null) p = grupoRepository.findById(id);
                if (p == null) throw ValidationException.of("projeto", "Compositor não encontrado: " + id);
                return p;
            }).toList();

        c.setProjetoMusical(projetos);
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!composicaoRepository.deleteById(id))
            throw ValidationException.of("id", "Composição não encontrada.");
    }

    @Override
    public List<ComposicaoResponseDTO> findByData(LocalDate data) {
        return composicaoRepository.findByData(data)
                .stream().map(ComposicaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ComposicaoResponseDTO> findByProjetoMusical(Long idPM) {
        return composicaoRepository.findByArtistaOuGrupo(idPM)
                .stream().map(ComposicaoResponseDTO::valueOf).toList();
    }

    @Override
    public ComposicaoResponseDTO findByFaixaId(Long idFaixa) {
        return ComposicaoResponseDTO.valueOf(composicaoRepository.findByFaixaId(idFaixa));
    }
}
