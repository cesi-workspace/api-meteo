package fr.cesi.meteo.domain.service;

import fr.cesi.divers.mysql.persist.Persist;
import fr.cesi.meteo.infrastructure.http.Request;
import org.json.JSONObject;

public interface IDataService {

    JSONObject getDataCollection(Request request);

    boolean addNewData(Request request);

    Persist getNewlyCreated();

    boolean updateData(Request request);

    boolean deleteData(Request request);
}
