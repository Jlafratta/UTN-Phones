package edu.phones.service;

import edu.phones.dao.UserDao;
import edu.phones.domain.User;
import edu.phones.exceptions.UserAlreadyExistsException;
import edu.phones.exceptions.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserDao userDao;

    @Autowired
    public UserService(@Qualifier("userMySQLDao") UserDao userDao) {
        this.userDao = userDao;
    }

    /** METHODS **/
    public User login(String username, String password) throws UserNotExistException {
        User user = userDao.getByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow( () -> new UserNotExistException() );
    }

    /** CRUD **/
    public User createUser(User user) throws UserAlreadyExistsException{
        return userDao.add(user);
    }

    public void removeUser(User user) throws UserNotExistException {
        if (userDao.remove(user) == 0 ){
            throw new UserNotExistException();
        }
    }

    public User updateUser(User user) throws UserNotExistException {
        if(userDao.update(user)>0){
            return user;
        }else {
            throw new UserNotExistException();
        }
    }

    public User getUser(Integer id) {
        return userDao.getById(id);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }
}
