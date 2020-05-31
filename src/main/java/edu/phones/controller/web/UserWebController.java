package edu.phones.controller.web;

import edu.phones.controller.CityController;
import edu.phones.controller.UserController;
import edu.phones.controller.UserProfileController;
import edu.phones.domain.City;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.dto.AddUserDto;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.CityNotExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserWebController {

    UserController userController;
    UserProfileController profileController;
    CityController cityController;
    SessionManager sessionManager;

    @Autowired
    public UserWebController(UserController userController, UserProfileController profileController,CityController cityController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
        this.profileController = profileController;
        this.cityController = cityController;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer id){

        User user = userController.getUser(id);

        ResponseEntity<User> responseEntity;

        if(user != null){
            responseEntity = ResponseEntity.ok(user);
        }else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestHeader("Authorization") String sessionToken){
        List<User> users = new ArrayList<>();
        users = userController.getAll();
        return (users.size() > 0) ? ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity addUser(@RequestHeader("Authorization") String sessionToken, @RequestBody AddUserDto userDto) throws UserAlreadyExistsException, ProfileNotExistException, CityNotExistException {

        User toAdd;
        UserProfile profile = profileController.getProfile(userDto.getProfileId());
        City city = cityController.getCity(userDto.getCityId());

        if(profile != null && city != null){
            toAdd = new User(userDto.getUsername(), userDto.getPassword(), profile, city);
            toAdd = userController.createUser(toAdd);
        }else {
            if(profile == null){
                throw new ProfileNotExistException();
            }else {
                throw new CityNotExistException();
            }
        }

        return ResponseEntity.created(getLocation(toAdd)).build();
    }

    private URI getLocation(User newUser) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getUserId())
                .toUri();
    }


}
