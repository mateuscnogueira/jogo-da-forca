package jogoforca;

public class JogadorNaoEncontradoException extends Exception {
    
    private String jogador;

    public JogadorNaoEncontradoException(String jogador) {
        super("Jogador não encontrado: " + jogador);
        this.jogador = jogador;
    }

    public String getJogador() {
        return this.jogador;
    }
}