package edu.phones.dao.mysql;

import edu.phones.dao.ProfileDao;
import edu.phones.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

// TODO completar los metodos del dao segun corresponda

@Repository
public class ProfileMySQLDao implements ProfileDao {

    Connection connect;

    @Autowired
    public ProfileMySQLDao(Connection connect) {
        this.connect = connect;
    }

    @Override
    public UserProfile add(UserProfile value) {
        return null;
    }

    @Override
    public Integer update(UserProfile value) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Integer remove(UserProfile value) {
        return null;
    }

    @Override
    public UserProfile getById(Integer id) {
        return null;
    }

    @Override
    public List<UserProfile> getAll() {
        return null;
    }
}
