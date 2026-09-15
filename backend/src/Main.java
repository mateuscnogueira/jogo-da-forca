import bancodepalavras.dominio.palavra.PalavraAppService;
import bancodepalavras.dominio.tema.Tema;
import bancodepalavras.dominio.tema.TemaFactory;
import bancodepalavras.dominio.tema.TemaRepository;
import jogoforca.Aplicacao;
import jogoforca.JogadorNaoEncontradoException;
import jogoforca.dominio.rodada.RodadaAppService;
import jogoforca.dominio.jogador.Jogador;
import jogoforca.dominio.jogador.JogadorFactory;
import jogoforca.dominio.jogador.JogadorRepository;
import jogoforca.dominio.rodada.Rodada;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializa o maestro do jogo e configura as injeções
        Aplicacao app = Aplicacao.getSoleInstance();
        app.configurar();
        
        // 2. Alimenta o banco em memória com dados iniciais para podermos jogar
        alimentarBancoDeDadosMock(app);
        
        // 3. Pega os serviços de aplicação
        RodadaAppService rodadaService = RodadaAppService.getSoleInstance();
        
        // 4. Inicia a interface do terminal
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;
        
        // Variável para controlar o que aparece na tela do menu
        String graficoAtual = app.getTiposElementoGraficoFactory()[0]; 

        System.out.println("===================================");
        System.out.println("      BEM-VINDO AO JOGO DA FORCA   ");
        System.out.println("===================================");

        while (executando) {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("1 - Jogar");
            System.out.println("2 - Mudar Gráficos (Atual: " + graficoAtual + ")"); 
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    jogar(scanner, rodadaService);
                    break;
                case "2":
                    System.out.print("Digite 'texto' ou 'imagem': ");
                    String grafico = scanner.nextLine().toLowerCase().trim();
                    
                    if (grafico.equals("texto") || grafico.equals("imagem")) {
                        app.setTipoElementoGraficoFactory(grafico);
                        graficoAtual = grafico;
                        System.out.println("Gráficos alterados para: " + grafico);
                    } else {
                        System.out.println("Opção inválida. Digite apenas 'texto' ou 'imagem'.");
                    }
                    break;
                case "3":
                    executando = false;
                    System.out.println("Saindo do jogo... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void jogar(Scanner scanner, RodadaAppService rodadaService) {
        try {
            System.out.print("\nDigite seu nome de jogador (tente 'Nog'): ");
            String nome = scanner.nextLine();
            
            Rodada rodada = rodadaService.novaRodada(nome);

            // Loop principal da partida
            while (!rodada.encerrou()) {
                System.out.println("\n-----------------------------------");
                System.out.println("Tema: " + rodada.getTema().getNome());
                
                rodada.exibirBoneco(System.out);
                System.out.println();
                
                System.out.println("Palavras: ");
                rodada.exibirItens(System.out); 
                
                System.out.print("\nLetras Erradas: ");
                rodada.exibirLetrasErradas(System.out);
                System.out.println("\nTentativas restantes: " + rodada.getQtdeTentativasRestantes());
                
                // Novo Menu Interativo da Rodada
                System.out.println("\nO que deseja fazer?");
                System.out.println("1 - Tentar uma letra");
                System.out.println("2 - Arriscar as palavras (Aviso: Se errar, perde o jogo!)");
                System.out.print("Opção: ");
                String opcaoJogada = scanner.nextLine().trim();
                
                if (opcaoJogada.equals("1")) {
                    System.out.print("Digite uma letra: ");
                    String entrada = scanner.nextLine().toUpperCase().trim();
                    
                    if (entrada.length() == 1) {
                        char letra = entrada.charAt(0);
                        rodada.tentar(letra);
                    } else {
                        System.out.println("Aviso: Digite apenas uma letra por vez!");
                    }
                    
                } else if (opcaoJogada.equals("2")) {
                    // Pega a quantidade exata de palavras dessa rodada
                    int qtdPalavras = rodada.getNumPalavras();
                    String[] palpites = new String[qtdPalavras];
                    
                    System.out.println("\n--- MODO ARRISCAR ---");
                    for (int i = 0; i < qtdPalavras; i++) {
                        System.out.print("Arrisque a palavra " + (i + 1) + ": ");
                        palpites[i] = scanner.nextLine().toUpperCase().trim();
                    }
                    
                    // Envia os palpites para o método que você criou
                    rodada.arriscar(palpites);
                    
                } else {
                    System.out.println("Opção inválida! Digite 1 ou 2.");
                }
            }

            // Fim da rodada
            System.out.println("\n===================================");
            rodada.exibirBoneco(System.out);
            System.out.println();
            
            System.out.println("Solução final: ");
            rodada.exibirPalavras(System.out); 
            System.out.println();
            
            // Verifica os critérios de vitória / derrota
            if (rodada.descobriu()) {
                System.out.println("\nPARABÉNS! Você venceu a rodada!");
                System.out.println("Pontos ganhos: " + rodada.calcularPontos());
            } else if (rodada.arriscou()) {
                // Se ele arriscou e NÃO descobriu (passou direto pelo if de cima), ele perdeu!
                System.out.println("\nGAME OVER! Você arriscou as palavras erradas e foi enforcado na hora.");
            } else if (rodada.atingiuMaxErros()) {
                System.out.println("\nGAME OVER! Você atingiu o limite de erros e foi enforcado.");
            }
            
            // Salva a rodada no histórico do jogador
            rodadaService.salvarRodada(rodada);
            System.out.println("\nRodada salva no histórico de " + rodada.getJogador().getNome() + ".");

        } catch (JogadorNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
            System.out.println("Por favor, digite um nome válido cadastrado no sistema.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    // Método auxiliar para criar dados falsos no banco em memória
    private static void alimentarBancoDeDadosMock(Aplicacao app) {
        try {
            // Pega as ferramentas necessárias
            TemaRepository temaRepo = app.getRepositoryFactory().getTemaRepository();
            TemaFactory temaFactory = app.getTemaFactory();
            
            JogadorRepository jogadorRepo = app.getRepositoryFactory().getJogadorRepository();
            JogadorFactory jogadorFactory = app.getJogadorFactory();
            
            PalavraAppService palavraService = PalavraAppService.getSoleInstance();

            // 1. Cria e salva um Tema
            Tema temaTI = temaFactory.getTema("Tecnologia");
            temaRepo.inserir(temaTI);

            // 2. Cria e salva um Jogador
            Jogador jogador = jogadorFactory.getJogador("Nog");
            jogadorRepo.inserir(jogador);

            // 3. Usa o AppService para criar palavras atreladas ao Tema (ID 1)
            palavraService.novaPalavra("JAVA", temaTI.getId());
            palavraService.novaPalavra("PYTHON", temaTI.getId());
            palavraService.novaPalavra("SPRING", temaTI.getId());
            palavraService.novaPalavra("FACTORY", temaTI.getId());

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados iniciais: " + e.getMessage());
        }
    }
}