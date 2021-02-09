package fr.cesi.mysql.utils;

import fr.cesi.mysql.persist.Persist;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtils {


    public static Field getPersistDeclaredField(String fieldName, Class<? extends Persist> clazz) throws NoSuchFieldException {
        List<String> fields = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());

        if (fields.contains(fieldName))
            return clazz.getDeclaredField(fieldName);
        return clazz.getSuperclass().getDeclaredField(fieldName);
    }

    public static List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> list = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));

        if (!clazz.equals(Persist.class))
            list.addAll(getDeclaredFields(clazz.getSuperclass()));

        return list;
    }

    public static HashMap<String, Object> getDeclaredFields(Object object, Class<?> clazz) {
        HashMap<String, Object> map = new HashMap<>();

        for (Field declaredField : clazz.getDeclaredFields()) {
            try {
                declaredField.setAccessible(true);
                Object fieldValue = declaredField.get(object);

                if ((fieldValue instanceof Long && (Long) fieldValue != 0 || !(fieldValue instanceof Long) && fieldValue != null)
                        && (!(fieldValue instanceof Double) || (Double) fieldValue != 0)
                        && (!(fieldValue instanceof Integer) || (Integer) fieldValue != 0))
                    map.put(declaredField.getName(), declaredField.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (!clazz.equals(Persist.class))
            map.putAll(getDeclaredFields(object, clazz.getSuperclass()));

        return map;
    }

}
