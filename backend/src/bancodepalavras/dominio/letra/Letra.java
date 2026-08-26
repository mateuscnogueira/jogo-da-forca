package bancodepalavras.dominio.letra;

public abstract class Letra {
    private char codigo;

    protected Letra(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    public abstract void exibir(Object contexto);

    @Override
    public int hashCode() {
        return this.codigo + this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Letra)) {
            return false;
        }

        Letra outra = (Letra) obj;
        return this.codigo == outra.codigo && this.getClass().equals(outra.getClass());
    }

    @Override
    public final String toString() {
        return "Letra [codigo=" + codigo + "]";
    }
}
