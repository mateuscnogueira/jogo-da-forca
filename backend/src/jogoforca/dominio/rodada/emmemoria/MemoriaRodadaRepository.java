package jogoforca.dominio.rodada.emmemoria;

import java.util.ArrayList;
import java.util.List;

import jogoforca.dominio.jogador.Jogador;
import jogoforca.dominio.rodada.Rodada;
import jogoforca.dominio.rodada.RodadaRepository;
import repository.RepositoryException;

public class MemoriaRodadaRepository implements RodadaRepository {

    private static MemoriaRodadaRepository soleInstance = null;

    private List<Rodada> pool;

    private MemoriaRodadaRepository() {
        this.pool = new ArrayList<>();
    }

    public static MemoriaRodadaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaRodadaRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        return pool.size() + 1;
    }

    @Override
    public Rodada getPorId(long id) {
        for (Rodada rodada : pool) {
            if (rodada.getId() == id) {
                return rodada;
            }
        }
        return null;
    }

    @Override
    public Rodada[] getPorJogador(Jogador jogador) {
        List<Rodada> encontradas = new ArrayList<>();
        for (Rodada rodada : pool) {
            if (rodada.getJogador().equals(jogador)) {
                encontradas.add(rodada);
            }
        }
        return encontradas.toArray(new Rodada[0]);
    }

    @Override
    public void inserir(Rodada rodada) throws RepositoryException {
        if (pool.contains(rodada)) {
            throw new RepositoryException("A rodada já existe no repositório.");
        }
        pool.add(rodada);
    }

    @Override
    public void atualizar(Rodada rodada) throws RepositoryException {
        // em memória a referência do objeto já está atualizada na lista. Apenas verifica se ele realmente pertence ao repositório.
        if (!pool.contains(rodada)) {
            throw new RepositoryException("Rodada não encontrada para atualização.");
        }
    }

    @Override
    public void remover(Rodada rodada) throws RepositoryException {
        if (!pool.contains(rodada)) {
            throw new RepositoryException("Rodada não encontrada para remoção.");
        }
        pool.remove(rodada);
    }
}
