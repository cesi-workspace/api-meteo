package fr.cesi.meteo.configuration.factory;

import fr.cesi.meteo.application.repository.DataRepository;
import lombok.Getter;

@Getter
public class RepositoryFactory {

    private static final RepositoryFactory instance = new RepositoryFactory();
    private RepositoryFactory() {}

    public static RepositoryFactory getInstance() {
        return instance;
    }

    private DataRepository dataRepository = new DataRepository();

}
