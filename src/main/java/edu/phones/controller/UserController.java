package edu.phones.controller;

import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** METHODS **/
    public User login(String username, String password) throws ValidationException, UserNotExistException {
        if(username != null && password != null){
            return userService.login(username, password);
        }else{
            throw new ValidationException("Username and password cant be empty");
        }
    }

    /** CRUD **/
    public User createUser(User user) throws UserAlreadyExistsException {
        return userService.createUser(user);
    }

    public void removeUser(User user) throws UserNotExistException {
        userService.removeUser(user);
    }

    public User updateUser(User user) throws UserNotExistException {
        return userService.updateUser(user);
    }

    public User getUser(Integer id){
        return userService.getUser(id);
    }

    public List<User> getAll(){
        return userService.getAll();
    }
}
