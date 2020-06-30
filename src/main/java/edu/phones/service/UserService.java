package edu.phones.service;

import edu.phones.dao.UserDao;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        User user = userDao.getByUsernameAndPassword(username, hashPass(password));
        return Optional.ofNullable(user).orElseThrow(UserNotExistException::new);
    }

    /** CRUD **/
    public User createUser(User user) throws UserAlreadyExistsException {
        user.setPassword(hashPass(user.getPassword()));
        return userDao.add(user);
    }

    public void removeUser(User user) throws UserNotExistException {
        if (userDao.remove(user) == 0 ){
            throw new UserNotExistException();
        }
    }

    public User updateUser(User user) throws UserNotExistException {
        user.setPassword(hashPass(user.getPassword()));
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

    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    private String hashPass(String pass)   {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(pass.getBytes(),0, pass.length());
            String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);
            return (hashedPass.length() < 32) ? "0" + hashedPass : hashedPass;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashed error", e);
        }
    }
}
