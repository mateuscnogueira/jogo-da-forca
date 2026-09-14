package factory;

import repository.Repository;

public abstract class EntityFactory {
    
    private Repository repository;

    protected EntityFactory(Repository repository) {
        this.repository = repository;
    }

    protected Repository getRepository() {
        return repository;
    }

    protected long getProximoId() {
        return repository.getProximoId();
    }
}