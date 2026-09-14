package bancodepalavras.dominio.letra;

import java.util.HashMap;
import java.util.Map;

public abstract class LetraFactoryImpl implements LetraFactory {

    // cache para as letras normais (26)
    private Map<Character, Letra> pool;
    
    // cache exclusivo para a letra encoberta
    private Letra encoberta;

    protected LetraFactoryImpl() {
        this.pool = new HashMap<>();
    }

    @Override
    public final Letra getLetra(char codigo) {
        if (pool.containsKey(codigo)) {
            return pool.get(codigo);
        }
        
        Letra novaLetra = this.criarLetra(codigo);
        pool.put(codigo, novaLetra);
        return novaLetra;
    }

    @Override
    public final Letra getLetraEncoberta() {
        // se a letra encoberta ainda não foi criada, ela é instanciada na primeira vez
        if (this.encoberta == null) {
            this.encoberta = this.criarLetraEncoberta();
        }
        return this.encoberta;
    }

    protected abstract Letra criarLetra(char codigo);
    protected abstract Letra criarLetraEncoberta();
}