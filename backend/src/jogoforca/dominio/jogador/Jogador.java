package jogoforca.dominio.jogador;

import dominio.ObjetoDominioImpl;

public class Jogador extends ObjetoDominioImpl {
    private String nome;
    private int pontuacao = 0;

    private Jogador(long id, String nome) {
        super(id);
        setNome(nome);
    }

    public static Jogador criar(long id, String nome) {
        return new Jogador(id, nome);
    }

    public static Jogador reconstituir(long id, String nome, int pontuacao) {
        Jogador jogador = new Jogador(id, nome);
        jogador.pontuacao = pontuacao;
        return jogador;
    }

    public String getNome() {
        return nome;
    }
    public final void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }
    public void atualizarPontuacao(int pontuacao) {
        this.pontuacao += pontuacao;
    }
}
