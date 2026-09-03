package jogoforca.dominio.rodada.embdr;

import jogoforca.dominio.jogador.Jogador;
import jogoforca.dominio.rodada.Rodada;
import jogoforca.dominio.rodada.RodadaRepository;
import repository.RepositoryException;

public class BDRRodadaRepository implements RodadaRepository {
    private static BDRRodadaRepository soleInstance = null;

    private BDRRodadaRepository() {
    }

    public static BDRRodadaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRRodadaRepository();
        }
        return soleInstance;
    }

    // ----- métodos vazios conforme orientação de Mark -----
    // classes estruturais caso seja implementado futuramente um Banco de Dados Relacional

    @Override
    public long getProximoId() {
        return 0;
    }

    @Override
    public Rodada getPorId(long id) {
        return null;
    }

    @Override
    public Rodada[] getPorJogador(Jogador jogador) {
        return null;
    }

    @Override
    public void inserir(Rodada rodada) throws RepositoryException {
    }

    @Override
    public void atualizar(Rodada rodada) throws RepositoryException {
    }

    @Override
    public void remover(Rodada rodada) throws RepositoryException {
    }
}
