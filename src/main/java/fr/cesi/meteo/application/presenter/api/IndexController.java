package fr.cesi.meteo.application.presenter.api;

import fr.cesi.meteo.domain.service.IDataService;
import fr.cesi.meteo.configuration.factory.ServiceFactory;
import fr.cesi.meteo.domain.service.IResponseService;
import fr.cesi.meteo.infrastructure.http.Method;
import fr.cesi.meteo.infrastructure.http.Request;
import fr.cesi.meteo.infrastructure.http.Response;
import fr.cesi.meteo.infrastructure.http.annotation.Action;
import org.json.JSONObject;

public class IndexController {

    @Action(path = "/", method = Method.GET)
    public Response getIndexAction(Request request, Response response) {
        IDataService dataService = ServiceFactory.getInstance().getDataService();

        response.setStatusCode(200);
        response.setBody(dataService.getDataCollection(request));

        return response;
    }

    @Action(path = "/create", method = Method.POST)
    public Response postIndexAction(Request request, Response response) {
        IDataService dataService = ServiceFactory.getInstance().getDataService();
        IResponseService responseService = ServiceFactory.getInstance().getResponseService();

        if (dataService.addNewData(request)) {
            response.setStatusCode(201);
            response.setBody(responseService.translatePersist(
                    dataService.getNewlyCreated()
            ));
        } else
            response.setStatusCode(400);

        return response;
    }

    @Action(path = "/update", method = Method.PUT)
    public Response updateIndexAction(Request request, Response response) {
        IDataService dataService = ServiceFactory.getInstance().getDataService();
        IResponseService responseService = ServiceFactory.getInstance().getResponseService();

        if (dataService.updateData(request))
            response.setStatusCode(200);
        else
            response.setStatusCode(400);

        response.setStatusCode(200);
        response.setBody(new JSONObject());
        return response;
    }

    @Action(path = "/delete", method = Method.DELETE)
    public Response deleteIndexAction(Request request, Response response) {
        response.setStatusCode(200);
        response.setBody(new JSONObject());
        return response;
    }

}
