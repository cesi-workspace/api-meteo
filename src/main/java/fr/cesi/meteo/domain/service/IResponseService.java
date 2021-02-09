package fr.cesi.meteo.domain.service;

import fr.cesi.divers.mysql.persist.Persist;
import org.json.JSONObject;

public interface IResponseService {

    JSONObject translatePersist(Persist persist);

}
