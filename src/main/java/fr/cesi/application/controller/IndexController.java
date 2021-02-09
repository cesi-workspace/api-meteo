package fr.cesi.application.controller;

import fr.cesi.application.service.interfaces.IDataService;
import fr.cesi.configuration.factory.ServiceFactory;
import fr.cesi.infrastructure.http.Method;
import fr.cesi.infrastructure.http.Request;
import fr.cesi.infrastructure.http.Response;
import fr.cesi.infrastructure.http.annotation.Action;
import org.json.JSONObject;

public class IndexController  {

    @Action(path = "/", method = Method.GET)
    public Response getIndexAction(Request request, Response response) {
        IDataService dataService = ServiceFactory.getInstance().getDataService();

        response.setStatusCode(200);
        response.setBody(dataService.getDataCollection(request));
        
        return response;
    }

    @Action(path = "/create", method = Method.POST)
    public Response postIndexAction(Request request, Response response) {
        response.setStatusCode(200);
        response.setBody(new JSONObject());
        return response;
    }

    @Action(path = "/update", method = Method.UPDATE)
    public Response updateIndexAction(Request request, Response response) {
        response.setStatusCode(200);
        response.setBody(new JSONObject());
        return response;
    }

}
