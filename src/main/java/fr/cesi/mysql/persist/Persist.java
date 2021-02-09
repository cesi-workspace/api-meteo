package fr.cesi.mysql.persist;

import fr.cesi.mysql.persist.annotation.Key;
import fr.cesi.mysql.persist.annotation.KeyType;
import fr.cesi.mysql.persist.annotation.Table;
import fr.cesi.mysql.persist.exception.PersistClassException;
import fr.cesi.mysql.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class Persist {

    @Key(keyType = KeyType.BIGINT, unsigned = true, notNull = true, autoincrement = true)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public Persist setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public static String getTable(Class<? extends Persist> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            try {
                throw new PersistClassException("Persist class must have Table annotation.");
            } catch (PersistClassException e) {
                e.printStackTrace();
            }

        return table.name();
    }

    public String getTable() {
        return getTable(getClass());
    }

    Persist parseFromResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= columnsNumber; i++) {
            try {
                Field field = ReflectionUtils.getPersistDeclaredField(resultSetMetaData.getColumnName(i), getClass());
                field.setAccessible(true);
                field.set(this, resultSet.getObject(i));
            } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        }

        return this;
    }

}
