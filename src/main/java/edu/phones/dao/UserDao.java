package edu.phones.dao;

import edu.phones.domain.User;

public interface UserDao extends AbstractDao<User>{
    User getByUsername(String username, String password);
}
