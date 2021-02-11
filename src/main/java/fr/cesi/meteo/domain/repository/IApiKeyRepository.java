package fr.cesi.meteo.domain.repository;

import fr.cesi.meteo.domain.entity.ApiKey;

public interface IApiKeyRepository {
    ApiKey findByKey(String token);
}
