package edu.phones.service;

import edu.phones.dao.UserDao;
import edu.phones.domain.User;
import edu.phones.exceptions.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserDao userDao;

    @Autowired
    public UserService(@Qualifier("userMySQLDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String username, String password) throws UserNotExistException {
        User user = userDao.getByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow( () -> new UserNotExistException() );
    }
}
