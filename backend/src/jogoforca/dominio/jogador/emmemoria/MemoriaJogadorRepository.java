package jogoforca.dominio.jogador.emmemoria;

import java.util.ArrayList;
import java.util.List;

import jogoforca.dominio.jogador.Jogador;
import jogoforca.dominio.jogador.JogadorRepository;
import repository.RepositoryException;

public class MemoriaJogadorRepository implements JogadorRepository {

    private static MemoriaJogadorRepository soleInstance = null;

    // pool = reservatório de jogadores salvos
    private List<Jogador> pool;

    private MemoriaJogadorRepository(){
        this.pool = new ArrayList<>();
    }

    public static MemoriaJogadorRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaJogadorRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return pool.size() + 1;
    }

    @Override
    public Jogador getPorId(long id) {
        for (Jogador jogador : pool) {
            if (jogador.getId() == id) {
                return  jogador;
            }
        }
        return  null;
    }

    @Override
    public Jogador getPorNome(String nome) {
        for (Jogador jogador : pool) {
            if (jogador.getNome().equalsIgnoreCase(nome)) {
                return jogador;
            }
        }
        return null;
    }

    @Override
    public void inserir(Jogador jogador) throws RepositoryException {
        if (pool.contains(jogador)) {
            throw new RepositoryException("Esse jogador já existe no repositório.");
        }
        pool.add(jogador);
    }

    @Override
    public void atualizar(Jogador jogador) throws RepositoryException {
        // em memória a referência do objeto já está atualizada na lista. Apenas verifica se ele realmente pertence ao repositório.
        if (!pool.contains(jogador)) {
            throw new RepositoryException("Jogador não encontrado para atualização.");
        }
    }

    @Override
    public void remover(Jogador jogador) throws RepositoryException {
        if (!pool.contains(jogador)) {
            throw new RepositoryException("Jogador não encontrado para exclusão.");
        }
        pool.remove(jogador);
    }
}
