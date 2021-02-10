package fr.cesi.divers.mysql.utils;

import fr.cesi.divers.mysql.persist.Persist;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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

}
