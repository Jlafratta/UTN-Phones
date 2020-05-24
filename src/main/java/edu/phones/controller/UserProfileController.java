package edu.phones.controller;

import edu.phones.domain.UserProfile;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserProfileController {

    ProfileService profileService;

    @Autowired
    public UserProfileController(ProfileService userProfileService) {
        this.profileService = userProfileService;
    }

    public UserProfile createProfile(UserProfile newProfile) {
        return profileService.createProfile(newProfile);
    }

    public UserProfile updateProfile(UserProfile toModify) throws ProfileNotExistException {
        return profileService.updateProfile(toModify);
    }

    public UserProfile getProfile(Integer id){
        return profileService.getProfile(id);
    }

}
