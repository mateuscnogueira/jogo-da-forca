package bancodepalavras.dominio.tema.embdr;

import bancodepalavras.dominio.tema.Tema;
import bancodepalavras.dominio.tema.TemaRepository;
import repository.RepositoryException;

public class BDRTemaRepository implements TemaRepository {

    private static BDRTemaRepository soleInstance = null;

    private BDRTemaRepository() {
    }

    public static BDRTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRTemaRepository();
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
    public Tema getPorId(long id) {
        return null;
    }

    @Override
    public Tema[] getPorNome(String nome) {
        return null;
    }

    @Override
    public Tema[] getTodos() {
        return null;
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
    }

    @Override
    public void atualizar(Tema tema) throws RepositoryException {
    }

    @Override
    public void remover(Tema tema) throws RepositoryException {
    }
}