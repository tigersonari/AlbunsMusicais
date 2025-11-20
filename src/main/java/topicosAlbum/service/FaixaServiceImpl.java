package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.FaixaDTO;
import topicosAlbum.dto.FaixaResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Album;
import topicosAlbum.model.Composicao;
import topicosAlbum.model.Faixa;
import topicosAlbum.model.Genero;
import topicosAlbum.model.TipoVersao;
import topicosAlbum.repository.AlbumRepository;
import topicosAlbum.repository.ComposicaoRepository;
import topicosAlbum.repository.FaixaRepository;
import topicosAlbum.repository.GeneroRepository;

@ApplicationScoped
public class FaixaServiceImpl implements FaixaService {

    @Inject FaixaRepository faixaRepository;
    @Inject AlbumRepository albumRepository;
    @Inject GeneroRepository generoRepository;
    @Inject ComposicaoRepository composicaoRepository;

    @Override
    public List<FaixaResponseDTO> findAll() {
        return faixaRepository.listAll().stream().map(FaixaResponseDTO::valueOf).toList();
    }

    @Override
    public FaixaResponseDTO findById(Long id) {
        Faixa f = faixaRepository.findById(id);
        if (f == null)
            throw ValidationException.of("id", "Faixa não encontrada.");
        return FaixaResponseDTO.valueOf(f);
    }

    @Override
    public List<FaixaResponseDTO> findByTitulo(String titulo) {
        return faixaRepository.findByTitulo(titulo)
                .stream().map(FaixaResponseDTO::valueOf).toList();
    }

    @Override
    public List<FaixaResponseDTO> findByAlbum(Long idAlbum) {
        return faixaRepository.findByAlbumId(idAlbum)
                .stream().map(FaixaResponseDTO::valueOf).toList();
    }

    @Override
    public List<FaixaResponseDTO> findByArtistaParticipante(Long idProjeto) {
        return faixaRepository.findByParticipacaoArtistaId(idProjeto)
                .stream()
                .map(FaixaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<FaixaResponseDTO> findByCompositor(Long idProjeto) {
        return faixaRepository.findByArtistaCompositorId(idProjeto)
                .stream().map(FaixaResponseDTO::valueOf).toList();
    }

    @Override
    public List<FaixaResponseDTO> findByGenero(Long idGenero) {
        return faixaRepository.findByGeneroId(idGenero)
                .stream().map(FaixaResponseDTO::valueOf).toList();
    }

    @Override
    public List<FaixaResponseDTO> findByIdioma(String idioma) {
        return faixaRepository.findByIdioma(idioma)
                .stream().map(FaixaResponseDTO::valueOf).toList();
    }

    @Override
    public List<FaixaResponseDTO> findByTipoVersao(Long idTipoVersao) {
        TipoVersao tipoVersao = toTipoVersao(idTipoVersao);

        return faixaRepository.findByTipoVersao(tipoVersao)
                .stream()
                .map(FaixaResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public FaixaResponseDTO create(FaixaDTO dto) {
        Faixa f = new Faixa();
        updateData(f, dto);
        faixaRepository.persist(f);
        return FaixaResponseDTO.valueOf(f);
    }

    @Override
    @Transactional
    public void update(Long id, FaixaDTO dto) {
        Faixa f = faixaRepository.findById(id);
        if (f == null)
            throw ValidationException.of("id", "Faixa não encontrada.");
        updateData(f, dto);
    }

    private void updateData(Faixa f, FaixaDTO dto) {
        f.setTitulo(dto.titulo());
        f.setNumeroFaixa(dto.numeroFaixa());
        f.setDuracao(dto.duracao());
        f.setIdioma(dto.idioma());

        // TipoVersao (enum) 
        TipoVersao tipoVersao = toTipoVersao(dto.idTipoVersao());
        f.setTipoVersao(tipoVersao);

        Album album = albumRepository.findById(dto.idAlbum());
        if (album == null)
            throw ValidationException.of("album", "Álbum não encontrado.");
        f.setAlbum(album);

        Genero g = generoRepository.findById(dto.idGenero());
        if (g == null)
            throw ValidationException.of("genero", "Gênero não encontrado.");
        f.setGenero(g);

        Composicao c = composicaoRepository.findById(dto.idComposicao());
        if (c == null)
            throw ValidationException.of("composicao", "Composição não encontrada.");
        f.setComposicao(c);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!faixaRepository.deleteById(id))
            throw ValidationException.of("id", "Faixa não encontrada.");
    }

    // ---------- helper para enum ----------

    private TipoVersao toTipoVersao(Long idTipoVersao) {
        if (idTipoVersao == null)
            throw ValidationException.of("tipoVersao", "TipoVersao inválido.");

        TipoVersao[] values = TipoVersao.values();
        int index = idTipoVersao.intValue() - 1; // assumindo IDs 1..n

        if (index < 0 || index >= values.length)
            throw ValidationException.of("tipoVersao", "TipoVersao inválido.");

        return values[index];
    }
}
