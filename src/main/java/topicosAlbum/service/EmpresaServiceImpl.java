package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import topicosAlbum.dto.AlbumResponseDTO;
import topicosAlbum.dto.ArtistaResponseDTO;
import topicosAlbum.dto.EmpresaDTO;
import topicosAlbum.dto.EmpresaResponseDTO;
import topicosAlbum.dto.GrupoMusicalResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Album;
import topicosAlbum.model.Artista;
import topicosAlbum.model.Empresa;
import topicosAlbum.model.GrupoMusical;
import topicosAlbum.repository.EmpresaRepository;

@ApplicationScoped
public class EmpresaServiceImpl implements EmpresaService {

    @Inject
    EmpresaRepository repository;

    @Override
    public List<EmpresaResponseDTO> findAll() {
        return repository.listAll()
            .stream().map(EmpresaResponseDTO::valueOf).toList();
    }

    @Override
    public EmpresaResponseDTO findById(Long id) {
        Empresa e = repository.findById(id);
        if (e == null) throw ValidationException.of("id", "Empresa não encontrada.");
        return EmpresaResponseDTO.valueOf(e);
    }

    @Override
    @Transactional
    public EmpresaResponseDTO create(@Valid EmpresaDTO dto) {
        validarCnpj(dto.cnpj(), null);

        Empresa e = new Empresa();
        e.setNomeEmpresa(dto.nomeEmpresa());
        e.setCnpj(dto.cnpj());
        e.setLocalizacao(dto.localizacao());
        e.setContato(dto.contato());

        repository.persist(e);
        return EmpresaResponseDTO.valueOf(e);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid EmpresaDTO dto) {
        Empresa e = repository.findById(id);
        if (e == null) throw ValidationException.of("id", "Empresa não encontrada.");

        validarCnpj(dto.cnpj(), id);

        e.setNomeEmpresa(dto.nomeEmpresa());
        e.setCnpj(dto.cnpj());
        e.setLocalizacao(dto.localizacao());
        e.setContato(dto.contato());
    }

    private void validarCnpj(String cnpj, Long idAtual) {
        Empresa existente = repository.findByCnpj(cnpj);
        if (existente != null && !existente.getId().equals(idAtual))
            throw ValidationException.of("cnpj", "CNPJ já cadastrado.");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw ValidationException.of("id", "Empresa não encontrada.");
    }

    //

    @Override
    public List<EmpresaResponseDTO> findByNome(String nomeEmpresa) {
        return repository.findByNome(nomeEmpresa)
            .stream().map(EmpresaResponseDTO::valueOf).toList();
    }

    @Override
    public EmpresaResponseDTO findByCnpj(String cnpj) {
        Empresa e = repository.findByCnpj(cnpj);
        if (e == null) throw ValidationException.of("cnpj", "Empresa não encontrada.");
        return EmpresaResponseDTO.valueOf(e);
    }

    @Override
    public List<EmpresaResponseDTO> findByLocalizacao(String localizacao) {
        return repository.findByLocalizacao(localizacao)
            .stream().map(EmpresaResponseDTO::valueOf).toList();
    }

    @Override
    public List<EmpresaResponseDTO> findByContato(String contato) {
        return repository.findByContato(contato)
            .stream().map(EmpresaResponseDTO::valueOf).toList();
    }

    // ---- domínio ----

    @Override
    public List<ArtistaResponseDTO> findArtistas(Long idEmpresa) {
        return repository.findArtistasByEmpresaId(idEmpresa)
            .stream()
            .map(a -> ArtistaResponseDTO.valueOf((Artista) a))
            .toList();
    }

    @Override
    public List<GrupoMusicalResponseDTO> findGrupos(Long idEmpresa) {
        return repository.findGruposByEmpresaId(idEmpresa)
            .stream()
            .map(g -> GrupoMusicalResponseDTO.valueOf((GrupoMusical) g))
            .toList();
    }

    @Override
    public List<AlbumResponseDTO> findAlbunsProduzidos(Long idEmpresa) {
        return repository.findAlbunsProduzidosByEmpresaId(idEmpresa)
            .stream()
            .map(a -> AlbumResponseDTO.valueOf((Album) a))
            .toList();
    }

    @Override
    public List<AlbumResponseDTO> findAlbunsLancados(Long idEmpresa) {
        return repository.findAlbunsLancadosByEmpresaId(idEmpresa)
            .stream()
            .map(AlbumResponseDTO::valueOf)
            .toList();
    }

}
