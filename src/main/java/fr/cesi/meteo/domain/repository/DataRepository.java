package fr.cesi.meteo.domain.repository;

import fr.cesi.meteo.domain.model.Data;

import java.util.List;

public class DataRepository implements IDataRepository {

    @Override
    public List<Data> findLasts(int lasts) {
        return null;
    }
}
