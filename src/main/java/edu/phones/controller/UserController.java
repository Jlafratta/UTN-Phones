package edu.phones.controller;

import edu.phones.domain.User;
import edu.phones.exceptions.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
}
