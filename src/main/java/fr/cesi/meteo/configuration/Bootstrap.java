package fr.cesi.meteo.configuration;

import fr.cesi.divers.mysql.connector.SQLConnectionAdapter;
import fr.cesi.divers.mysql.connector.SQLConnectionAdapterFactory;
import fr.cesi.divers.mysql.persist.PersistQuery;
import fr.cesi.meteo.domain.entity.ApiKey;
import fr.cesi.meteo.domain.entity.Data;

public class Bootstrap {

    public void register(SQLConnectionAdapter sqlConnectionAdapter) {
        SQLConnectionAdapterFactory.getInstance().addConnectionAdapter(Data.class, sqlConnectionAdapter);
        SQLConnectionAdapterFactory.getInstance().addConnectionAdapter(ApiKey.class, sqlConnectionAdapter);

        new PersistQuery<Data>(Data.class).createTable();
        new PersistQuery<ApiKey>(ApiKey.class).createTable();
    }

}
