package fr.cesi.domain.repository;

import fr.cesi.domain.model.Data;

public interface IDataRepository {

    Data[] findLasts(int lasts);

}
