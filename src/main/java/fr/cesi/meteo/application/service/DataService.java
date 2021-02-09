package fr.cesi.meteo.application.service;

import fr.cesi.meteo.application.service.interfaces.IDataService;
import fr.cesi.meteo.application.service.interfaces.IResponseService;
import fr.cesi.meteo.configuration.factory.RepositoryFactory;
import fr.cesi.meteo.configuration.factory.ServiceFactory;
import fr.cesi.meteo.domain.model.Data;
import fr.cesi.meteo.domain.repository.interfaces.IDataRepository;
import fr.cesi.meteo.infrastructure.http.Request;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataService implements IDataService {

    @Override
    public JSONObject getDataCollection(Request request) {
        int limit = 10;
        try {
            limit = Integer.parseInt(request.getParameters().getOrDefault("limit","10"));
        } catch (NumberFormatException ignored) {}

        IDataRepository dataRepository = RepositoryFactory.getInstance().getDataRepository();
        Data[] lasts = dataRepository.findLasts(limit);

        JSONObject response = new JSONObject();
        List<JSONObject> responseDatas = new ArrayList<>();
        IResponseService responseService = ServiceFactory.getInstance().getResponseService();

        for (Data last : lasts)
            responseDatas.add(responseService.translatePersist(last));

        response.put("datas", responseDatas);

        return response;
    }

}
