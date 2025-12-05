package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Produto;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> findByPrecoMaiorQue(Double preco) {
        return find("preco > ?1", preco).list();
    }

    public List<Produto> findByPrecoMenorQue(Double preco) {
        return find("preco < ?1", preco).list();
    }

    public List<Produto> findByFaixaDePreco(Double min, Double max) {
        return find("preco between ?1 and ?2", min, max).list();
    }

    public boolean existsByAlbum(Long idAlbum) {
        return count("album.id", idAlbum) > 0;
    }
}
