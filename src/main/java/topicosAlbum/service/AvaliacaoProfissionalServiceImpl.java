package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.AvaliacaoProfissionalDTO;
import topicosAlbum.dto.AvaliacaoProfissionalResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Album;
import topicosAlbum.model.AvaliacaoProfissional;
import topicosAlbum.repository.AlbumRepository;
import topicosAlbum.repository.AvaliacaoProfissionalRepository;

@ApplicationScoped
public class AvaliacaoProfissionalServiceImpl implements AvaliacaoProfissionalService {

    @Inject AvaliacaoProfissionalRepository repo;
    @Inject AlbumRepository albumRepo;

    @Override
    public List<AvaliacaoProfissionalResponseDTO> findAll() {
        return repo.listAll().stream()
            .map(a -> AvaliacaoProfissionalResponseDTO.valueOf(a, null))
            .toList();
    }

    @Override
    public AvaliacaoProfissionalResponseDTO findById(Long id) {
        AvaliacaoProfissional a = repo.findById(id);
        if (a == null) 
            throw ValidationException.of("id", "Avaliação não encontrada.");
        return AvaliacaoProfissionalResponseDTO.valueOf(a, null);
    }

    @Override
    @Transactional
    public AvaliacaoProfissionalResponseDTO create(AvaliacaoProfissionalDTO dto) {
        Album album = albumRepo.findById(dto.idAlbum());
        if (album == null)
            throw ValidationException.of("album", "Álbum não encontrado.");

        AvaliacaoProfissional a = new AvaliacaoProfissional();
        a.setAvaliador(dto.avaliador());
        a.setComentario(dto.comentario());
        a.setNota(dto.nota());

        album.getAvaliacaoProfissional().add(a); // <- unidirecional, album conhece avaliação
        albumRepo.persist(album);

        return AvaliacaoProfissionalResponseDTO.valueOf(a, album);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AvaliacaoProfissional a = repo.findById(id);
        if (a == null)
            throw ValidationException.of("id", "Avaliação não encontrada.");

        repo.delete(a);
    }

    @Override
    public List<AvaliacaoProfissionalResponseDTO> findByAlbumId(Long idAlbum) {
        return repo.findByAlbumId(idAlbum)
            .stream()
            .map(a -> AvaliacaoProfissionalResponseDTO.valueOf(a, null))
            .toList();
    }

    @Override
    public List<AvaliacaoProfissionalResponseDTO> findByAvaliador(String avaliador) {
        return repo.findByAvaliador(avaliador)
            .stream().map(a -> AvaliacaoProfissionalResponseDTO.valueOf(a, null)).toList();
    }

    @Override
    public List<AvaliacaoProfissionalResponseDTO> findByAlbumAndNota(Long idAlbum, double nota) {
        return repo.findByAlbumAndNota(idAlbum, nota)
            .stream().map(a -> AvaliacaoProfissionalResponseDTO.valueOf(a, null)).toList();
    }

    @Override
    public List<AvaliacaoProfissionalResponseDTO> findByAlbumAndNotaMinima(Long idAlbum, double notaMinima) {
        return repo.findByAlbumAndNotaMinima(idAlbum, notaMinima)
            .stream().map(a -> AvaliacaoProfissionalResponseDTO.valueOf(a, null)).toList();
    }
}
