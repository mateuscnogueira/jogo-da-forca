package jogoforca.dominio.rodada;

import jogoforca.JogadorNaoEncontradoException;
import jogoforca.dominio.jogador.Jogador;
import jogoforca.dominio.jogador.JogadorRepository;
import repository.RepositoryException;

public class RodadaAppService {

    private static RodadaAppService soleInstance;

    private RodadaFactory rodadaFactory;
    private RodadaRepository rodadaRepository;
    private JogadorRepository jogadorRepository;
    
    private RodadaAppService(RodadaFactory rodadaFactory, RodadaRepository rodadaRepository, JogadorRepository jogadorRepository) {
        this.rodadaFactory = rodadaFactory;
        this.rodadaRepository = rodadaRepository;
        this.jogadorRepository = jogadorRepository;
    }

    // método para CRIAR a instância única passando o repositório como parâmetro
    public static void createSoleInstance(RodadaFactory rodadaFactory, RodadaRepository rodadaRepository, JogadorRepository jogadorRepository) {
        if (soleInstance == null) {
            soleInstance = new RodadaAppService(rodadaFactory, rodadaRepository, jogadorRepository);
        }
    }

    // método tradicional para PEGAR a instância já criada
    public static RodadaAppService getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException("RodadaAppService não foi inicializada. Chame createSoleInstance() primeiro.");
        }
        return soleInstance;
    }

    // recebe o ID do jogador
    public Rodada novaRodada(long idJogador) {
        Jogador jogador = this.jogadorRepository.getPorId(idJogador);
        
        if (jogador == null) {
            return null;
        }
        
        // delega a criação da Rodada para a factory passando o jogador
        return this.rodadaFactory.getRodada(jogador);
    }

    // recebe o nome do jogador e gera a exceção customizada se não achar
    public Rodada novaRodada(String nomeJogador) throws JogadorNaoEncontradoException {
        Jogador jogador = this.jogadorRepository.getPorNome(nomeJogador);
        
        if (jogador == null) {
            throw new JogadorNaoEncontradoException(nomeJogador);
        }
        
        // delega a criação da Rodada para a factory passando o jogador
        return this.rodadaFactory.getRodada(jogador);
    }

    public boolean salvarRodada(Rodada rodada) {
        try {
            this.rodadaRepository.inserir(rodada);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}