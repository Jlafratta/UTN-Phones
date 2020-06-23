package edu.phones.controller.web;

import edu.phones.controller.UserProfileController;
import edu.phones.domain.UserProfile;
import edu.phones.dto.AddProfileDto;
import edu.phones.dto.ProfileDto;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/backoffice/profile")
public class UserProfileWebController {

    UserProfileController profileController;
    SessionManager sessionManager;

    public UserProfileWebController(UserProfileController profileController, SessionManager sessionManager) {
        this.profileController = profileController;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Integer id, @RequestHeader("Authorization") String sessionToken){

        UserProfile profile = profileController.getProfile(id);

        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<UserProfile> addProfile(@RequestBody AddProfileDto profileDto, @RequestHeader("Authorization") String sessionToken) throws ProfileAlreadyExistException {

        UserProfile profile = new UserProfile(profileDto.getName(), profileDto.getLastname(), profileDto.getDni());
        profile = profileController.createProfile(profile);

        return ResponseEntity.created(getLocation(profile)).build();
    }

    @PutMapping
    public ResponseEntity<UserProfile> updateProfile(@RequestBody ProfileDto profileDto, @RequestHeader("Authorization") String sessionToken) throws ProfileNotExistException {

        UserProfile profile = new UserProfile(profileDto.getProfileId(), profileDto.getName(), profileDto.getLastname(), profileDto.getDni());
        profile = profileController.updateProfile(profile);

        return ResponseEntity.ok(profile);
    }

    private URI getLocation(UserProfile profile) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(profile.getProfileId())
                .toUri();
    }
}
