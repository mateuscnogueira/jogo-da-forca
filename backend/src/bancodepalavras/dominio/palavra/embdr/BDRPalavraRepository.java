package bancodepalavras.dominio.palavra.embdr;

import bancodepalavras.dominio.palavra.PalavraRepository;
import bancodepalavras.dominio.tema.Tema;
import bancodepalavras.dominio.palavra.Palavra;
import repository.RepositoryException;

public class BDRPalavraRepository implements PalavraRepository {
    
    private static BDRPalavraRepository soleInstance = null;

    private BDRPalavraRepository() {
    }

    public static BDRPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRPalavraRepository();
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
    public Palavra getPorId(long id) {
        return null;
    }
    
    @Override
    public Palavra[] getPorTema(Tema tema) {
        return null;
    }
    
    @Override
    public Palavra[] getTodas() {
        return null;
    }
    
    @Override
    public Palavra getPalavra(String palavra) {
        return null;
    }

    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
    }
    
    @Override
    public void atualizar(Palavra palavra) throws RepositoryException {
    }
    
    @Override
    public void remover(Palavra palavra) throws RepositoryException {
    }
}
