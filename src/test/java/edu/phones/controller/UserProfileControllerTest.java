package edu.phones.controller;

import edu.phones.domain.UserProfile;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.service.UserProfileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserProfileControllerTest {

    UserProfileController profileController;
    @Mock
    UserProfileService profileService;

    @Before
    public void setUp(){
        initMocks(this);
        profileController = new UserProfileController(profileService);
    }

    @Test
    public void testCreateProfileOk() throws ProfileAlreadyExistException {
        UserProfile toAdd = new UserProfile("Name", "Lastname", 12346789);
        UserProfile added = new UserProfile(1, "Name", "Lastname", 12346789);
        when(profileService.createProfile(toAdd)).thenReturn(added);

        UserProfile profile = profileController.createProfile(toAdd);

        assertEquals(added.getProfileId(), profile.getProfileId());
        verify(profileService, times(1)).createProfile(toAdd);
    }

    @Test
    public void testUpdateProfileOk() throws ProfileNotExistException {
        UserProfile toUpdate = new UserProfile(1, "Name", "Lastname", 12346789);
        UserProfile updated = new UserProfile(1, "Name2", "Lastname2", 12346789);
        when(profileService.updateProfile(toUpdate)).thenReturn(updated);

        UserProfile profile = profileController.updateProfile(toUpdate);

        assertEquals(updated.getProfileId(), profile.getProfileId());
        verify(profileService, times(1)).updateProfile(toUpdate);
    }

    @Test
    public void testGetProfileOk(){
        UserProfile profile = new UserProfile(1, "Name", "Lastname", 12346789);
        when(profileService.getProfile(1)).thenReturn(profile);

        UserProfile getted = profileController.getProfile(1);

        assertEquals(profile.getProfileId(), getted.getProfileId());
        verify(profileService, times(1)).getProfile(1);
    }

}
