package jogoforca.dominio.rodada;

import bancodepalavras.dominio.letra.Letra;
import bancodepalavras.dominio.palavra.Palavra;
import dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl {

    private Palavra palavra;
    private boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    private Item(long id, Palavra palavra) {
        super(id);
        if (palavra == null) {
            throw new IllegalArgumentException("A palavra do item não pode ser nula.");
        }
        this.palavra = palavra;
        
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
    }

    public static Item criar(long id, Palavra palavra) {
        return new Item(id, palavra);
    }

    public static Item reconstituir(long id, Palavra palavra, boolean[] posicoesDescobertas, String palavraArriscada) {
        Item item = new Item(id, palavra);
        item.posicoesDescobertas = posicoesDescobertas;
        item.palavraArriscada = palavraArriscada;

        return item;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public Letra[] getLetrasDescobertas() {
        int qtd = palavra.getTamanho() - qtdeLetrasEncobertas();
        Letra[] descobertas = new Letra[qtd];
        int index = 0;
        
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (posicoesDescobertas[i]) {
                descobertas[index++] = palavra.getLetra(i);
            }
        }
        return descobertas;
    }
    
    public Letra[] getLetrasEncobertas() {
        int qtd = qtdeLetrasEncobertas();
        Letra[] encobertas = new Letra[qtd];
        int index = 0;
        
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (!posicoesDescobertas[i]) {
                encobertas[index++] = palavra.getLetra(i);
            }
        }
        return encobertas;
    }


    public int qtdeLetrasEncobertas() {
        int count = 0;
        /* percorre o vetor posDescobertas:
            - se a posicao atual for false -> cout++ */
        for (boolean descoberta : posicoesDescobertas) {
            if (!descoberta) {
                count++;
            }
        }
        return count;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return qtdeLetrasEncobertas() * valorPorLetraEncoberta;
    }

    public boolean descobriu() {
        return acertou() || qtdeLetrasEncobertas() == 0;
    }

    public void exibir(Object contexto) {
        palavra.exibir(contexto, posicoesDescobertas);
    }

    public boolean tentar(char codigo) {
        // chama a palavra para obter as posições onde a letra existe
        int[] posicoes = palavra.tentar(codigo);
        
        if (posicoes.length > 0) {
            // att o vetor posDescobertas marcando as posições encontradas como true
            for (int pos : posicoes) {
                posicoesDescobertas[pos] = true;
            }
            return true;
        }
        
        return false;
    }

    public void arriscar(String palavraArriscada) {
        this.palavraArriscada = palavraArriscada;
    }

    public String getPalavraArriscada() {
        return palavraArriscada;
    }

    public boolean arriscou() {
        return palavraArriscada != null;
    }

    public boolean acertou() {
        if (arriscou()) {
            return palavra.comparar(palavraArriscada);
        }
        return false;
    }
}