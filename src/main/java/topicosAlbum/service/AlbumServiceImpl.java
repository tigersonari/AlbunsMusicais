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

    @Override
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

        // formato
    Formato formato = null;
    try {
        formato = Formato.valueOf(dto.idFormato());
        } catch (Exception e) {
            throw ValidationException.of("formato", "Formato inválido.");
    }
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

    /* */
    @Override
public List<AlbumResponseDTO> findByFormato(Long idFormato) {
    Formato formato;

    try {
        formato = Formato.valueOf(idFormato); // converte Long → Enum
    } catch (Exception e) {
        throw ValidationException.of("formato", "Formato inválido.");
    }

    return albumRepository.find("formato", formato)
            .list()
            .stream()
            .map(AlbumResponseDTO::valueOf)
            .toList();
}

    /* */

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
}
