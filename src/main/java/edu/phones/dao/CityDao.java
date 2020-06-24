package edu.phones.dao;

import edu.phones.domain.City;
import edu.phones.exceptions.alreadyExist.CityAlreadyExistException;
import edu.phones.exceptions.notExist.CityNotExistException;

import java.util.List;

public interface CityDao extends AbstractDao<City> {

    /** CRUD **/
    City add(City city) throws CityAlreadyExistException;

    Integer remove(City city);

    Integer update(City city);

    City getById(Integer id);

    List<City> getAll();
}
