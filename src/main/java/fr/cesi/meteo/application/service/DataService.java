package fr.cesi.meteo.application.service;

import fr.cesi.divers.mysql.persist.Persist;
import fr.cesi.meteo.application.repository.DataRepository;
import fr.cesi.meteo.configuration.factory.RepositoryFactory;
import fr.cesi.meteo.configuration.factory.ServiceFactory;
import fr.cesi.meteo.domain.entity.Data;
import fr.cesi.meteo.domain.repository.IDataRepository;
import fr.cesi.meteo.domain.service.IDataService;
import fr.cesi.meteo.domain.service.IResponseService;
import fr.cesi.meteo.infrastructure.http.Request;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataService implements IDataService {

    @Override
    public JSONObject getDataCollection(Request request) {
        int limit = 10;

        if (request.getBody().has("limit"))
            limit = request.getBody().getInt("limit");

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

    @Override
    public boolean addNewData(Request request) {
        JSONObject body = request.getBody();

        if (!(body.has("temperature") && body.has("humidity")))
            return false;

        double temperature = body.getDouble("temperature");
        int humidity = body.getInt("humidity");

        DataRepository dataRepository = RepositoryFactory.getInstance().getDataRepository();
        Data data = new Data();

        data.setHumidity(humidity);
        data.setTemperature(temperature);
        data.setCreatedAt(new Date().getTime() / 1000);

        return dataRepository.create(data) > 0;
    }

    @Override
    public boolean updateData(Request request) {
        JSONObject body = request.getBody();

        if (!body.has("id"))
            return false;

        DataRepository dataRepository = RepositoryFactory.getInstance().getDataRepository();
        Data data = dataRepository.find(body.getInt("id"));

        if (body.has("humidity"))
            data.setHumidity(body.getInt("humidity"));

        if (body.has("temperature"))
            data.setTemperature(body.getDouble("temperature"));

        return dataRepository.save(data) > 0;
    }

    @Override
    public Persist getNewlyCreated() {
        DataRepository dataRepository = RepositoryFactory.getInstance().getDataRepository();

        return dataRepository.findLasts(1)[0];
    }

    @Override
    public boolean deleteData(Request request) {
        DataRepository dataRepository = RepositoryFactory.getInstance().getDataRepository();
        JSONObject body = request.getBody();

        if (!body.has("id"))
            return false;

        return dataRepository.delete(body.getInt("id")) > 0;
    }
}
