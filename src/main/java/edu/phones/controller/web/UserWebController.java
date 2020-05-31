package edu.phones.controller.web;

import edu.phones.controller.UserController;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserWebController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public UserWebController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }


    
    @PostMapping
    public ResponseEntity newUser(@RequestHeader("Authorization") String sessionToken, @RequestBody User user) throws UserAlreadyExistsException {

        User newUser = userController.createUser(user);
        return ResponseEntity.created(getLocation(newUser)).build();
    }

    private URI getLocation(User newUser) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getUserId())
                .toUri();
    }


}
