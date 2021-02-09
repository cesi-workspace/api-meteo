package fr.cesi.meteo.domain.repository;

import fr.cesi.meteo.domain.model.Data;

import java.util.List;

public interface IDataRepository {

    List<Data> findLasts(int lasts);

}
