package edu.phones.service;

import edu.phones.dao.CityDao;
import edu.phones.domain.City;
import edu.phones.exceptions.notExist.CityNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    CityDao cityDao;

    @Autowired
    public CityService(@Qualifier("cityMySQLDao")CityDao cityDao) {
        this.cityDao = cityDao;
    }

    /** CRUD **/
    public City createCity(City city) {
        return cityDao.add(city);
    }

    public void remove(City city) throws CityNotExistException {
        if(cityDao.remove(city) == 0){
            throw new CityNotExistException();
        }
    }

    public City updateCity(City city) throws CityNotExistException {
        if (cityDao.update(city) > 0){
            return city;
        }else {
            throw new CityNotExistException();
        }
    }

    public City getCity(Integer cityId) {
        return cityDao.getById(cityId);
    }

    public List<City> getAll() {
        return cityDao.getAll();
    }
}
