package fr.cesi.infrastructure.configuration;

import fr.cesi.application.controller.IndexController;
import fr.cesi.infrastructure.http.Router;

public class ApiRouterList extends Router {

    ApiRouterList() {
        getControllers().put("/", IndexController.class);
    }

}
