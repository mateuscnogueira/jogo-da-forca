package jogoforca.dominio.rodada;

import bancodepalavras.dominio.letra.Letra;
import bancodepalavras.dominio.palavra.Palavra;
import bancodepalavras.dominio.tema.Tema;
import dominio.ObjetoDominioImpl;
import jogoforca.dominio.boneco.Boneco;
import jogoforca.dominio.boneco.BonecoFactory;
import jogoforca.dominio.jogador.Jogador;

public class Rodada extends ObjetoDominioImpl {
    private static int maxPalavras = 3;
    private static int maxErros = 10;
    private static int pontosQuandoDescobreTodasAsPalavras = 100;
    private static int pontosPorLetraEncoberta = 15;
    
    private static BonecoFactory bonecoFactory;

    private Item[] itens;
    private Jogador jogador;
    private Boneco boneco;

    private Letra[] erradas = new Letra[maxErros];
    private int qtdeErros = 0;

    private Letra[] certas = new Letra[26]; // alfabeto = 26
    private int qtdeAcertos = 0;

    // construtor criar
    private Rodada(long id, Palavra[] palavras, Jogador jogador) {
        super(id);
        
        if (bonecoFactory == null) {
            throw new IllegalStateException("BonecoFactory deve ser setada antes de instanciar a Rodada.");
        }
        if (palavras == null || palavras.length == 0 || palavras.length > maxPalavras) {
            throw new IllegalArgumentException("Quantidade de palavras inválida. Deve ser entre 1 e " + maxPalavras);
        }

        Tema temaReferencia = palavras[0].getTema();
        for (Palavra p : palavras) {
            if (p.getTema() != temaReferencia) {
                throw new IllegalArgumentException("Todas as palavras têm que ser do mesmo tema.");
            }
        }

        this.jogador = jogador;
        this.itens = new Item[palavras.length];
        
        for (int i = 0; i < palavras.length; i++) {
            this.itens[i] = Item.criar(i, palavras[i]);
        }
        
        this.boneco = bonecoFactory.getBoneco();
    }

    // construtor reconstituir
    private Rodada(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        super(id);
        
        if (bonecoFactory == null) {
            throw new IllegalStateException("BonecoFactory deve ser setada antes de instanciar a Rodada.");
        }
        
        this.itens = itens;
        this.erradas = erradas;
        this.jogador = jogador;
        this.boneco = bonecoFactory.getBoneco();

        int erros = 0;
        for (Letra l : erradas) {
            if (l != null) {
                erros++;
            }
        }
        this.qtdeErros = erros;
    }

    public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
        return new Rodada(id, palavras, jogador);
    }

    public static Rodada reconstituir(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        return new Rodada(id, itens, erradas, jogador);
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Tema getTema() {
        return itens[0].getPalavra().getTema();
    }

    public Palavra[] getPalavras() {
        Palavra[] palavras = new Palavra[itens.length];
        for (int i = 0; i < itens.length; i++) {
            palavras[i] = itens[i].getPalavra();
        }
        return palavras;
    }

    public int getNumPalavras() {
        return itens.length;
    }

    public void tentar(char codigo) {
        if (encerrou()) {
            return; // se a rodada encerrou retorna
        }

        // verifica se a letra da vez já foi escolhida anteriormente
        for (int i = 0; i < qtdeAcertos; i++) {
            if (certas[i].getCodigo() == codigo) {
                return;
            }
        }
        for (int i = 0; i < qtdeErros; i++) {
            if (erradas[i].getCodigo() == codigo) {
                return;
            }
        }

        boolean teveAcerto = false;
        for (Item item : itens) { // varre os itens da rodada, buscando a letra escolhida em alguma das palavras
            if (item.tentar(codigo)) {
                teveAcerto = true;
            }
        }

        // instancia as letras usando LetraFactory e a guarda no respectivo vetor (certa ou errada)
        if (teveAcerto) {
            certas[qtdeAcertos] = Palavra.getLetraFactory().getLetra(codigo);
            qtdeAcertos++;
        } else {
            erradas[qtdeErros] = Palavra.getLetraFactory().getLetra(codigo);
            qtdeErros++;
        }

        /* verifica novamente se a rodada encerrou
        true -> calcula a pontuação e atualiza os pontos do jogador */
        if (encerrou()) {
            jogador.atualizarPontuacao(calcularPontos());
        }
    }

    public void arriscar(String[] palavrasArriscadas) {
        if (encerrou()) {
            return;
        }

        /* varre os itens da rodada em paralelo com o vetor de palavras arriscadas.
        atribui a cada item a palavra arriscada escolhida */
        for (int i = 0; i < itens.length && i < palavrasArriscadas.length; i++) {
            itens[i].arriscar(palavrasArriscadas[i]);
        }

        if (encerrou()) { // como o jogador só pode arriscar uma vez, o ato de arriscar encerra a rodada
            jogador.atualizarPontuacao(calcularPontos());
        }
    }

    public Letra[] getTentativas() {
        Letra[] todasTentativas = new Letra[qtdeAcertos + qtdeErros];
        
        for (int i = 0; i < qtdeAcertos; i++) {
        todasTentativas[i] = certas[i];
        }
        for (int j = 0; j < qtdeErros; j++) {
            todasTentativas[qtdeAcertos + j] = erradas[j];
        }

        return todasTentativas;
    }
    
    public Letra[] getLetrasErradas() {
        Letra[] retorno = new Letra[qtdeErros];
        
        for (int i = 0; i < qtdeErros; i++) {
            retorno[i] = erradas[i];
        }

        return retorno;
    }
    
    public Letra[] getLetrasCertas() {
        Letra[] retorno = new Letra[qtdeAcertos];
    
        for (int i = 0; i < qtdeAcertos; i++) {
            retorno[i] = certas[i];
        }

        return retorno;
    }

    public int calcularPontos() {
        if (descobriu()) {
            int pontos = pontosQuandoDescobreTodasAsPalavras;
            for (Item item : itens) {
                pontos += item.calcularPontosLetrasEncobertas(pontosPorLetraEncoberta);
            }
            return pontos;
        }
        return 0;
    }
    
    //exibir
    public void exibirItens(Object contexto) {
        for (Item item : itens) {
            item.exibir(contexto); // delega para item, que repassa para palavra que irá verificar quais letras estão encobertas e descobertas
            System.out.println(); // quebra linha após cada palavra
        }
    }
    
    public void exibirBoneco(Object contexto) {
        boneco.exibir(contexto, qtdeErros); // boneco usa a qtdeErros para desenhar as partes do corpo
    }
    
    public void exibirPalavras(Object contexto) {
        for (Item item : itens) {
            item.getPalavra().exibir(contexto);
            System.out.println(); // quebra linha após cada palavra
        }
    }
    
    public void exibirLetrasErradas(Object contexto) {
        for (int i = 0; i < qtdeErros; i++) {
            //erradas[i].exibir(contexto);
            getLetrasErradas()[i].exibir(contexto);
        }
    }

    //estado booleano
    public boolean arriscou() {
        for (Item item : itens) {
            if (item.arriscou()) {
                return true;
            }
        }
        return false;
    }

    public boolean descobriu() {
        for (Item item : itens) {
            if (!item.descobriu()) { // todas as palavras precisam ser descobertas, se alguma delas não tiver sido, retorna false
                return false;
            }
        }
        return true;
    }

    public boolean encerrou() {
        return arriscou() || descobriu() || atingiuMaxErros();
    }

    public boolean atingiuMaxErros() {
        return getQtdeTentativasRestantes() == 0;
    }

    //quantidade
    public int getQtdeTentativasRestantes() {
        return maxErros - qtdeErros;
    }

    public int getQtdeErros() {
        return qtdeErros;
    }

    public int getQtdeAcertos() {
        return qtdeAcertos;
    }

    public int getQtdeTentativas() {
        return qtdeAcertos + qtdeErros;
    }

    //static
    public static int getMaxPalavras() {
        return maxPalavras;
    }
    public static void setMaxPalavras(int max) {
        if (max == 0) {
            throw new IllegalArgumentException("O número de palvras não pode ser 0");
        }
        maxPalavras = max;
    }

    public static int getMaxErros() {
        return maxErros;
    }
    public static void setMaxErros(int max) {
        if (max == 0) {
            throw new IllegalArgumentException("O número de erros não pode ser 0");
        }
        maxErros = max;
    }

    public static int getPontosQuandoDescobreTodasAsPalavras() {
        return pontosQuandoDescobreTodasAsPalavras;
    }
    public static void setPontosQuandoDescobreTodasAsPalavras(int pontos) {
        if (pontos == 0) {
            throw new IllegalArgumentException("O número de pontos não pode ser 0");
        }
        pontosQuandoDescobreTodasAsPalavras = pontos;
    }

    public static int getPontosPorLetraEncoberta() {
        return pontosPorLetraEncoberta;
    }
    public static void setPontosPorLetraEncoberta(int pontos) {
        if (pontos == 0) {
            throw new IllegalArgumentException("O número de pontos não pode ser 0");
        }
        pontosPorLetraEncoberta = pontos;
    }

    public static BonecoFactory getBonecoFactory() {
        return bonecoFactory;
    }
    public static void setBonecoFactory(BonecoFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("BonecoFactory não pode ser null");
        }
        bonecoFactory = factory;
    }
}