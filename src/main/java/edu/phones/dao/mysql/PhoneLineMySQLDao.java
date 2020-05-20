package edu.phones.dao.mysql;

import edu.phones.dao.PhoneLineDao;
import edu.phones.domain.PhoneLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

// TODO completar los metodos del dao segun corresponda

@Repository
@Qualifier("phoneLineMySQLDao")
public class PhoneLineMySQLDao implements PhoneLineDao {

    final Connection connect;

    @Autowired
    public PhoneLineMySQLDao(Connection connect) {
        this.connect = connect;
    }


    /** CRUD **/
    @Override
    public PhoneLine add(PhoneLine line) {
        return null;
    }

    @Override
    public Integer remove(PhoneLine line) {
        return null;
    }

    @Override
    public Integer update(PhoneLine line) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public PhoneLine getById(Integer id) {
        return null;
    }

    @Override
    public List<PhoneLine> getAll() {
        return null;
    }
}
