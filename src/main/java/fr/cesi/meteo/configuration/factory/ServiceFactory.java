package fr.cesi.meteo.configuration.factory;

import fr.cesi.meteo.logic.DataService;
import fr.cesi.meteo.logic.ResponseService;
import lombok.Getter;

@Getter
public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();
    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    private final DataService dataService = new DataService();
    private final ResponseService responseService = new ResponseService();

}
