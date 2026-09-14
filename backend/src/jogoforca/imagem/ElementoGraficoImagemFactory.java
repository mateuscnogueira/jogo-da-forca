package jogoforca.imagem;

import bancodepalavras.dominio.letra.Letra;
import bancodepalavras.dominio.letra.LetraFactory;
import bancodepalavras.dominio.letra.imagem.LetraImagemFactory;
import jogoforca.dominio.boneco.Boneco;
import jogoforca.dominio.boneco.BonecoFactory;
import jogoforca.dominio.boneco.imagem.BonecoImagemFactory;
import jogoforca.ElementoGraficoFactory;

public class ElementoGraficoImagemFactory implements ElementoGraficoFactory {
    
    private static ElementoGraficoImagemFactory soleInstance = null;
    
    private BonecoFactory bonecoFactory;
    private LetraFactory letraFactory;

    private ElementoGraficoImagemFactory() {
        // acessa os singletons e setando os campos das agregações para a família de Imagem
        this.bonecoFactory = BonecoImagemFactory.getSoleInstance();
        this.letraFactory = LetraImagemFactory.getSoleInstance();
    }

    public static ElementoGraficoImagemFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new ElementoGraficoImagemFactory();
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