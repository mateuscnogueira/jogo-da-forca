package bancodepalavras.dominio.tema;

import factory.EntityFactory;

public class TemaFactoryImpl extends EntityFactory implements TemaFactory {

    private static TemaFactoryImpl soleInstance;

    private TemaFactoryImpl(TemaRepository repository) {
        super(repository);
    }

    // método para CRIAR a instância única passando o repositório como parâmetro
    public static void createSoleInstance(TemaRepository repository) {
        if (soleInstance == null) {
            soleInstance = new TemaFactoryImpl(repository);
        }
    }

    // método tradicional para PEGAR a instância já criada
    public static TemaFactoryImpl getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException("A fábrica TemaFactoryImpl não foi inicializada. Chame createSoleInstance() primeiro.");
        }
        return soleInstance;
    }

    // sobrescreve o getRepository para retornar o tipo específico (TemaRepository)
    @Override
    protected TemaRepository getRepository() {
        return (TemaRepository) super.getRepository();
    }

    @Override
    public Tema getTema(String nome) {
        // a fábrica pede o próximo ID para o repositório e chama o método criar() da entidade Tema
        return Tema.criar(this.getProximoId(), nome);
    }
}

/* 
Um Singleton tradicional é autossuficiente e geralmente possui um construtor vazio. 
As fábricas de entidade possuem uma dependência forte com a camada de persistência (os repositórios) para gerenciar a geração de IDs. 

O Parametrized Singleton permite separar a fase de inicialização (onde injetamos essa dependência de infraestrutura na classe de Configuração) da fase de utilização (onde os AppServices apenas recuperam a instância limpa e a utilizam), respeitando o isolamento das camadas.
*/