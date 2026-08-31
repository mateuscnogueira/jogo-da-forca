package bancodepalavras.dominio.tema;

import repository.Repository;
import repository.RepositoryException;

public interface TemaRepository extends Repository {
    public Tema getPorId(long id);
    public Tema[] getPorNome(String nome);
    public Tema[] getTodos();
    public void inserir(Tema tema) throws RepositoryException;
    public void atualizar(Tema tema) throws RepositoryException;
    public void remover(Tema tema) throws RepositoryException;
}