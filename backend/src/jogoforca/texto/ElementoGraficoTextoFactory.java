package jogoforca.texto;

import bancodepalavras.dominio.letra.Letra;
import bancodepalavras.dominio.letra.LetraFactory;
import bancodepalavras.dominio.letra.texto.LetraTextoFactory;
import jogoforca.dominio.boneco.Boneco;
import jogoforca.dominio.boneco.BonecoFactory;
import jogoforca.dominio.boneco.texto.BonecoTextoFactory;
import jogoforca.ElementoGraficoFactory;

public class ElementoGraficoTextoFactory implements ElementoGraficoFactory {
    
    private static ElementoGraficoTextoFactory soleInstance = null;
    
    private BonecoFactory bonecoFactory;
    private LetraFactory letraFactory;

    private ElementoGraficoTextoFactory() {
        // acessa os singletons e setando os campos das agregações
        this.bonecoFactory = BonecoTextoFactory.getSoleInstance();
        this.letraFactory = LetraTextoFactory.getSoleInstance();
    }

    public static ElementoGraficoTextoFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new ElementoGraficoTextoFactory();
        }
        return soleInstance;
    }

    @Override
    public Boneco getBoneco() {
        return this.bonecoFactory.getBoneco();
    }

    @Override
    public Letra getLetra(char codigo) {
        return this.letraFactory.getLetra(codigo);
    }

    @Override
    public Letra getLetraEncoberta() {
        return this.letraFactory.getLetraEncoberta();
    }
}