package bancodepalavras.dominio.tema;

import dominio.ObjetoDominioImpl;

public class Tema extends ObjetoDominioImpl {
    private String nome;

    private Tema(long id, String nome) {
        super(id);
        setNome(nome);
    }
    
    public static Tema criar(long id, String nome) {
        return new Tema(id, nome);
    }
    
    public static Tema reconstituir(long id, String nome) {
        return new Tema(id, nome);
    }

    public String getNome() {
        return nome;
    }

    public final void setNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome não pode ser null");
        }
        this.nome = nome;
    }
}