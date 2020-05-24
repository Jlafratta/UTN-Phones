package edu.phones.dao.mysql;

import edu.phones.dao.UserProfileDao;
import edu.phones.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

// TODO completar los metodos del dao segun corresponda

@Repository
@Qualifier("profileMySQLDao")
public class UserProfileMySQLDao implements UserProfileDao {

    final Connection connect;

    @Autowired
    public UserProfileMySQLDao(Connection connect) {
        this.connect = connect;
    }

    /** CRUD **/
    @Override
    public UserProfile add(UserProfile profile) {
        return null;
    }

    @Override
    public Integer update(UserProfile profile) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Integer remove(UserProfile profile) {
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
