package bancodepalavras.dominio.palavra;

import bancodepalavras.dominio.tema.Tema;
import bancodepalavras.dominio.tema.TemaRepository;
import repository.RepositoryException;

public class PalavraAppService {

    private static PalavraAppService soleInstance;

    private TemaRepository temaRepository;
    private PalavraRepository palavraRepository;
    private PalavraFactory factory;

    private PalavraAppService(TemaRepository temaRepository, PalavraRepository palavraRepository, PalavraFactory factory) {
        this.temaRepository = temaRepository;
        this.palavraRepository = palavraRepository;
        this.factory = factory;
    }

    // método para CRIAR a instância única passando o repositório como parâmetro
    public static void createSoleInstance(TemaRepository temaRepository, PalavraRepository palavraRepository, PalavraFactory palavraFactory) {
        if (soleInstance == null) {
            soleInstance = new PalavraAppService(temaRepository, palavraRepository, palavraFactory);
        }
    }

    // método tradicional para PEGAR a instância já criada
    public static PalavraAppService getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException("PalavraAppService não foi inicializada. Chame createSoleInstance() primeiro.");
        }
        return soleInstance;
    }

    public boolean novaPalavra(String palavra, long idTema) {

        // verifica se a palavra já existe no repositório
        if (this.palavraRepository.getPalavra(palavra) != null) {
            return true;
        }

        // busca o tema por id no repositório
        Tema tema = this.temaRepository.getPorId(idTema);
        
        if (tema == null) {
            return false; 
        }

        // delega a criação da novaPalavra para a factory
        Palavra novaPalavra = this.factory.getPalavra(palavra, tema);

        // insere a palavra no repositório
        try {
            this.palavraRepository.inserir(novaPalavra);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}