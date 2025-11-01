package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.ParticipacaoDTO;
import topicosAlbum.dto.ParticipacaoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Participacao;
import topicosAlbum.model.ProjetoMusical;
import topicosAlbum.repository.ArtistaRepository;
import topicosAlbum.repository.GrupoMusicalRepository;
import topicosAlbum.repository.ParticipacaoRepository;

@ApplicationScoped
public class ParticipacaoServiceImpl implements ParticipacaoService {

    @Inject ParticipacaoRepository participacaoRepository;
    @Inject ArtistaRepository artistaRepository;
    @Inject GrupoMusicalRepository grupoRepository;

    @Override
    public List<ParticipacaoResponseDTO> findAll() {
        return participacaoRepository.listAll()
            .stream().map(ParticipacaoResponseDTO::valueOf).toList();
    }

    @Override
    public ParticipacaoResponseDTO findById(Long id) {
        Participacao p = participacaoRepository.findById(id);
        if (p == null) throw ValidationException.of("id", "Participação não encontrada.");
        return ParticipacaoResponseDTO.valueOf(p);
    }

    @Override
    @Transactional
    public ParticipacaoResponseDTO create(ParticipacaoDTO dto) {
        Participacao p = new Participacao();
        updateEntity(p, dto);
        participacaoRepository.persist(p);
        return ParticipacaoResponseDTO.valueOf(p);
    }

    @Override
    @Transactional
    public void update(Long id, ParticipacaoDTO dto) {
        Participacao p = participacaoRepository.findById(id);
        if (p == null) throw ValidationException.of("id", "Participação não encontrada.");
        updateEntity(p, dto);
    }

    private void updateEntity(Participacao p, ParticipacaoDTO dto) {
        p.setPapel(dto.papel());
        p.setDestaque(dto.destaque());

        List<ProjetoMusical> projetos = dto.idsProjetoMusical().stream()
            .map(id -> {
                ProjetoMusical pm = artistaRepository.findById(id);
                if (pm == null) pm = grupoRepository.findById(id);
                if (pm == null) throw ValidationException.of("projeto", "Participante não encontrado: " + id);
                return pm;
            }).toList();

        p.setProjetoMusical(projetos);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!participacaoRepository.deleteById(id))
            throw ValidationException.of("id", "Participação não encontrada.");
    }

    @Override
    public List<ParticipacaoResponseDTO> findByFaixaId(Long idFaixa) {
        return participacaoRepository.findByFaixaId(idFaixa)
            .stream().map(ParticipacaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ParticipacaoResponseDTO> findByProjetoMusicalId(Long idProjetoMusical) {
        return participacaoRepository.findByProjetoMusicalId(idProjetoMusical)
            .stream().map(ParticipacaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ParticipacaoResponseDTO> findByPapel(String papel) {
        return participacaoRepository.findByPapel(papel)
            .stream().map(ParticipacaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ParticipacaoResponseDTO> findPrincipais() {
        return participacaoRepository.findPrincipais()
            .stream().map(ParticipacaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<ParticipacaoResponseDTO> findByAlbumId(Long idAlbum) {
        return participacaoRepository.findByAlbumId(idAlbum)
            .stream().map(ParticipacaoResponseDTO::valueOf).toList();
    }
}
