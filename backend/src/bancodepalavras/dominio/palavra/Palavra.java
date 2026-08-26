package bancodepalavras.dominio.palavra;

import bancodepalavras.dominio.letra.Letra;
import bancodepalavras.dominio.letra.LetraFactory;
import bancodepalavras.dominio.tema.Tema;
import dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl {

    private static LetraFactory letraFactory;
    
    private Tema tema;
    private Letra[] letras;
    private String textoPalavra; 

    private Palavra(long id, String palavra, Tema tema) {
        super(id);
        if (letraFactory == null) {
            throw new IllegalStateException("A LetraFactory deve ser setada antes de instanciar uma Palavra.");
        }
        if (palavra == null || palavra.trim().isEmpty()) {
            throw new IllegalArgumentException("A palavra não pode ser nula ou vazia.");
        }
        
        this.tema = tema;
        this.textoPalavra = palavra;
        
        this.letras = new Letra[palavra.length()];
        
        for (int i = 0; i < palavra.length(); i++) {
            this.letras[i] = letraFactory.getLetra(palavra.charAt(i));
        }
    }
    
    public static Palavra criar(long id, String palavra, Tema tema) {
        return new Palavra(id, palavra, tema);
    }

    public static Palavra reconstituir(long id, String palavra, Tema tema) {
        return new Palavra(id, palavra, tema);
    }

    public Tema getTema() {
        return tema;
    }

    public int getTamanho() {
        return letras.length;
    }

    public Letra[] getLetras() {
        return letras;
    }

    public Letra getLetra(int posicao) {
        if (posicao < 0 || posicao >= letras.length) {
            throw new IndexOutOfBoundsException("Posição inválida.");
        }
        return letras[posicao];
    }

    public int[] tentar(char codigo) {
        // conta quantas vezes a letra aparece para definir o tamanho do vetor
        int count = 0;
        for (Letra l : letras) {
            if (l.getCodigo() == codigo) {
                count++;
            }
        }

        // cria o vetor de posições (se count for 0, o vetor nasce vazio, cumprindo a regra de não ser null)
        int[] posicoes = new int[count];
        int index = 0;
        
        // preenche o vetor com os índices da posicao q a letra aparece
        for (int i = 0; i < letras.length; i++) {
            if (letras[i].getCodigo() == codigo) {
                posicoes[index++] = i;
            }
        }
        
        return posicoes;
    }

    public boolean comparar(String palavra) {
        return this.textoPalavra.equalsIgnoreCase(palavra);
    }

    // mostra a palavra inteira, itera o vetor letras e manda cada uma se exibir
    public void exibir(Object contexto) {
        for (Letra letra : letras) {
            letra.exibir(contexto);
        }
    }

    /* varre as letras e verifica se a posição:
        - se a posição correspondente no vetor booleano for true -> exibe a letra real
        - se for false -> letraFactory busca a letra encoberta e exibe ela
    */
    public void exibir(Object contexto, boolean[] posicoesDescobertas) {
        for (int i = 0; i < letras.length; i++) {
            if (posicoesDescobertas[i]) {
                letras[i].exibir(contexto);
            } else {
                letraFactory.getLetraEncoberta().exibir(contexto);
            }
        }
    }
    
    public static LetraFactory getLetraFactory() {
        return letraFactory;
    }
    public static void setLetraFactory(LetraFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("letraFactory não pode ser null");
        }
        letraFactory = factory;
    }

    @Override
    public String toString() {
        return this.textoPalavra;
    }
}