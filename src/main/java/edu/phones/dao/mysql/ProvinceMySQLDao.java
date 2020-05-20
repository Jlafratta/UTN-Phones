package edu.phones.dao.mysql;

import edu.phones.dao.ProvinceDao;
import edu.phones.domain.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

// TODO completar los metodos del dao segun corresponda

@Repository
@Qualifier("provinceMySQLDao")
public class ProvinceMySQLDao implements ProvinceDao {

    final Connection connect;

    @Autowired
    public ProvinceMySQLDao(Connection connect) {
        this.connect = connect;
    }

    /** CRUD **/
    @Override
    public Province add(Province province) {
        return null;
    }

    @Override
    public Integer remove(Province province) {
        return null;
    }

    @Override
    public Integer update(Province province) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Province getById(Integer id) {
        return null;
    }

    @Override
    public List<Province> getAll() {
        return null;
    }
}
