package jogoforca.embdr;

import bancodepalavras.dominio.palavra.PalavraRepository;
import bancodepalavras.dominio.palavra.embdr.BDRPalavraRepository;
import bancodepalavras.dominio.tema.TemaRepository;
import bancodepalavras.dominio.tema.embdr.BDRTemaRepository;
import jogoforca.RepositoryFactory;
import jogoforca.dominio.jogador.JogadorRepository;
import jogoforca.dominio.jogador.embdr.BDRJogadorRepository;
import jogoforca.dominio.rodada.RodadaRepository;
import jogoforca.dominio.rodada.embdr.BDRRodadaRepository;

public class BDRRepositoryFactory implements RepositoryFactory {

    private static BDRRepositoryFactory soleInstance = null;

    private BDRRepositoryFactory() {
    }

    public static BDRRepositoryFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRRepositoryFactory();
        }
        return soleInstance;
    }

    @Override
    public PalavraRepository getPalavraRepository() {
        return BDRPalavraRepository.getSoleInstance();
    }

    @Override
    public TemaRepository getTemaRepository() {
        return BDRTemaRepository.getSoleInstance();
    }

    @Override
    public RodadaRepository getRodadaRepository() {
        return BDRRodadaRepository.getSoleInstance();
    }

    @Override
    public JogadorRepository getJogadorRepository() {
        return BDRJogadorRepository.getSoleInstance();
    }
}