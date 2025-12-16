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

    // ---------- LISTAR -----------

    @Override
    public List<EnderecoResponseDTO> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario)
            .stream()
            .map(e -> new EnderecoResponseDTO(
                e.getId(), e.getRua(), e.getNumero(), e.getComplemento(),
                e.getBairro(), e.getCidade(), e.getUf(), e.getCep()
            ))
            .toList();
    }

    // ---------- BUSCAR POR ID SEGURO -----------

    public EnderecoResponseDTO findByIdSeguro(Long id, Long idUsuarioToken, boolean isAdmin) {

        Endereco e = repository.findById(id);
        if (e == null)
            throw ValidationException.of("id", "Endereço não encontrado.");

        if (!isAdmin && !e.getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode acessar endereço de outro usuário.");

        return new EnderecoResponseDTO(
            e.getId(), e.getRua(), e.getNumero(), e.getComplemento(),
            e.getBairro(), e.getCidade(), e.getUf(), e.getCep()
        );
    }

    // ---------- CRIAR (sempre para o usuário logado) -----------

    @Transactional
    public EnderecoResponseDTO createParaUsuario(EnderecoDTO dto, Long idUsuarioToken) {

        Usuario usuario = usuarioRepository.findById(idUsuarioToken);
        if (usuario == null)
            throw ValidationException.of("usuario", "Usuário autenticado não encontrado.");

        Endereco e = new Endereco();
        e.setRua(dto.rua());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setBairro(dto.bairro());
        e.setCidade(dto.cidade());
        e.setUf(dto.uf());
        e.setCep(dto.cep());
        e.setUsuario(usuario);

        repository.persist(e);

        return new EnderecoResponseDTO(
            e.getId(), e.getRua(), e.getNumero(), e.getComplemento(),
            e.getBairro(), e.getCidade(), e.getUf(), e.getCep()
        );
    }

    // ---------- ATUALIZAR SEGURO -----------

    @Transactional
    public void updateSeguro(Long id, EnderecoDTO dto, Long idUsuarioToken) {

        Endereco e = repository.findById(id);
        if (e == null)
            throw ValidationException.of("id", "Endereço não encontrado.");

        if (!e.getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode alterar endereço de outro usuário.");

        e.setRua(dto.rua());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setBairro(dto.bairro());
        e.setCidade(dto.cidade());
        e.setUf(dto.uf());
        e.setCep(dto.cep());
    }

    // ---------- DELETAR SEGURO -----------

    @Transactional
    public void deleteSeguro(Long id, Long idUsuarioToken) {

        Endereco e = repository.findById(id);
        if (e == null)
            throw ValidationException.of("id", "Endereço não encontrado.");

        if (!e.getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Você não pode apagar endereço de outro usuário.");

        repository.delete(e);
    }

    // ---------- Métodos antigos (Mantidos pela interface) -----------

    @Override
    public EnderecoResponseDTO findById(Long id) { return null; }

    @Override
    public EnderecoResponseDTO create(EnderecoDTO dto) { return null; }

    @Override
    public void update(Long id, EnderecoDTO dto) { }

    @Override
    public void delete(Long id) { }
}
