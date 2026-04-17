package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import topicosAlbum.dto.AlbumDTO;
import topicosAlbum.dto.AlbumResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Album;
import topicosAlbum.model.Formato;
import topicosAlbum.model.Genero;
import topicosAlbum.model.Producao;
import topicosAlbum.model.ProjetoMusical;
import topicosAlbum.repository.AlbumRepository;
import topicosAlbum.repository.ArtistaRepository;
import topicosAlbum.repository.GeneroRepository;
import topicosAlbum.repository.GrupoMusicalRepository;
import topicosAlbum.repository.ProducaoRepository;

@ApplicationScoped
public class AlbumServiceImpl implements AlbumService {

    @Inject AlbumRepository albumRepository;
    @Inject ProducaoRepository producaoRepository;
    @Inject ArtistaRepository artistaRepository;
    @Inject GrupoMusicalRepository grupoRepository;
    @Inject GeneroRepository generoRepository;
    

    // ========================
    // PAGINAÇÃO + LISTAGEM
    // ========================
    @Override
    public List<AlbumResponseDTO> findAll(int page, int pageSize) {
        return albumRepository.findAll()
                .page(page, pageSize)
                .list()
                .stream()
                .map(AlbumResponseDTO::valueOf)
                .toList();
    }

    @Override
    public long count() {
        return albumRepository.count();
    }

    @Override
    public AlbumResponseDTO findById(Long id) {
        Album album = albumRepository.findById(id);
        if (album == null)
            throw ValidationException.of("id", "Álbum não encontrado.");
        return AlbumResponseDTO.valueOf(album);
    }

    @Override
    public List<AlbumResponseDTO> findByTitulo(String titulo, int page, int pageSize) {
        return albumRepository.findByTitulo(titulo, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    // ========================
    // CRUD
    // ========================
    @Override
    @Transactional
    public AlbumResponseDTO create(@Valid AlbumDTO dto) {
        Album album = new Album();
        updateEntityFromDTO(album, dto);
        albumRepository.persist(album);
        return AlbumResponseDTO.valueOf(album);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid AlbumDTO dto) {
        Album album = albumRepository.findById(id);
        if (album == null)
            throw ValidationException.of("id", "Álbum não encontrado.");
        updateEntityFromDTO(album, dto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!albumRepository.deleteById(id))
            throw ValidationException.of("id", "Álbum não encontrado.");
    }

    // ========================
    // UPDATE ENTITY
    // ========================
    private void updateEntityFromDTO(Album album, AlbumDTO dto) {
        album.setTitulo(dto.titulo());
        album.setDescricao(dto.descricao());
        album.setLancamento(dto.lancamento());

        Formato formato = toFormato(dto.idFormato());
        album.setFormato(formato);

        Producao p = producaoRepository.findById(dto.idProducao());
        if (p == null)
            throw ValidationException.of("producao", "Produção não encontrada.");
        album.setProducao(p);

        List<ProjetoMusical> projetos = dto.idProjetoMusical().stream()
            .map(id -> {
                ProjetoMusical pm = artistaRepository.findById(id);
                if (pm == null) pm = grupoRepository.findById(id);
                if (pm == null)
                    throw ValidationException.of("projetoMusical", "Projeto não encontrado: " + id);
                return pm;
            }).toList();
        album.setProjetoMusical(projetos);

        List<Genero> generos = dto.idGenero().stream()
            .map(id -> {
                Genero g = generoRepository.findById(id);
                if (g == null)
                    throw ValidationException.of("genero", "Gênero não encontrado: " + id);
                return g;
            }).toList();
        album.setGeneros(generos);
    }

    // ========================
    // CONSULTAS COM PAGINAÇÃO
    // ========================
    @Override
    public List<AlbumResponseDTO> findByAnoLancamento(int ano, int page, int pageSize) {
        return albumRepository.findByAnoLancamento(ano, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByFormato(Long idFormato, int page, int pageSize) {
        return albumRepository.findByFormato(toFormato(idFormato), page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByArtistaPrincipal(Long id, int page, int pageSize) {
        return albumRepository.findByProjetoMusicalId(id, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findColaboracoesEntre(Long id1, Long id2, int page, int pageSize) {
        return albumRepository.findColaboracoesEntre(id1, id2, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByEmpresaProducao(Long id, int page, int pageSize) {
        return albumRepository.findByEmpresaProducao(id, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByProdutor(String nome, int page, int pageSize) {
        return albumRepository.findByProdutor(nome, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByEngenheiroMixagem(String nome, int page, int pageSize) {
        return albumRepository.findByEngenheiroMixagem(nome, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByEngenheiroMasterizacao(String nome, int page, int pageSize) {
        return albumRepository.findByEngenheiroMasterizacao(nome, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByGenero(Long id, int page, int pageSize) {
        return albumRepository.findByGeneroId(id, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByParticipacao(Long id, int page, int pageSize) {
        return albumRepository.findByParticipacao(id, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByFaixaTitulo(String titulo, int page, int pageSize) {
        return albumRepository.findByFaixaTitulo(titulo, page, pageSize)
                .stream().map(AlbumResponseDTO::valueOf).toList();
    }

    // ========================
    // ENUM
    // ========================
    private Formato toFormato(Long idFormato) {
        if (idFormato == null)
            throw ValidationException.of("formato", "Formato inválido.");

        Formato[] values = Formato.values();
        int index = idFormato.intValue() - 1;

        if (index < 0 || index >= values.length)
            throw ValidationException.of("formato", "Formato inválido.");

        return values[index];
    }

    /*@Override
    public List<AlbumResponseDTO> findAll() {
        return albumRepository.listAll().stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public AlbumResponseDTO findById(Long id) {
        Album album = albumRepository.findById(id);
        if (album == null)
            throw ValidationException.of("id", "Álbum não encontrado.");
        return AlbumResponseDTO.valueOf(album);
    }

    @Override
    public List<AlbumResponseDTO> findByTitulo(String titulo) {
        return albumRepository.findByTitulo(titulo).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    @Transactional
    public AlbumResponseDTO create(@Valid AlbumDTO dto) {
        Album album = new Album();
        updateEntityFromDTO(album, dto);
        albumRepository.persist(album);
        return AlbumResponseDTO.valueOf(album);
    }
// COUNT
    @Override
public long count() {
    return albumRepository.count();
}
//COUNT 

// PAGINAÇÃO
@Override
public List<AlbumResponseDTO> findAll(int page, int pageSize) {
    return albumRepository.findAll()
            .page(page, pageSize)
            .list()
            .stream()
            .map(AlbumResponseDTO::valueOf)
            .toList();
}
// PAGINAÇÃO

    @Override
    @Transactional
    public void update(Long id, @Valid AlbumDTO dto) {
        Album album = albumRepository.findById(id);
        if (album == null)
            throw ValidationException.of("id", "Álbum não encontrado.");
        updateEntityFromDTO(album, dto);
    }

    private void updateEntityFromDTO(Album album, AlbumDTO dto) {
        album.setTitulo(dto.titulo());
        album.setDescricao(dto.descricao());
        album.setLancamento(dto.lancamento());

        // Formato (enum) 
        Formato formato = toFormato(dto.idFormato());
        album.setFormato(formato);

        // produção
        Producao p = producaoRepository.findById(dto.idProducao());
        if (p == null)
            throw ValidationException.of("producao", "Produção não encontrada.");
        album.setProducao(p);

        // ProjetoMusical (Artistas/Grupos)
        List<ProjetoMusical> projetos = dto.idProjetoMusical().stream()
            .map(id -> {
                ProjetoMusical pm = artistaRepository.findById(id);
                if (pm == null) pm = grupoRepository.findById(id);
                if (pm == null)
                    throw ValidationException.of("projetoMusical", "Projeto musical não encontrado: " + id);
                return pm;
            }).toList();
        album.setProjetoMusical(projetos);

        // gêneros
        List<Genero> generos = dto.idGenero().stream()
            .map(id -> {
                Genero g = generoRepository.findById(id);
                if (g == null)
                    throw ValidationException.of("genero", "Gênero não encontrado: " + id);
                return g;
            }).toList();
        album.setGeneros(generos);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!albumRepository.deleteById(id))
            throw ValidationException.of("id", "Álbum não encontrado.");
    }

    // Consultas específicas
    @Override
    public List<AlbumResponseDTO> findByAnoLancamento(int ano) {
        return albumRepository.findByAnoLancamento(ano).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByFormato(Long idFormato) {
        Formato formato = toFormato(idFormato);

        return albumRepository.findByFormato(formato)
                .stream()
                .map(AlbumResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<AlbumResponseDTO> findByArtistaPrincipal(Long idProjetoMusical) {
        return albumRepository.findByProjetoMusicalId(idProjetoMusical).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findColaboracoesEntre(Long idProjetoMusical1, Long idProjetoMusical2) {
        return albumRepository.findColaboracoesEntre(idProjetoMusical1, idProjetoMusical2).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByEmpresaProducao(Long idEmpresa) {
        return albumRepository.findByEmpresaProducao(idEmpresa).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByProdutor(String produtor) {
        return albumRepository.findByProdutor(produtor).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByEngenheiroMixagem(String nome) {
        return albumRepository.findByEngenheiroMixagem(nome).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByEngenheiroMasterizacao(String nome) {
        return albumRepository.findByEngenheiroMasterizacao(nome).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByGenero(Long idGenero) {
        return albumRepository.findByGeneroId(idGenero).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByParticipacao(Long idProjetoMusical) {
        return albumRepository.findByParticipacao(idProjetoMusical).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    @Override
    public List<AlbumResponseDTO> findByFaixaTitulo(String tituloFaixa) {
        return albumRepository.findByFaixaTitulo(tituloFaixa).stream().map(AlbumResponseDTO::valueOf).toList();
    }

    // ---------- helper para enum ----------

    private Formato toFormato(Long idFormato) {
        if (idFormato == null)
            throw ValidationException.of("formato", "Formato inválido.");

        Formato[] values = Formato.values();
        int index = idFormato.intValue() - 1; // assumindo IDs 1..n

        if (index < 0 || index >= values.length)
            throw ValidationException.of("formato", "Formato inválido.");

        return values[index];
    }*/
}
