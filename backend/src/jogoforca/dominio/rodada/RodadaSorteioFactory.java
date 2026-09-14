package jogoforca.dominio.rodada;

import bancodepalavras.dominio.palavra.Palavra;
import bancodepalavras.dominio.palavra.PalavraRepository;
import bancodepalavras.dominio.tema.Tema;
import bancodepalavras.dominio.tema.TemaRepository;
import jogoforca.dominio.jogador.Jogador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RodadaSorteioFactory extends RodadaFactoryImpl {

    private static RodadaSorteioFactory soleInstance;

    private RodadaSorteioFactory(RodadaRepository rodadaRepository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        super(rodadaRepository, temaRepository, palavraRepository);
    }

    // método para CRIAR a instância única passando o repositório como parâmetro
    public static void createSoleInstance(RodadaRepository rodadaRepository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        if (soleInstance == null) {
            soleInstance = new RodadaSorteioFactory(rodadaRepository, temaRepository, palavraRepository);
        }
    }

    // método tradicional para PEGAR a instância já criada
    public static RodadaSorteioFactory getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException("A fábrica RodadaSorteioFactory não foi inicializada. Chame createSoleInstance() primeiro.");
        }
        return soleInstance;
    }

    @Override
    public Rodada getRodada(Jogador jogador) {
        // usa o repositório de temas e pega todos os temas
        Tema[] temas = this.getTemaRepository().getTodos();
        if (temas == null || temas.length == 0) {
            throw new IllegalStateException("Não há temas cadastrados para realizar o sorteio.");
        }
        
        // sorteia um tema aleatório
        Random random = new Random();
        Tema temaSorteado = temas[random.nextInt(temas.length)];

        // usa o repositório de palavras e pega as palavras do tema
        Palavra[] palavrasDoTema = this.getPalavraRepository().getPorTema(temaSorteado);
        if (palavrasDoTema == null || palavrasDoTema.length == 0) {
            throw new IllegalStateException("Não há palavras cadastradas no tema: " + temaSorteado.getNome());
        }

        // define quantas palavras serão sorteadas (respeitando o limite estático da classe Rodada)
        int maxPalavras = Rodada.getMaxPalavras();
        int qtdPalavrasSorteio = Math.min(maxPalavras, palavrasDoTema.length);

        // embaralha as palavras para garantir que não virão repetidas
        List<Palavra> listaPalavras = new ArrayList<>();
        for (Palavra p : palavrasDoTema) {
            listaPalavras.add(p);
        }
        Collections.shuffle(listaPalavras);

        // separa apenas a quantidade necessária de palavras em um vetor
        Palavra[] palavrasSorteadas = new Palavra[qtdPalavrasSorteio];
        for (int i = 0; i < qtdPalavrasSorteio; i++) {
            palavrasSorteadas[i] = listaPalavras.get(i);
        }

        // a fábrica pede o próximo ID para o repositório e chama o método criar() de Rodada.
        return Rodada.criar(this.getProximoId(), palavrasSorteadas, jogador);
    }
}