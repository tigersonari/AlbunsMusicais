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
import topicosAlbum.model.Album;
import topicosAlbum.model.Composicao;
import topicosAlbum.model.Faixa;
import topicosAlbum.model.GrupoMusical;
import topicosAlbum.repository.ArtistaRepository;
import topicosAlbum.repository.EmpresaRepository;

@ApplicationScoped
public class ArtistaServiceImpl implements ArtistaService {

    @Inject ArtistaRepository artistaRepository;
    @Inject EmpresaRepository empresaRepository;

    @Override
    public List<ArtistaResponseDTO> findAll() {
        return artistaRepository.listAll()
                .stream()
                .map(ArtistaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public ArtistaResponseDTO findById(Long id) {
        Artista a = artistaRepository.findById(id);
        if (a == null)
            throw ValidationException.of("id", "Artista não encontrado.");
        return ArtistaResponseDTO.valueOf(a);
    }

    @Override @Transactional
    public ArtistaResponseDTO create(@Valid ArtistaDTO dto) {
        Artista a = new Artista();
        updateEntity(a, dto);
        artistaRepository.persist(a);
        return ArtistaResponseDTO.valueOf(a);
    }

    @Override @Transactional
    public void update(Long id, @Valid ArtistaDTO dto) {
        Artista a = artistaRepository.findById(id);
        if (a == null)
            throw ValidationException.of("id", "Artista não encontrado.");
        updateEntity(a, dto);
    }

    private void updateEntity(Artista a, ArtistaDTO dto) {
        a.setNomeCompleto(dto.nomeCompleto());
        a.setNomeArtistico(dto.nomeArtistico());
        a.setDataNascimento(dto.dataNascimento());
        a.setNacionalidade(dto.nacionalidade());
        a.setFuncaoPrincipal(dto.funcaoPrincipal());

        Empresa empresa = empresaRepository.findById(dto.idEmpresa());
        if (empresa == null)
            throw ValidationException.of("empresa", "Empresa não encontrada.");
        a.setEmpresa(empresa); //  herdado de ProjetoMusical
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!artistaRepository.deleteById(id))
            throw ValidationException.of("id", "Artista não encontrado.");
    }

    //---------- CONSULTAS ----------
    @Override public List<ArtistaResponseDTO> findByNomeArtistico(String n) {
        return artistaRepository.findByNomeArtistico(n).stream().map(ArtistaResponseDTO::valueOf).toList();
    }

    @Override public List<ArtistaResponseDTO> findByNomeCompleto(String n) {
        return artistaRepository.findByNomeCompleto(n).stream().map(ArtistaResponseDTO::valueOf).toList();
    }

    @Override public List<ArtistaResponseDTO> findByNacionalidade(String n) {
        return artistaRepository.findByNacionalidade(n).stream().map(ArtistaResponseDTO::valueOf).toList();
    }

    @Override public List<ArtistaResponseDTO> findByFuncaoPrincipal(String f) {
        return artistaRepository.findByFuncaoPrincipal(f).stream().map(ArtistaResponseDTO::valueOf).toList();
    }

    @Override public List<ArtistaResponseDTO> findByEmpresaId(Long e) {
        return artistaRepository.findByEmpresaId(e).stream().map(ArtistaResponseDTO::valueOf).toList();
    }

    @Override public List<GrupoMusicalResponseDTO> findGruposByArtistaId(Long id) {
        return artistaRepository.findGruposByArtistaId(id).stream().map(GrupoMusicalResponseDTO::valueOf).toList();
    }

    @Override public List<AlbumResponseDTO> findAlbunsPrincipaisByArtistaId(Long id) {
        return artistaRepository.findAlbunsPrincipaisByArtistaId(id).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override public List<AlbumResponseDTO> findAlbunsComParticipacaoByArtistaId(Long id) {
        return artistaRepository.findAlbunsComParticipacaoByArtistaId(id).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override public List<FaixaResponseDTO> findFaixasParticipadasByArtistaId(Long id) {
        return artistaRepository.findFaixasParticipadasByArtistaId(id).stream().map(FaixaResponseDTO::valueOf).toList();
    }

    @Override public List<ComposicaoResponseDTO> findComposicoesByArtistaId(Long id) {
        return artistaRepository.findComposicoesByArtistaId(id).stream().map(ComposicaoResponseDTO::valueOf).toList();
    }

    @Override public List<FaixaResponseDTO> findTodasFaixasRelacionadas(Long id) {
        return artistaRepository.findTodasFaixasRelacionadas(id)
            .stream().filter(Faixa.class::isInstance).map(Faixa.class::cast)
            .map(FaixaResponseDTO::valueOf).toList();
    }
}
