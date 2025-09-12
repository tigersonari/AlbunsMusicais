package topicosAlbum.service;

//@Transactional se usa em service e NÃO em resource, porque a service é que faz a transação com o banco de dados

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.ArtistaDTO;
import topicosAlbum.model.Artista;
import topicosAlbum.repository.ArtistaRepository;

@ApplicationScoped //não esquecer.
public class ArtistaServiceImpl implements ArtistaService {

    @Inject
    ArtistaRepository repository;

    @Override
    public List<Artista> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Artista> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public Artista findById(Long id) {
       return repository.findById(id);
    }

    @Override
    @Transactional
    public Artista create(ArtistaDTO dto) {
        Artista novoArtista = new Artista();
        novoArtista.setNome(dto.nome());
        novoArtista.setDataNascimento(dto.dataNascimento());
        novoArtista.setNacionalidade(dto.nacionalidade());
        novoArtista.setInstrumentoPrincipal(dto.instrumentoPrincipal());
        novoArtista.setInfo(dto.info());

        repository.persist(novoArtista);

        return novoArtista;
    }

    @Override
    @Transactional
    public void update(Long id, ArtistaDTO dto) {
        Artista edicaoArtista = repository.findById(id);
        edicaoArtista.setNome(dto.nome());
        edicaoArtista.setDataNascimento(dto.dataNascimento());
        edicaoArtista.setNacionalidade(dto.nacionalidade());
        edicaoArtista.setInstrumentoPrincipal(dto.instrumentoPrincipal());
        edicaoArtista.setInfo(dto.info());

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    


}
