package jogoforca.emmemoria;

import bancodepalavras.dominio.palavra.PalavraRepository;
import bancodepalavras.dominio.palavra.emmemoria.MemoriaPalavraRepository;
import bancodepalavras.dominio.tema.TemaRepository;
import bancodepalavras.dominio.tema.emmemoria.MemoriaTemaRepository;
import jogoforca.RepositoryFactory;
import jogoforca.dominio.jogador.JogadorRepository;
import jogoforca.dominio.jogador.emmemoria.MemoriaJogadorRepository;
import jogoforca.dominio.rodada.RodadaRepository;
import jogoforca.dominio.rodada.emmemoria.MemoriaRodadaRepository;

public class MemoriaRepositoryFactory implements RepositoryFactory {

    private static MemoriaRepositoryFactory soleInstance = null;

    private MemoriaRepositoryFactory() {
    }

    public static MemoriaRepositoryFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaRepositoryFactory();
        }
        return soleInstance;
    }

    @Override
    public PalavraRepository getPalavraRepository() {
        return MemoriaPalavraRepository.getSoleInstance();
    }

    @Override
    public TemaRepository getTemaRepository() {
        return MemoriaTemaRepository.getSoleInstance();
    }

    @Override
    public RodadaRepository getRodadaRepository() {
        return MemoriaRodadaRepository.getSoleInstance();
    }

    @Override
    public JogadorRepository getJogadorRepository() {
        return MemoriaJogadorRepository.getSoleInstance();
    }
}