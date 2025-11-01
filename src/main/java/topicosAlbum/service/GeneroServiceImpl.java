package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.GeneroDTO;
import topicosAlbum.dto.GeneroResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Genero;
import topicosAlbum.repository.GeneroRepository;

@ApplicationScoped
public class GeneroServiceImpl implements GeneroService {

    @Inject
    GeneroRepository repository;

    @Override
    public List<GeneroResponseDTO> findAll() {
        return repository.listAll()
            .stream()
            .map(GeneroResponseDTO::valueOf)
            .toList();
    }

    @Override
    public GeneroResponseDTO findById(Long id) {
        Genero genero = repository.findById(id);
        if (genero == null)
            throw ValidationException.of("id", "Gênero não encontrado.");

        return GeneroResponseDTO.valueOf(genero);
    }

    @Override
    public List<GeneroResponseDTO> findByNome(String nomeGenero) {
        return repository.findByNome(nomeGenero)
            .stream()
            .map(GeneroResponseDTO::valueOf)
            .toList();
    }

    @Override
    public List<GeneroResponseDTO> findByAlbum(Long idAlbum) {
        return repository.findByAlbumId(idAlbum)
            .stream()
            .map(GeneroResponseDTO::valueOf)
            .toList();
    }



    @Override
    @Transactional
    public GeneroResponseDTO create(GeneroDTO dto) {
        validarNome(dto.nomeGenero(), null);

        Genero genero = new Genero();
        genero.setNomeGenero(dto.nomeGenero());
        genero.setDescricao(dto.descricao());

        repository.persist(genero);
        return GeneroResponseDTO.valueOf(genero);
    }

    @Override
    @Transactional
    public void update(Long id, GeneroDTO dto) {
        validarNome(dto.nomeGenero(), id);

        Genero genero = repository.findById(id);
        if (genero == null)
            throw ValidationException.of("id", "Gênero não encontrado.");

        genero.setNomeGenero(dto.nomeGenero());
        genero.setDescricao(dto.descricao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw ValidationException.of("id", "Gênero não encontrado.");
    }

    private void validarNome(String nomeGenero, Long id) {
        List<Genero> existentes = repository.findByNome(nomeGenero);
        
        if (!existentes.isEmpty()) {
            boolean existeOutro = existentes.stream()
                .anyMatch(g -> !g.getId().equals(id));

            if (existeOutro)
                throw ValidationException.of("nomeGenero", "Esse gênero já está cadastrado.");
        }
    }

    
}
