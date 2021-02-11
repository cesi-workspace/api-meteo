package fr.cesi.meteo.application.repository;

import fr.cesi.divers.mysql.persist.Persist;
import fr.cesi.divers.mysql.persist.PersistQuery;
import fr.cesi.meteo.domain.entity.ApiKey;
import fr.cesi.meteo.domain.repository.IApiKeyRepository;

import java.util.HashMap;
import java.util.List;

public class ApiKeyRepository implements IApiKeyRepository {

    @Override
    public ApiKey findByKey(String token) {
        List<Persist> apiKeys = new PersistQuery<>(ApiKey.class)
                .select("*")
                .where(new HashMap<String, Object>() {{
                    put("key", token);
                }})
                .executeQuery();

        if (apiKeys.size() > 0)
            return (ApiKey) apiKeys.get(0);
        return null;
    }
}
