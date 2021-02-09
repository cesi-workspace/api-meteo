package fr.cesi.meteo.application.service.interfaces;

import fr.cesi.meteo.infrastructure.http.Request;
import org.json.JSONObject;

public interface IDataService {

    JSONObject getDataCollection(Request request);
}
