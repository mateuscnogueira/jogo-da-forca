package jogoforca.dominio.boneco.imagem;

import jogoforca.dominio.boneco.Boneco;

public class BonecoImagem implements Boneco {

    // instância única (Singleton)
    private static BonecoImagem soleInstance = null;

    private BonecoImagem() {
    }

    // acesso global a única instância de BonecoImagem
    public static BonecoImagem getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoImagem(); // vai criar o boneco da primeira vez que alguém pedir e depois vai devolver a unica instancia ja criada na memoria
        }
        return soleInstance;
    }

    @Override
    public void exibir(Object contexto, int partes) {
        // implementação vazia conforme orientação de Mark
    }
}