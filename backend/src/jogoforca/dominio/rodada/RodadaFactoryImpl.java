package jogoforca.dominio.rodada;

import bancodepalavras.dominio.palavra.PalavraRepository;
import bancodepalavras.dominio.tema.TemaRepository;
import factory.EntityFactory;

public abstract class RodadaFactoryImpl extends EntityFactory implements RodadaFactory {

    private TemaRepository temaRepository;
    private PalavraRepository palavraRepository;

    protected RodadaFactoryImpl(RodadaRepository rodadaRepository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        super(rodadaRepository);
        this.temaRepository = temaRepository;
        this.palavraRepository = palavraRepository;
    }

    protected RodadaRepository geRodadaRepository() {
        return (RodadaRepository) super.getRepository();
    }
    
    protected TemaRepository getTemaRepository() {
        return temaRepository;
    }

    protected PalavraRepository getPalavraRepository() {
        return palavraRepository;
    }
}