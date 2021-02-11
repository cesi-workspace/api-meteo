package fr.cesi.meteo.domain.service;

import fr.cesi.meteo.infrastructure.http.Request;

public interface IApiKeyService {

    boolean verifyApiToken(Request request);

}
