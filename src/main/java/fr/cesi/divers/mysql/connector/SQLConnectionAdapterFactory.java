package fr.cesi.divers.mysql.connector;

import fr.cesi.divers.mysql.persist.Persist;

import java.util.HashMap;
import java.util.Optional;

public class SQLConnectionAdapterFactory {

    private SQLConnectionAdapterFactory(){}
    private static final SQLConnectionAdapterFactory instance = new SQLConnectionAdapterFactory();
    public static SQLConnectionAdapterFactory getInstance() {
        return instance;
    }

    private final HashMap<Class<? extends Persist>, SQLConnectionAdapter> SQLConnectionAdapters = new HashMap<>();

    public SQLConnectionAdapter addConnectionAdapter(Class<? extends Persist> clazz, SQLConnectionAdapter sqlConnectionAdapter) {
        return SQLConnectionAdapters.put(clazz, sqlConnectionAdapter);
    }

    public Optional<SQLConnectionAdapter> getSQLConnectionAdapter(Class<? extends Persist> clazz) {
        return Optional.ofNullable(SQLConnectionAdapters.get(clazz));
    }

}
