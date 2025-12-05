package topicosAlbum.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import topicosAlbum.exception.ValidationException;
import topicosAlbum.model.Estoque;
import topicosAlbum.repository.EstoqueRepository;

@ApplicationScoped
public class EstoqueServiceImpl implements EstoqueService {

    @Inject
    EstoqueRepository repository;

    @Override
    public void verificarDisponibilidade(Long idProduto, Integer quantidadeSolicitada) {
        Estoque estoque = repository.findByProduto(idProduto);

        if (estoque == null)
            throw ValidationException.of("produto", "Produto sem estoque cadastrado.");

        if (estoque.getQuantidadeDisponivel() < quantidadeSolicitada)
            throw ValidationException.of("estoque", "Estoque insuficiente.");
    }

    @Override
    @Transactional
    public void baixarEstoque(Long idProduto, Integer quantidade) {
        Estoque estoque = repository.findByProduto(idProduto);

        if (estoque == null)
            throw ValidationException.of("produto", "Produto sem estoque cadastrado.");

        Integer novaQuantidade = estoque.getQuantidadeDisponivel() - quantidade;

        if (novaQuantidade < 0)
            throw ValidationException.of("estoque", "Estoque insuficiente.");

        estoque.setQuantidadeDisponivel(novaQuantidade);
    }

    @Override
    @Transactional
    public void reporEstoque(Long idProduto, Integer quantidade) {
        Estoque estoque = repository.findByProduto(idProduto);

        if (estoque == null)
            throw ValidationException.of("produto", "Produto sem estoque cadastrado.");

        estoque.setQuantidadeDisponivel(
            estoque.getQuantidadeDisponivel() + quantidade
        );
    }
}
