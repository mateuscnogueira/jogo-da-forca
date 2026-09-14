package jogoforca.dominio.boneco.texto;

import jogoforca.dominio.boneco.Boneco;
import jogoforca.dominio.boneco.BonecoFactory;

public class BonecoTextoFactory implements BonecoFactory {

    private static BonecoTextoFactory soleInstance;

    private BonecoTextoFactory() {}

    public static BonecoTextoFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTextoFactory();
        }
        return soleInstance;
    }

    @Override
    public Boneco getBoneco() {
        // retorna a única instância (Singleton) do BonecoTexto
        return BonecoTexto.getSoleInstance();
    }
}