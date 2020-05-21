package edu.phones.dao;

import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;

import java.util.List;

public interface UserDao extends AbstractDao<User>{

    /**  METHODS **/
    User getByUsername(String username, String password);

    /** CRUD **/
    User add(User user) throws UserAlreadyExistsException;
    Integer remove(User user);
    Integer update(User user);
    User getById(Integer id);
    List<User> getAll();
}
