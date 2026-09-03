package bancodepalavras.dominio.palavra.emmemoria;

import java.util.ArrayList;
import java.util.List;

import bancodepalavras.dominio.palavra.Palavra;
import bancodepalavras.dominio.palavra.PalavraRepository;
import bancodepalavras.dominio.tema.Tema;
import repository.RepositoryException;

public class MemoriaPalavraRepository implements PalavraRepository {

    private static MemoriaPalavraRepository soleInstance = null;

    // pool = reservatório de palavras salvas
    private List<Palavra> pool;

    private MemoriaPalavraRepository() {
        this.pool = new ArrayList<>();
    }

    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaPalavraRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return pool.size() + 1;
    }

    @Override
    public Palavra getPorId(long id) {
        for (Palavra palavra : pool) {
            if (palavra.getId() == id) {
                return palavra;
            }
        }
        return null;
    }

    @Override
    public Palavra[] getPorTema(Tema tema) {
        List<Palavra> encontrados = new ArrayList<>();
        for (Palavra palavra : pool) {
            if (palavra.getTema().equals(tema)) {
                encontrados.add(palavra);
            }
        }
        // converte a lista para vetor exigido pela interface
        return encontrados.toArray(new Palavra[0]);
    }

    @Override
    public Palavra[] getTodas() {
        return pool.toArray(new Palavra[0]); // passa o tamanho 0, pois o Java identifica o tamanho correto do vetor e cria o vetor do tamanho exato de elementos presentes na pool
    }

    @Override
    public Palavra getPalavra(String palavra) {
        for (Palavra p : pool) {
            if (p.comparar(palavra)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        if (pool.contains(palavra)) {
            throw new RepositoryException("Palavra já existente no repositório.");
        }
        pool.add(palavra);
    }

    @Override
    public void atualizar(Palavra palavra) throws RepositoryException {
        // em memória a referência do objeto já está atualizada na lista. Apenas verifica se ele realmente pertence ao repositório.
        if (!pool.contains(palavra)) {
            throw new RepositoryException("Palavra não encontrada para atualização");
        }
    }

    @Override
    public void remover(Palavra palavra) throws RepositoryException {
        if (!pool.contains(palavra)) {
            throw new RepositoryException("Palavra não encontrada para remoção.");
        }
        pool.remove(palavra);
    }
}