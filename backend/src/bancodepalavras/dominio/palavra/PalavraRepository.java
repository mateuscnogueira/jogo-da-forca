package bancodepalavras.dominio.palavra;

import bancodepalavras.dominio.tema.Tema;
import repository.Repository;
import repository.RepositoryException;

public interface PalavraRepository extends Repository{
    public Palavra getPorId(long id);
    public Palavra[] getPorTema(Tema tema);
    public Palavra[] getTodas();
    public Palavra getPalavra(String palavra);
    public void inserir(Palavra palavra) throws RepositoryException;
    public void atualizar(Palavra palavra) throws RepositoryException;
    public void remover(Palavra palavra) throws RepositoryException;
}
