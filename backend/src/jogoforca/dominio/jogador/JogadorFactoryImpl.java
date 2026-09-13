package jogoforca.dominio.jogador;

import factory.EntityFactory;
import repository.Repository;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory {

    private static JogadorFactoryImpl soleInstance;

    protected JogadorFactoryImpl(Repository repository) {
        super(repository);
    }

    // método para CRIAR a instância única passando o repositório como parâmetro
    public static void createSoleInstance(JogadorRepository repository){
        if (soleInstance == null){
            soleInstance = new JogadorFactoryImpl(repository);
        }
    }

    // método tradicional para PEGAR a instância já criada
    public static JogadorFactoryImpl getSoleInstance(){
        if (soleInstance == null){
            throw new IllegalStateException("A fábrica JogadorFactoryImpl não foi inicializada. Chame createSoleInstance() primeiro.");
        }
        return soleInstance;
    }

    @Override
    protected JogadorRepository getRepository(){
        return (JogadorRepository) super.getRepository();
    }

    @Override
    public Jogador getJogador(String nome) {
        return Jogador.criar(this.getProximoId(), nome);
    }
    
}
