package topicosAlbum.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Empresa;

@ApplicationScoped
public class EmpresaRepository implements PanacheRepository<Empresa> {

    public List<Empresa> findByNome(String nome) {
        return find("SELECT e FROM Empresa e WHERE e.nome LIKE ?1", "%" + nome + "%").list();
    }
}
