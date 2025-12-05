package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.EnderecoDTO;
import topicosAlbum.dto.EnderecoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Endereco;
import topicosAlbum.model.Usuario;
import topicosAlbum.repository.EnderecoRepository;
import topicosAlbum.repository.UsuarioRepository;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public List<EnderecoResponseDTO> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario)
            .stream()
            .map(e -> new EnderecoResponseDTO(
                e.getId(),
                e.getRua(),
                e.getNumero(),
                e.getComplemento(),
                e.getBairro(),
                e.getCidade(),
                e.getUf(),
                e.getCep()
            ))
            .toList();
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco e = repository.findById(id);
        if (e == null)
            throw ValidationException.of("id", "Endereço não encontrado.");

        return new EnderecoResponseDTO(
            e.getId(),
            e.getRua(),
            e.getNumero(),
            e.getComplemento(),
            e.getBairro(),
            e.getCidade(),
            e.getUf(),
            e.getCep()
        );
    }

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoDTO dto) {

        Usuario u = usuarioRepository.findById(dto.idUsuario());
        if (u == null)
            throw ValidationException.of("usuario", "Usuário não encontrado.");

        Endereco e = new Endereco();
        e.setRua(dto.rua());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setBairro(dto.bairro());
        e.setCidade(dto.cidade());
        e.setUf(dto.uf());
        e.setCep(dto.cep());
        e.setUsuario(u);

        repository.persist(e);

        return new EnderecoResponseDTO(
            e.getId(),
            e.getRua(),
            e.getNumero(),
            e.getComplemento(),
            e.getBairro(),
            e.getCidade(),
            e.getUf(),
            e.getCep()
        );
    }

    @Override
    @Transactional
    public void update(Long id, EnderecoDTO dto) {

        Endereco e = repository.findById(id);
        if (e == null)
            throw ValidationException.of("id", "Endereço não encontrado.");

        e.setRua(dto.rua());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setBairro(dto.bairro());
        e.setCidade(dto.cidade());
        e.setUf(dto.uf());
        e.setCep(dto.cep());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw ValidationException.of("id", "Endereço não encontrado.");
    }
}
