package fr.cesi.meteo.domain.repository;

import fr.cesi.meteo.domain.model.Data;
import fr.cesi.divers.mysql.persist.PersistQuery;

public class DataRepository implements IDataRepository {

    @Override
    public Data[] findLasts(int lasts) {
        return new PersistQuery<>(Data.class)
                .select("*")
                .orderBy("id DESC")
                .limit(lasts)
                .executeQuery()
                .toArray(new Data[0]);
    }

}
