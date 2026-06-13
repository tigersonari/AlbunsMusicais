package topicosAlbum.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.dto.ProdutoDTO;
import topicosAlbum.dto.ProdutoResponseDTO;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Album;
import topicosAlbum.model.Estoque;
import topicosAlbum.model.Produto;
import topicosAlbum.repository.AlbumRepository;
import topicosAlbum.repository.EstoqueRepository;
import topicosAlbum.repository.ProdutoRepository;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    AlbumRepository albumRepository;

    @Inject
    EstoqueRepository estoqueRepository;

    @Override
    public List<ProdutoResponseDTO> findAll() {
        return produtoRepository.listAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id);

        if (produto == null) {
            throw ValidationException.of("id", "Produto não encontrado.");
        }

        return toResponse(produto);
    }

    @Override
    public ProdutoResponseDTO findByAlbum(Long idAlbum) {
        Produto produto = produtoRepository.find("album.id", idAlbum).firstResult();

        if (produto == null) {
            throw ValidationException.of("album", "Produto não encontrado para este álbum.");
        }

        return toResponse(produto);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO create(ProdutoDTO dto) {
        if (produtoRepository.existsByAlbum(dto.idAlbum())) {
            throw ValidationException.of("album", "Já existe produto cadastrado para este álbum.");
        }

        Album album = albumRepository.findById(dto.idAlbum());

        if (album == null) {
            throw ValidationException.of("album", "Álbum não encontrado.");
        }

        Produto produto = new Produto();
        produto.setAlbum(album);
        produto.setPreco(dto.preco());

        produtoRepository.persist(produto);

        Estoque estoque = new Estoque();
        estoque.setProdutoId(produto.getId());
        estoque.setQuantidadeDisponivel(dto.quantidadeEstoque());

        estoqueRepository.persist(estoque);

        return toResponse(produto);
    }

    @Override
    @Transactional
    public void update(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id);

        if (produto == null) {
            throw ValidationException.of("id", "Produto não encontrado.");
        }

        produto.setPreco(dto.preco());

        Estoque estoque = estoqueRepository.findByProduto(id);

        if (estoque == null) {
            estoque = new Estoque();
            estoque.setProdutoId(id);
            estoqueRepository.persist(estoque);
        }

        estoque.setQuantidadeDisponivel(dto.quantidadeEstoque());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Produto produto = produtoRepository.findById(id);

        if (produto == null) {
            throw ValidationException.of("id", "Produto não encontrado.");
        }

        Estoque estoque = estoqueRepository.findByProduto(id);

        if (estoque != null) {
            estoqueRepository.delete(estoque);
        }

        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO toResponse(Produto produto) {
        Estoque estoque = estoqueRepository.findByProduto(produto.getId());

        Integer quantidade = estoque != null
            ? estoque.getQuantidadeDisponivel()
            : 0;

        return ProdutoResponseDTO.valueOf(produto, quantidade);
    }
}