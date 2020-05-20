package edu.phones.dao;

import edu.phones.dao.AbstractDao;
import edu.phones.domain.City;
import edu.phones.domain.User;
import edu.phones.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface CityDao extends AbstractDao<City> {

    /** CRUD **/
    City add(City city);
    Integer remove(City city);
    Integer update(City city);
    City getById(Integer id);
    List<City> getAll();
}
