package fr.cesi.mysql.persist;

import fr.cesi.mysql.connector.SQLConnectionAdapter;
import fr.cesi.mysql.connector.SQLConnectionAdapterFactory;
import fr.cesi.mysql.persist.annotation.Key;
import fr.cesi.mysql.persist.exception.PersistQueryException;
import fr.cesi.mysql.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersistQuery<T extends Persist> {

    private final Class<? extends Persist> clazz;

    public PersistQuery(Class<? extends Persist> type) {
        this.clazz = type;
    }

    public List<T> findAll() {
        return find(null);
    }
    
    public T getOrCreate(Persist persist) {
        List<T> ts = find(persist);

        if (ts.size() > 1)
            try {
                throw new PersistQueryException("More than one " + persist.getClass().getSimpleName() + " founded!");
            } catch (PersistQueryException e) {
                e.printStackTrace();
            }
        else if (ts.size() == 0) {
            create(persist);
            persist = find(persist).get(0);
        } else
            persist = ts.get(0);

        return (T) persist;
    }

    public boolean create(Persist persist) {
        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapterFactory
                .getInstance().getSQLConnectionAdapter(clazz);
        HashMap<String, Object> declaredFields = new HashMap<>(ReflectionUtils.getDeclaredFields(persist, persist.getClass()));
        String query = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                persist.getTable(),
                String.join(",", declaredFields.keySet()),
                declaredFields.values().stream().map(o -> "?").collect(Collectors.joining(","))
        );

        if (sqlConnectionAdapter.isPresent())
            return sqlConnectionAdapter.get().update(query, declaredFields.values().toArray(new Object[0])) > 0;
        return false;
    }

    public boolean save(Persist persist) {
        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapterFactory
                .getInstance().getSQLConnectionAdapter(clazz);
        HashMap<String, Object> declaredFields = new HashMap<>(ReflectionUtils.getDeclaredFields(persist, persist.getClass()));
        String query = "UPDATE `" + persist.getTable() + "` SET ";

        declaredFields.remove("id");
        query += declaredFields.keySet().stream().map(key -> "`" + key + "`=?").collect(Collectors.joining(", "));
        query += " WHERE `id`=" + persist.getId().intValue();

        if (sqlConnectionAdapter.isPresent())
            return sqlConnectionAdapter.get().update(query, declaredFields.values().toArray(new Object[0])) > 0;
        return false;
    }

    public List<T> find(Persist persist) {
        List<T> persists = new ArrayList<>();
        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapterFactory
                .getInstance().getSQLConnectionAdapter(clazz);
        HashMap<String, Object> declaredFields = new HashMap<>();
        String whereClause = "";

        if(persist != null) {
            declaredFields.putAll(ReflectionUtils.getDeclaredFields(persist, persist.getClass()));
            whereClause = "WHERE " + String.join("=? AND ", declaredFields.keySet()) + "=?";
        }

        if (sqlConnectionAdapter.isPresent()) {
            Optional<ResultSet> resultSet = sqlConnectionAdapter.get().query(String.format(
                    "SELECT * FROM %s %s",
                    Persist.getTable(clazz),
                    whereClause
            ), declaredFields.values().toArray(new Object[0]));

            if (resultSet.isPresent()) {
                try {
                    while (resultSet.get().next()) {
                        T persistType = (T) clazz.newInstance().parseFromResultSet(resultSet.get());
                        persists.add(persistType);
                    }
                } catch (SQLException | InstantiationException | IllegalAccessException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return persists;
    }

    public int delete(Persist persist) {
        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapterFactory
                .getInstance().getSQLConnectionAdapter(clazz);
        HashMap<String, Object> declaredFields = new HashMap<>();
        String whereClause = "";

        if(persist != null) {
            declaredFields.putAll(ReflectionUtils.getDeclaredFields(persist, persist.getClass()));
            whereClause = "WHERE " + String.join("=? AND ", declaredFields.keySet()) + "=?";
        }

        if (sqlConnectionAdapter.isPresent()) {
            return sqlConnectionAdapter.get().update(String.format(
                    "DELETE FROM %s %s",
                    Persist.getTable(clazz),
                    whereClause
            ), declaredFields.values().toArray(new Object[0]));
        } else return 0;
    }

    public int createTable() {
        Optional<SQLConnectionAdapter> sqlConnectionAdapter = SQLConnectionAdapterFactory
                .getInstance().getSQLConnectionAdapter(clazz);
        StringBuilder createTableQuery = new StringBuilder(
                String.format("CREATE TABLE IF NOT EXISTS `%s` (", Persist.getTable(clazz))
        );

        List<Field> fields = ReflectionUtils.getDeclaredFields(clazz);
        fields = fields.stream().filter(field -> field.getAnnotation(Key.class) != null).collect(Collectors.toList());

        for (Field field : fields) {
            Key key = field.getAnnotation(Key.class);
            String fieldQuery = "`" + field.getName() + "` " + key.keyType().name().toLowerCase();

            if (key.length() != -1)
                fieldQuery += "(" + key.length() + ")";

            if (key.unsigned())
                fieldQuery += " UNSIGNED";

            if (key.notNull())
                fieldQuery += " NOT NULL";

            if (key.autoincrement())
                fieldQuery += " AUTO_INCREMENT";

            fieldQuery += ", ";
            createTableQuery.append(fieldQuery);

            if (key.autoincrement())
                createTableQuery.append("KEY(").append(field.getName()).append("), ");
        }

        createTableQuery
                .replace(createTableQuery.length()-", ".length(), createTableQuery.length(), "")
                .append(");");

        return sqlConnectionAdapter.map(connectionAdapter -> connectionAdapter.update(createTableQuery.toString()))
                .orElse(0);
    }

}
