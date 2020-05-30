package edu.phones.dao.mysql;

import edu.phones.dao.TariffDao;
import edu.phones.domain.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

// TODO completar los metodos del dao segun corresponda

@Repository
@Qualifier("tariffMySQLDao")
public class TariffMySQLDao implements TariffDao {

    final Connection connect;

    @Autowired
    public TariffMySQLDao(Connection connect) {
        this.connect = connect;
    }

    /** CRUD **/
    @Override
    public Tariff add(Tariff tariff) {
        return null;
    }

    @Override
    public Integer remove(Tariff tariff) {
        return null;
    }

    @Override
    public Integer update(Tariff tariff) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Tariff getById(Integer id) {
        return null;
    }

    @Override
    public List<Tariff> getAll() {
        return null;
    }
}
