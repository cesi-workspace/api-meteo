package fr.cesi.meteo.application.service;

import fr.cesi.meteo.configuration.factory.RepositoryFactory;
import fr.cesi.meteo.domain.entity.ApiKey;
import fr.cesi.meteo.domain.repository.IApiKeyRepository;
import fr.cesi.meteo.domain.service.IApiKeyService;
import fr.cesi.meteo.infrastructure.http.Request;

public class ApiKeyService implements IApiKeyService {

    @Override
    public boolean verifyApiToken(Request request) {
        IApiKeyRepository apiKeyRepository = RepositoryFactory.getInstance().getApiKeyRepository();
        String token = null;

        if (request.getHeaders().containsKey("Authorization"))
            token = request.getHeaders().getFirst("Authorization");
        else
            return false;

        ApiKey apiKey = apiKeyRepository.findByKey(token);

        return apiKey != null;
    }

}
