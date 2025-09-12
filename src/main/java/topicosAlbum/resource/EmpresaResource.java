package topicosAlbum.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import topicosAlbum.model.Empresa;
import topicosAlbum.repository.EmpresaRepository;

@Path("/empresas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpresaResource {

    @Inject
    EmpresaRepository repository;

    @GET
    public List<Empresa> buscarTodos() {
        return repository.listAll();
    }

    @GET
    @Path("/find/{nome}")
    public List<Empresa> buscarPorNome(@PathParam("nome") String nome) {
        return repository.findByNome(nome);
    }

    @POST
    @Transactional
    public Empresa incluir(Empresa empresa) {
        Empresa novaEmpresa = new Empresa();
        novaEmpresa.setNome(empresa.getNome());
        novaEmpresa.setCnpj(empresa.getCnpj());
        novaEmpresa.setLocalizacao(empresa.getLocalizacao());
        novaEmpresa.setContato(empresa.getContato());

        repository.persist(novaEmpresa);

        return novaEmpresa;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Empresa alterar(@PathParam("id") Long id, Empresa empresa) {
        Empresa edicaoEmpresa = repository.findById(id);
        if (edicaoEmpresa != null) {
            edicaoEmpresa.setNome(empresa.getNome());
            edicaoEmpresa.setCnpj(empresa.getCnpj());
            edicaoEmpresa.setLocalizacao(empresa.getLocalizacao());
            edicaoEmpresa.setContato(empresa.getContato());
        }
        return edicaoEmpresa;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagar(@PathParam("id") Long id) {
        repository.deleteById(id);
    }
}
