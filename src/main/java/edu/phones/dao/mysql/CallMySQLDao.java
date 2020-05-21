package edu.phones.dao.mysql;

import edu.phones.dao.CallDao;
import edu.phones.domain.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
@Qualifier("callMySQLDao")
public class CallMySQLDao implements CallDao {

    final Connection connect;

    @Autowired
    public CallMySQLDao(Connection connect) {
        this.connect = connect;
    }

    @Override
    public Call add(Call call) {
        return null;
    }

    @Override
    public Integer remove(Call call) {
        return null;
    }

    @Override
    public Integer update(Call call) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Call getById(Integer id) {
        return null;
    }

    @Override
    public List<Call> getAll() {
        return null;
    }
}
