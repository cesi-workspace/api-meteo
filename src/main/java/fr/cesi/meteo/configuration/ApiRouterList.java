package fr.cesi.meteo.configuration;

import fr.cesi.meteo.controller.IndexController;
import fr.cesi.meteo.http.Router;

public class ApiRouterList extends Router {

    ApiRouterList() {
        getControllers().put("/", IndexController.class);
    }

}
