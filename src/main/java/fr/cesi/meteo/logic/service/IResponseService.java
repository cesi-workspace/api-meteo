package fr.cesi.meteo.logic.service;

import fr.cesi.divers.mysql.persist.Persist;
import org.json.JSONObject;

public interface IResponseService {

    JSONObject translatePersist(Persist persist);

}
