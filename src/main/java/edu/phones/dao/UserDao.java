package edu.phones.dao;

import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;

import java.util.List;

public interface UserDao extends AbstractDao<User>{

    /**  METHODS **/
    User getByUsernameAndPassword(String username, String password);

    /** CRUD **/
    User add(User user) throws UserAlreadyExistsException;

    Integer remove(User user);

    Integer update(User user);

    User getById(Integer id);

    List<User> getAll();

    User getByUsername(String username);
}
