package topicosAlbum.repository;


import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import topicosAlbum.model.Composicao;

@ApplicationScoped
public class ComposicaoRepository implements PanacheRepository<Composicao> {

    
    public List<Composicao> findByArtistaId(Long idArtista) {
        return find("SELECT c FROM Composicao c JOIN c.artista a WHERE a.id = ?1", idArtista).list();
    }

    
    public List<Composicao> findByData(java.time.LocalDate data) {
        return find("data = ?1", data).list();
    }

    // buscar composições associadas a uma faixa específica
    public Composicao findByFaixaId(Long idFaixa) {
        return find("SELECT c FROM Composicao c JOIN c.faixa f WHERE f.id = ?1", idFaixa).firstResult();
    }

    /* buscar composições que ainda não possuem faixa vinculada não implementado,
     * pois o relacionamento é 1:1 e cada composição é criada especificamente para
     * uma faixa. deixarei o código comentado caso queira implementar futuramente ou algo
     * relacionado.
    */
    /*public List<Composicao> findSemFaixa() {
        return find("faixa IS NULL").list();
    }*/
}
