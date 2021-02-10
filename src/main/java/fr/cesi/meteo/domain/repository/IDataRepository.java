package fr.cesi.meteo.domain.repository;

import fr.cesi.meteo.domain.entity.Data;

public interface IDataRepository {

    Data[] findLasts(int lasts);

    int create(Data data);

    Data find(int id);

    int save(Data data);
}
