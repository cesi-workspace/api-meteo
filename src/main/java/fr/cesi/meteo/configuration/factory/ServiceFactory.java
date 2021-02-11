package fr.cesi.meteo.configuration.factory;

import fr.cesi.meteo.application.service.ApiKeyService;
import fr.cesi.meteo.application.service.DataService;
import fr.cesi.meteo.application.service.ResponseService;
import fr.cesi.meteo.domain.service.IApiKeyService;
import fr.cesi.meteo.domain.service.IDataService;
import fr.cesi.meteo.domain.service.IResponseService;
import lombok.Getter;

@Getter
public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();
    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    private final IDataService dataService = new DataService();
    private final IResponseService responseService = new ResponseService();
    private final IApiKeyService apiKeyService = new ApiKeyService();

}
