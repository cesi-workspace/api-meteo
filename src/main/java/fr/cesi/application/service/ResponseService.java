package fr.cesi.application.service;

import fr.cesi.application.service.interfaces.IResponseService;
import fr.cesi.mysql.persist.Persist;
import fr.cesi.mysql.utils.ReflectionUtils;
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
