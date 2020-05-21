package edu.phones.dao.mysql;

import edu.phones.dao.CityDao;
import edu.phones.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

// TODO completar los metodos del dao segun corresponda

@Repository
@Qualifier("cityMySQLDao")
public class CityMySQLDao implements CityDao {

    final Connection connect;

    @Autowired
    public CityMySQLDao(Connection connect) {
        this.connect = connect;
    }

    /** CRUD **/
    @Override
    public City add(City city) {
        return null;
    }

    @Override
    public Integer update(City city) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Integer remove(City city) {
        return null;
    }

    @Override
    public City getById(Integer id) {
        return null;
    }

    @Override
    public List<City> getAll() {
        return null;
    }
}
