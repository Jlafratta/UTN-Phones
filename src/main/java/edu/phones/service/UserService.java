package edu.phones.service;

import edu.phones.dao.UserDao;
import edu.phones.domain.User;
import edu.phones.exceptions.UserNotExistException;

import java.util.Optional;

public class UserService {

    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String username, String password) throws UserNotExistException {
        User user = userDao.getByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow( () -> new UserNotExistException() );
    }
}
