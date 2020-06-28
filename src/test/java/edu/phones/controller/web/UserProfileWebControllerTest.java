package edu.phones.controller.web;

import edu.phones.controller.UserProfileController;
import edu.phones.domain.UserProfile;
import edu.phones.dto.ProfileDto;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserProfileWebControllerTest {

    @Mock
    UserProfileController profileController;
    @Mock
    SessionManager sessionManager;

    UserProfileWebController profileWebController;

    @Before
    public void setUp(){
        initMocks(this);
        this.profileWebController = new UserProfileWebController(profileController, sessionManager);
    }

    @Test
    public void testGetProfileOk(){
        UserProfile profile = new UserProfile(1, "name", "username", 1);
        Integer id = 1;
        String sessionToken = "StringValue";

        when(profileController.getProfile(id)).thenReturn(profile);

        ResponseEntity<UserProfile> response = profileWebController.getProfile(id, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile, response.getBody());
        verify(profileController, times(1)).getProfile(id);
    }

    @Test
    public void testGetProfileNotFound(){
        Integer id = 1;
        String sessionToken = "StringValue";

        when(profileController.getProfile(id)).thenReturn(null);

        ResponseEntity<UserProfile> response = profileWebController.getProfile(id, sessionToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(profileController, times(1)).getProfile(id);
    }

    @Test
    public void testUpdateProfileOk() throws ProfileNotExistException {
        ProfileDto dto = new ProfileDto();
        dto.setProfileId(1);
        dto.setName("name");
        dto.setLastname("lastname");
        dto.setDni(1);
        String sessionToken = "StringValue";
        UserProfile profile = new UserProfile(dto.getProfileId(), dto.getName(), dto.getLastname(), dto.getDni());

        when(profileController.updateProfile(profile)).thenReturn(new UserProfile(1, "name", "lastname", 1));

        ResponseEntity<UserProfile> response = profileWebController.updateProfile(dto, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(profile, response.getBody());
        //verify(profileController, times(1)).updateProfile(profile);
    }
}
