package bancodepalavras.dominio.letra.imagem;

import bancodepalavras.dominio.letra.Letra;
import bancodepalavras.dominio.letra.LetraFactoryImpl;

public class LetraImagemFactory extends LetraFactoryImpl {

    private static LetraImagemFactory soleInstance;

    private LetraImagemFactory() {
        super();
    }

    public static LetraImagemFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new LetraImagemFactory();
        }
        return soleInstance;
    }

    @Override
    protected Letra criarLetra(char codigo) {
        return new LetraImagem(codigo);
    }

    @Override
    protected Letra criarLetraEncoberta() {
        // Passamos o '_' apenas para satisfazer o construtor, pois o método exibir() seria o responsável por desenhar a imagem do "quadradinho" em branco futuramente
        return new LetraImagem('_');
    }
}