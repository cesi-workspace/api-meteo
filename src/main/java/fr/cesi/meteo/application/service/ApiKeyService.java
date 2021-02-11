package fr.cesi.meteo.application.service;

import fr.cesi.meteo.domain.service.IApiKeyService;
import fr.cesi.meteo.infrastructure.http.Request;

public class ApiKeyService implements IApiKeyService {

    @Override
    public boolean verifyApiToken(Request request) {
        return false;
    }

}
