package fr.cesi.meteo.configuration.factory;

import fr.cesi.meteo.application.repository.ApiKeyRepository;
import fr.cesi.meteo.application.repository.DataRepository;
import fr.cesi.meteo.domain.repository.IApiKeyRepository;
import fr.cesi.meteo.domain.repository.IDataRepository;
import lombok.Getter;

@Getter
public class RepositoryFactory {

    private static final RepositoryFactory instance = new RepositoryFactory();
    private RepositoryFactory() {}

    public static RepositoryFactory getInstance() {
        return instance;
    }

    private IDataRepository dataRepository = new DataRepository();

    private IApiKeyRepository apiKeyRepository = new ApiKeyRepository();

}
