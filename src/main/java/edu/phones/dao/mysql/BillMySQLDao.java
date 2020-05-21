package edu.phones.dao.mysql;

import edu.phones.dao.BillDao;
import edu.phones.domain.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
@Qualifier("billMySQLDao")
public class BillMySQLDao implements BillDao {

    final Connection connect;

    @Autowired
    public BillMySQLDao(Connection connect) {
        this.connect = connect;
    }

    @Override
    public Bill add(Bill bill) {
        return null;
    }

    @Override
    public Integer remove(Bill bill) {
        return null;
    }

    @Override
    public Integer update(Bill bill) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Bill getById(Integer id) {
        return null;
    }

    @Override
    public List<Bill> getAll() {
        return null;
    }
}
