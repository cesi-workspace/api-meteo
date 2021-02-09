package fr.cesi.meteo.logic;

import fr.cesi.meteo.logic.service.IResponseService;
import fr.cesi.divers.mysql.persist.Persist;
import fr.cesi.divers.mysql.utils.ReflectionUtils;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

public class ResponseService implements IResponseService {

    @SneakyThrows
    @Override
    public JSONObject translatePersist(Persist persist) {
        JSONObject jsonObject = new JSONObject();
        List<Field> declaredFields = ReflectionUtils.getDeclaredFields(persist.getClass());

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            jsonObject.put(declaredField.getName(), declaredField.get(persist));
        }

        return jsonObject;
    }
}
