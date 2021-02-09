package fr.cesi.divers.mysql.persist;

import fr.cesi.divers.mysql.persist.annotation.Key;
import fr.cesi.divers.mysql.persist.annotation.KeyType;
import fr.cesi.divers.mysql.persist.annotation.Table;
import fr.cesi.divers.mysql.persist.exceptions.PersistClassException;
import fr.cesi.divers.mysql.utils.ReflectionUtils;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class Persist {

    @Getter
    @Setter
    @Key(keyType = KeyType.BIGINT, unsigned = true, notNull = true, autoincrement = true)
    private BigInteger id;

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
