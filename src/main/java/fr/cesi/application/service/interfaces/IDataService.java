package fr.cesi.application.service.interfaces;

import fr.cesi.infrastructure.http.Request;
import org.json.JSONObject;

public interface IDataService {

    JSONObject getDataCollection(Request request);
}
