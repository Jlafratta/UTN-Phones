package edu.phones.controller.web;

import edu.phones.controller.CityController;
import edu.phones.controller.UserController;
import edu.phones.controller.UserProfileController;
import edu.phones.domain.City;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.dto.AddUserDto;
import edu.phones.dto.UserDto;
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
@RequestMapping("/backoffice/users")
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

    /* 2) Manejo de clientes. */

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id, @RequestHeader("Authorization") String sessionToken){

        User user = userController.getUser(id);

        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestHeader("Authorization") String sessionToken){
        List<User> users = new ArrayList<>();
        users = userController.getAll();
        return (users.size() > 0) ? ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody AddUserDto userDto, @RequestHeader("Authorization") String sessionToken) throws UserAlreadyExistsException, ProfileNotExistException, CityNotExistException {

        User toAdd;
        UserProfile profile = profileController.getProfile(userDto.getProfileId());
        City city = cityController.getCity(userDto.getCityId());

        Optional.ofNullable(city).orElseThrow(CityNotExistException::new);
        Optional.ofNullable(profile).orElseThrow(ProfileNotExistException::new);

        toAdd = new User(userDto.getUsername(), userDto.getPassword(), userDto.isEmployee(), profile, city);
        toAdd = userController.createUser(toAdd);

        return ResponseEntity.created(getLocation(toAdd)).build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ProfileNotExistException, CityNotExistException {

        UserProfile profile = userDto.getProfileId() != null ? profileController.getProfile(userDto.getProfileId()) : null;
        City city = userDto.getCityId() != null ? cityController.getCity(userDto.getCityId()) : null;

        Optional.ofNullable(city).orElseThrow(CityNotExistException::new);
        Optional.ofNullable(profile).orElseThrow(ProfileNotExistException::new);

        User toUpdate = userController.updateUser(new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.isEmployee(), profile, city));

        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping
    public ResponseEntity<User> removeUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ProfileNotExistException, CityNotExistException {

        User toRemove;
        UserProfile profile = profileController.getProfile(userDto.getProfileId());
        City city = cityController.getCity(userDto.getCityId());

        Optional.ofNullable(city).orElseThrow(CityNotExistException::new);
        Optional.ofNullable(profile).orElseThrow(ProfileNotExistException::new);

        toRemove = new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.isEmployee(), profile, city);
        userController.removeUser(toRemove);

        return ResponseEntity.ok(toRemove);
    }

    private URI getLocation(User newUser) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getUserId())
                .toUri();
    }


}
