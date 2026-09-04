package bancodepalavras.dominio.tema.emmemoria;

import java.util.ArrayList;
import java.util.List;
import bancodepalavras.dominio.tema.Tema;
import bancodepalavras.dominio.tema.TemaRepository;
import repository.RepositoryException;

public class MemoriaTemaRepository implements TemaRepository {

    private static MemoriaTemaRepository soleInstance = null;
    
    // pool = reservatório de temas salvos
    private List<Tema> pool;

    private MemoriaTemaRepository() {
        this.pool = new ArrayList<>();
    }

    public static MemoriaTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaTemaRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return pool.size() + 1;
    }

    @Override
    public Tema getPorId(long id) {
        for (Tema tema : pool) {
            if (tema.getId() == id) {
                return tema;
            }
        }
        return null;
    }

    @Override
    public Tema[] getPorNome(String nome) {
        List<Tema> encontrados = new ArrayList<>();
        for (Tema tema : pool) {
            if (tema.getNome().equalsIgnoreCase(nome)) {
                encontrados.add(tema);
            }
        }
        // converte a lista para vetor exigido pela interface
        return encontrados.toArray(new Tema[0]);
    }

    @Override
    public Tema[] getTodos() {
        return pool.toArray(new Tema[0]);
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
        if (pool.contains(tema)) {
            throw new RepositoryException("Tema já existe no repositório.");
        }
        pool.add(tema);
    }

    @Override
    public void atualizar(Tema tema) throws RepositoryException {
        // em memória a referência do objeto já está atualizada na lista. Apenas verifica se ele realmente pertence ao repositório.
        if (!pool.contains(tema)) {
            throw new RepositoryException("Tema não encontrado para atualização.");
        }
    }

    @Override
    public void remover(Tema tema) throws RepositoryException {
        if (!pool.contains(tema)) {
            throw new RepositoryException("Tema não encontrado para remoção.");
        }
        pool.remove(tema);
    }
}