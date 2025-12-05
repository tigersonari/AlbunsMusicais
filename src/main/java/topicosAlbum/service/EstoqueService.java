package topicosAlbum.service;

public interface EstoqueService {

    void verificarDisponibilidade(Long idProduto, Integer quantidadeSolicitada);

    void baixarEstoque(Long idProduto, Integer quantidade);

    void reporEstoque(Long idProduto, Integer quantidade);
}
