package jogoforca.dominio.boneco.texto;

import jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco {

    // instância única (Singleton)
    private static BonecoTexto soleInstance = null;

    private BonecoTexto() {
    }

    // acesso global a única instância de BonecoTexto
    public static BonecoTexto getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTexto(); // vai criar o boneco da primeira vez que alguém pedir e depois vai devolver a unica instancia ja criada na memoria
        }
        return soleInstance;
    }

    @Override
    public void exibir(Object contexto, int partes) {
        if (partes == 0) {
            System.out.println("Boneco: Nenhuma parte exibida (0 erros).");
            return;
        }

        System.out.print("Boneco: ");
        
        switch (partes) {
            case 1:
                System.out.println("cabeça");
                break;
            case 2:
                System.out.println("cabeça, olho esquerdo");
                break;
            case 3:
                System.out.println("cabeça, olho esquerdo, olho direito");
                break;
            case 4:
                System.out.println("cabeça, olho esquerdo, olho direito, nariz");
                break;
            case 5:
                System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca");
                break;
            case 6:
                System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco");
                break;
            case 7:
                System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo");
                break;
            case 8:
                System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito");
                break;
            case 9:
                System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda");
                break;
            case 10:
                System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda, perna direita");
                break;
            default:
                System.out.println("Quantidade de partes inválida.");
                break;
        }
    }
}