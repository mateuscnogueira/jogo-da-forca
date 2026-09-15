package jogoforca;

import bancodepalavras.dominio.palavra.PalavraAppService;
import bancodepalavras.dominio.letra.LetraFactory;
import bancodepalavras.dominio.palavra.Palavra;
import bancodepalavras.dominio.palavra.PalavraFactory;
import bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import bancodepalavras.dominio.tema.TemaFactory;
import bancodepalavras.dominio.tema.TemaFactoryImpl;
import jogoforca.dominio.rodada.RodadaAppService;
import jogoforca.dominio.boneco.BonecoFactory;
import jogoforca.dominio.jogador.JogadorFactory;
import jogoforca.dominio.jogador.JogadorFactoryImpl;
import jogoforca.dominio.rodada.Rodada;
import jogoforca.dominio.rodada.RodadaFactory;
import jogoforca.dominio.rodada.RodadaSorteioFactory;
import jogoforca.embdr.BDRRepositoryFactory;
import jogoforca.emmemoria.MemoriaRepositoryFactory;
import jogoforca.imagem.ElementoGraficoImagemFactory;
import jogoforca.texto.ElementoGraficoTextoFactory;

public class Aplicacao {

    private static final String[] TIPOS_REPOSITORY_FACTORY = {"memoria", "relacional"};
    private static final String[] TIPOS_ELEMENTO_GRAFICO_FACTORY = {"texto", "imagem"};
    private static final String[] TIPOS_RODADA_FACTORY = {"sorteio"};

    private static Aplicacao soleInstance;

    private String tipoRepositoryFactory = TIPOS_REPOSITORY_FACTORY[0];
    private String tipoElementoGraficoFactory = TIPOS_ELEMENTO_GRAFICO_FACTORY[0];
    private String tipoRodadaFactory = TIPOS_RODADA_FACTORY[0];

    private Aplicacao() {
    }

    public static Aplicacao getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new Aplicacao();
        }
        return soleInstance;
    }

    public void configurar() {
        // criação dos singletons parametrizados da aplicação

        // escolhe o tipo de repo, por padrão "memória", sendo assim, MemoriaRepositoryFactory. Agora o sistema tem acesso a todos os repositórios em memória do jogo
        RepositoryFactory repoFactory = this.getRepositoryFactory(); 

        // pega o repositório do tipo decidido acima e injeta dentro de cada classe. Nesse caso, todas utilizaram o banco em memória
        TemaFactoryImpl.createSoleInstance(repoFactory.getTemaRepository());
        PalavraFactoryImpl.createSoleInstance(repoFactory.getPalavraRepository());
        JogadorFactoryImpl.createSoleInstance(repoFactory.getJogadorRepository());
        
        RodadaSorteioFactory.createSoleInstance(
            repoFactory.getRodadaRepository(), 
            repoFactory.getTemaRepository(), 
            repoFactory.getPalavraRepository()
        );

        PalavraAppService.createSoleInstance(
            repoFactory.getTemaRepository(),
            repoFactory.getPalavraRepository(),
            this.getPalavraFactory()
        );

        RodadaAppService.createSoleInstance(
            this.getRodadaFactory(),
            repoFactory.getRodadaRepository(),
            repoFactory.getJogadorRepository()
        );

        /* injeta as fábricas gráficas (texto ou imagem), garantindo que quando a palavra se exibir, ela saiba se deve desenha um caractere no terminal ou mostrar uma imagem.
        esses métodos set da classe de negócio não possuem validação de instância única (Singleton),
        sendo assim, diferente dos AppServices e Factories que possuem essa "trava", elas aceitam a nova fábrica visual imediatamente */
        Palavra.setLetraFactory(this.getLetraFactory());
        Rodada.setBonecoFactory(this.getBonecoFactory());

        /* observação: mesmo que seja chamado o configurar() novamente (devido a alteração de algum parâmetro),
        isso não vai gerar nenhum problema, pois o Singleton imposto nas classes irá blindar a criação de uma nova instância se já existir uma do mesmo tipo */
    }

    // Repositório Factory
    public String[] getTiposRepositoryFactory() {
        return TIPOS_REPOSITORY_FACTORY;
    }

    public void setTipoRepositoryFactory(String tipo) {
        this.tipoRepositoryFactory = tipo;
        this.configurar(); // reconfigurar devido a alterarão de um parâmetro do método
    }

    public RepositoryFactory getRepositoryFactory() {
        if (this.tipoRepositoryFactory.equalsIgnoreCase("memoria")) {
            return MemoriaRepositoryFactory.getSoleInstance();
        } else {
            return BDRRepositoryFactory.getSoleInstance();
        }
    }

    // Elemento Gráfico
    public String[] getTiposElementoGraficoFactory() {
        return TIPOS_ELEMENTO_GRAFICO_FACTORY;
    }

    public void setTipoElementoGraficoFactory(String tipo) {
        this.tipoElementoGraficoFactory = tipo;
        this.configurar(); // reconfigurar devido a alterarão de um parâmetro do método
    }

    private ElementoGraficoFactory getElementoGraficoFactory() {
        if (this.tipoElementoGraficoFactory.equalsIgnoreCase("texto")) {
            return ElementoGraficoTextoFactory.getSoleInstance();
        } else {
            return ElementoGraficoImagemFactory.getSoleInstance();
        }
    }

    // Boneco Factory
    public BonecoFactory getBonecoFactory() {
        // retorna ElementoGraficoFactory, pois ela é um BonecoFactory (herança)
        return this.getElementoGraficoFactory();
    }
    
    // Letra Factory
    public LetraFactory getLetraFactory() {
        // retorna ElementoGraficoFactory, pois ela é uma LetraFactory (herança)
        return this.getElementoGraficoFactory();
    }

    // Rodada Factory
    public String[] getTiposRodadaFactory() {
        return TIPOS_RODADA_FACTORY;
    }

    public void setTipoRodadaFactory(String tipo) {
        this.tipoRodadaFactory = tipo;
        this.configurar(); // reconfigurar devido a alterarão de um parâmetro do método
    }

    public RodadaFactory getRodadaFactory() {
        // só tem a opção "sorteio" (posição 0)
        return RodadaSorteioFactory.getSoleInstance();
    }

    // Tema Factory
    public TemaFactory getTemaFactory() {
        return TemaFactoryImpl.getSoleInstance();
    }

    // Palavra Factory
    public PalavraFactory getPalavraFactory() {
        return PalavraFactoryImpl.getSoleInstance();
    }

    // Jogador Factory
    public JogadorFactory getJogadorFactory() {
        return JogadorFactoryImpl.getSoleInstance();
    }
}