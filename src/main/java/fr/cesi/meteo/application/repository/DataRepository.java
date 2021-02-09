package fr.cesi.meteo.application.repository;

import fr.cesi.meteo.domain.entity.Data;
import fr.cesi.divers.mysql.persist.PersistQuery;
import fr.cesi.meteo.domain.repository.IDataRepository;

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
