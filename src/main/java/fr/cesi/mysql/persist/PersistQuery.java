package fr.cesi.mysql.persist;

import fr.cesi.meteo.domain.model.Data;
import fr.cesi.mysql.Values;
import fr.cesi.mysql.connector.SQLConnectionAdapter;
import fr.cesi.mysql.connector.SQLConnectionAdapterFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PersistQuery<T extends Persist> {
    private final Class<? extends Persist> clazz;

    private String query = "";
    private Object[] objectsForStatement = new Object[0];

    public PersistQuery<T> select(Persist persist) {
        HashMap<String, Object> persistData = getPersistData(persist);

        return select(persistData.keySet().toArray(new String[0]))
                .where(persistData);
    }

    public PersistQuery<T> select(String... fields) {
        String table = Persist.getTable(Data.class);
        query += String.format(
                "SELECT %s FROM %s ",
                String.join(",", fields),
                Persist.getTable(Data.class)
        );
        return this;
    }

    public PersistQuery<T> update(Persist persist) {
        HashMap<String, Object> persistData = getPersistData(persist);
        return update(persistData).where(persistData);
    }

    public PersistQuery<T> update(Map<String, Object> fields) {
        String table = Persist.getTable(clazz);

        query += String.format(
                "UPDATE %s SET %s",
                table,
                String.join("=?, ", fields.keySet())
        );
        objectsForStatement = fields.values().toArray(new Object[0]);

        return this;
    }

    public PersistQuery<T> insert(Persist persist) {
        return insert(getPersistData(persist));
    }

    public PersistQuery<T> insert(Map<String, Object> fields) {
        String table = Persist.getTable(Data.class);
        query += String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                table,
                String.join(", ", fields.keySet()),
                fields.values().stream().map(o -> "?").collect(Collectors.joining(", "))
        );
        objectsForStatement = fields.values().toArray(new Object[0]);
        return this;
    }

    public PersistQuery<T> where(HashMap<String, Object> where) {
        query += "WHERE ";

        ((HashMap<String, Object>) where.clone()).forEach((s, o) -> {
            if (o == null || Integer.parseInt(String.valueOf(o)) == Values.NULL_INTEGER) {
                where.remove(s);
            }
        });

        for (String s : where.keySet())
            query += s + "=? AND ";
        objectsForStatement = where.values().toArray(new Object[0]);

        query = query.substring(0, query.length()-"AND ".length());
        return this;
    }

    public PersistQuery<T> orderBy(String order) {
        query += "ORDER BY " + order;
        return this;
    }

    @SneakyThrows
    public List<T> executeQuery() {
        List<T> persists = new ArrayList<>();
        Optional<SQLConnectionAdapter> optionalSQLConnectionAdapter = SQLConnectionAdapterFactory
                .getInstance()
                .getSQLConnectionAdapter(Data.class);

        if (optionalSQLConnectionAdapter.isPresent()) {
            SQLConnectionAdapter sqlConnectionAdapter = optionalSQLConnectionAdapter.get();

            Optional<ResultSet> optionalResultSet = sqlConnectionAdapter.query(query, objectsForStatement);

            if (optionalResultSet.isPresent()) {
                ResultSet resultSet = optionalResultSet.get();
                while (resultSet.next()) {
                    T persistType = (T) clazz.newInstance().parseFromResultSet(resultSet);
                    persists.add(persistType);
                }
            }
        }

        return persists;
    }

    public int executeUpdate() {
        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapterFactory
                .getInstance()
                .getSQLConnectionAdapter(Data.class);

        return sqlConnectionAdapter.map(connectionAdapter -> connectionAdapter.update(query, objectsForStatement)).orElse(0);
    }


    @SneakyThrows
    private HashMap<String, Object> getPersistData(Persist persist) {
        final HashMap<String, Object> persistData = new HashMap<>();
        Class<?>[] classes = new Class<?>[] {
                clazz.getSuperclass(),
                clazz
        };

        for (Class<?> aClass : classes) {
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                persistData.put(declaredField.getName(), declaredField.get(persist));
            }
        }

        return persistData;
    }
}
