package fr.cesi.meteo.logic.service;

import fr.cesi.meteo.infrastructure.http.Request;
import org.json.JSONObject;

public interface IDataService {

    JSONObject getDataCollection(Request request);
}
