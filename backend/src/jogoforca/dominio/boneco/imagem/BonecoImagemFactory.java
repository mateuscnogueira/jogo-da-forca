package jogoforca.dominio.boneco.imagem;

import jogoforca.dominio.boneco.Boneco;
import jogoforca.dominio.boneco.BonecoFactory;

public class BonecoImagemFactory implements BonecoFactory {

    private static BonecoImagemFactory soleInstance;

    private BonecoImagemFactory() {}

    public static BonecoImagemFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoImagemFactory();
        }
        return soleInstance;
    }

    @Override
    public Boneco getBoneco() {
        // retorna a única instância (Singleton) do BonecoImagem
        return BonecoImagem.getSoleInstance();
    }
}