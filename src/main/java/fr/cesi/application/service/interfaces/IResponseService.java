package fr.cesi.application.service.interfaces;

import fr.cesi.mysql.persist.Persist;
import org.json.JSONObject;

public interface IResponseService {

    JSONObject translatePersist(Persist persist);

}
