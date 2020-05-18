package edu.phones.controller;

import edu.phones.domain.User;
import edu.phones.exceptions.UserAlreadyExistsException;
import edu.phones.exceptions.UserNotExistException;
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

    public User login(String username, String password) throws ValidationException, UserNotExistException {
        if(username != null && password != null){
            return userService.login(username, password);
        }else{
            throw new ValidationException("Username and password cant be empty");
        }
    }

    public User getById(Integer id){
        return userService.getById(id);
    }

    public List<User> getAll(){
        return userService.getAll();
    }

    public User addUser(User user) throws UserAlreadyExistsException {
        return userService.addUser(user);
    }

    public User modifyUser(User user) throws UserNotExistException {
        return userService.modifyUser(user);
    }

    public void removeUser(User user) throws UserNotExistException {
        userService.removeUser(user);
    }

}
