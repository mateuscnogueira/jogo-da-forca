package bancodepalavras.dominio.letra.texto;

import bancodepalavras.dominio.letra.Letra;
import bancodepalavras.dominio.letra.LetraFactoryImpl;

public class LetraTextoFactory extends LetraFactoryImpl {

    private static LetraTextoFactory soleInstance;

    private LetraTextoFactory() {
        super(); 
    }

    public static LetraTextoFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new LetraTextoFactory();
        }
        return soleInstance;
    }

    @Override
    protected Letra criarLetra(char codigo) {
        return new LetraTexto(codigo);
    }

    @Override
    protected Letra criarLetraEncoberta() {
        return new LetraTexto('_');
    }
}