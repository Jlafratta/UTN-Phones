package edu.phones.controller;

import edu.phones.domain.UserProfile;
import edu.phones.exceptions.ProfileNotExistException;
import edu.phones.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {

    ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService userProfileService) {
        this.profileService = userProfileService;
    }

    public UserProfile newProfile(UserProfile newProfile) {
        return profileService.newProfile(newProfile);
    }

    public UserProfile getById(Integer id){
        return profileService.getById(id);
    }

    public UserProfile modifyProfile(UserProfile toModify) throws ProfileNotExistException {
        return profileService.modifyProfile(toModify);
    }


}
