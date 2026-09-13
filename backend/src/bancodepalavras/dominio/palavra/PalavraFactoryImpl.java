package bancodepalavras.dominio.palavra;

import bancodepalavras.dominio.tema.Tema;
import factory.EntityFactory;

public class PalavraFactoryImpl extends EntityFactory implements PalavraFactory{

    private static PalavraFactoryImpl soleInstance;

    private PalavraFactoryImpl(PalavraRepository repository) {
        super(repository);
    }

    // método para CRIAR a instância única passando o repositório como parâmetro
    public static void createSoleInstance (PalavraRepository repository){
        if (soleInstance == null){
            soleInstance = new PalavraFactoryImpl(repository);
        }
    }

    // método tradicional para PEGAR a instância já criada
    public static PalavraFactory getSoloInstance(){
        if (soleInstance == null){
            throw new IllegalStateException("A fábrica PalavraFactoryImpl não foi inicializada. Chame createSoleInstance() primeiro.");
        }
        return soleInstance;
    }

    // sobrescreve o getRepository para retornar o tipo específico (PalavraRepository)
    @Override 
    protected PalavraRepository getRepository(){
        return (PalavraRepository) super.getRepository();
    }

    @Override
    public Palavra getPalavra(String palavra, Tema tema) {
        // a fábrica pede o próximo ID para o repositório e chama o método criar() da entidade Palavra
        return Palavra.criar(this.getProximoId(), palavra, tema);
    }
    
}
