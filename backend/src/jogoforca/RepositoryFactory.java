package jogoforca;

import bancodepalavras.dominio.palavra.PalavraRepository;
import bancodepalavras.dominio.tema.TemaRepository;
import jogoforca.dominio.jogador.JogadorRepository;
import jogoforca.dominio.rodada.RodadaRepository;

public interface RepositoryFactory {
    public PalavraRepository getPalavraRepository();
    public TemaRepository getTemaRepository();
    public RodadaRepository getRodadaRepository();
    public JogadorRepository getJogadorRepository();
}