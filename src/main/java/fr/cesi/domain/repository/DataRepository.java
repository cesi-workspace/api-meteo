package fr.cesi.domain.repository;

import fr.cesi.domain.model.Data;
import fr.cesi.mysql.persist.PersistQuery;

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
