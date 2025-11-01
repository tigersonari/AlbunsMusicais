package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import topicosAlbum.dto.*;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Artista;
import topicosAlbum.model.Empresa;
import topicosAlbum.model.GrupoMusical;
import topicosAlbum.repository.ArtistaRepository;
import topicosAlbum.repository.EmpresaRepository;
import topicosAlbum.repository.GrupoMusicalRepository;

@ApplicationScoped
public class GrupoMusicalServiceImpl implements GrupoMusicalService {

    @Inject GrupoMusicalRepository grupoRepository;
    @Inject ArtistaRepository artistaRepository;
    @Inject EmpresaRepository empresaRepository;

    @Override
    public List<GrupoMusicalResponseDTO> findAll() {
        return grupoRepository.listAll().stream()
                .map(GrupoMusicalResponseDTO::valueOf)
                .toList();
    }

    @Override
    public GrupoMusicalResponseDTO findById(Long id) {
        GrupoMusical g = grupoRepository.findById(id);
        if (g == null)
            throw ValidationException.of("id", "Grupo não encontrado.");
        return GrupoMusicalResponseDTO.valueOf(g);
    }

    @Override @Transactional
    public GrupoMusicalResponseDTO create(@Valid GrupoMusicalDTO dto) {
        GrupoMusical g = new GrupoMusical();
        updateEntity(g, dto);
        grupoRepository.persist(g);
        return GrupoMusicalResponseDTO.valueOf(g);
    }

    @Override @Transactional
    public void update(Long id, @Valid GrupoMusicalDTO dto) {
        GrupoMusical g = grupoRepository.findById(id);
        if (g == null)
            throw ValidationException.of("id", "Grupo não encontrado.");
        updateEntity(g, dto);
    }

    private void updateEntity(GrupoMusical g, GrupoMusicalDTO dto) {
        g.setNomeGrupo(dto.nomeGrupo());
        g.setDataInicio(dto.dataInicio());
        g.setDataTermino(dto.dataTermino());

        // Empresa (herdada)
        Empresa empresa = empresaRepository.findById(dto.idEmpresa());
        if (empresa == null)
            throw ValidationException.of("empresa", "Empresa não encontrada.");
        g.setEmpresa(empresa);

        // Membros
        List<Artista> membros = dto.idsArtistas().stream()
                .map(id -> {
                    Artista a = artistaRepository.findById(id);
                    if (a == null)
                        throw ValidationException.of("artista", "Artista não encontrado: " + id);
                    return a;
                })
                .toList();

        g.setMembros(membros);
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!grupoRepository.deleteById(id))
            throw ValidationException.of("id", "Grupo não encontrado.");
    }

    // -------- Consultas --------

    @Override
    public List<GrupoMusicalResponseDTO> findByNomeGrupo(String nome) {
        return grupoRepository.findByNomeGrupo(nome).stream()
                .map(GrupoMusicalResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<GrupoMusicalResponseDTO> findByEmpresaId(Long idEmpresa) {
        return grupoRepository.findByEmpresaId(idEmpresa).stream()
                .map(GrupoMusicalResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<GrupoMusicalResponseDTO> findAtivos() {
        return grupoRepository.findGruposAtivos().stream()
                .map(GrupoMusicalResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<GrupoMusicalResponseDTO> findInativos() {
        return grupoRepository.findGruposInativos().stream()
                .map(GrupoMusicalResponseDTO::valueOf)
                .toList();
    }

    // -------- Relacionamentos --------

    @Override
    public List<ArtistaResponseDTO> findMembrosByGrupoId(Long idGrupo) {
        return grupoRepository.findMembrosByGrupoId(idGrupo).stream()
                .map(ArtistaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<AlbumResponseDTO> findAlbunsByGrupoId(Long idGrupo) {
        return grupoRepository.findAlbunsByGrupoId(idGrupo).stream()
                .map(AlbumResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<AlbumResponseDTO> findAlbunsComParticipacaoByGrupoId(Long idGrupo) {
        return grupoRepository.findAlbunsComParticipacaoByGrupoId(idGrupo).stream()
                .map(AlbumResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<FaixaResponseDTO> findFaixasByGrupoId(Long idGrupo) {
        return grupoRepository.findFaixasByGrupoId(idGrupo).stream()
                .map(FaixaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ComposicaoResponseDTO> findComposicoesDoGrupo(Long idGrupo) {
        return grupoRepository.findComposicoesDoGrupoByGrupoId(idGrupo).stream()
                .map(ComposicaoResponseDTO::valueOf)
                .toList();
    }
}
