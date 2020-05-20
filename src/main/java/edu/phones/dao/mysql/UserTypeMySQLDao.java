package edu.phones.dao.mysql;

import edu.phones.dao.UserTypeDao;
import edu.phones.domain.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

// TODO completar los metodos del dao segun corresponda

@Repository
@Qualifier("userTypeMySQLDao")
public class UserTypeMySQLDao implements UserTypeDao {

    final Connection connect;

    @Autowired
    public UserTypeMySQLDao(Connection connect) {
        this.connect = connect;
    }

    @Override
    public UserType add(UserType type) {
        return null;
    }

    @Override
    public Integer remove(UserType type) {
        return null;
    }

    @Override
    public Integer update(UserType type) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public UserType getById(Integer id) {
        return null;
    }

    @Override
    public List<UserType> getAll() {
        return null;
    }
}
