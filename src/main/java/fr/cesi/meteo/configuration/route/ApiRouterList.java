package fr.cesi.meteo.configuration.route;

import fr.cesi.meteo.application.presenter.api.IndexController;
import fr.cesi.meteo.infrastructure.http.Router;

public class ApiRouterList extends Router {

    ApiRouterList() {
        getControllers().put("/", IndexController.class);
    }

}
