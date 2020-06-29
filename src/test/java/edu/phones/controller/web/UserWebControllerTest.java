package edu.phones.controller.web;

import edu.phones.controller.CityController;
import edu.phones.controller.UserController;
import edu.phones.controller.UserProfileController;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.dto.UserDto;
import edu.phones.exceptions.notExist.CityNotExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserWebControllerTest {

    @Mock
    UserController userController;
    @Mock
    UserProfileController profileController;
    @Mock
    CityController cityController;
    @Mock
    SessionManager sessionManager;

    UserWebController userWebController;

    @Before
    public void setUp(){
        initMocks(this);
        this.userWebController = new UserWebController(userController, profileController, cityController, sessionManager);
    }

    @Test
    public void testGetUserOk(){
        User user = new User(1, "username", "password", null, null, null);
        Integer id = 1;
        String sessionToken = "StringValue";
        when(userController.getUser(id)).thenReturn(user);
        ResponseEntity<User> response = userWebController.getUser(id, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userController, times(1)).getUser(id);
    }

    @Test
    public void testGetUserNotFound(){
        Integer id = 1;
        String sessionToken = "StringValue";
        when(userController.getUser(id)).thenReturn(null);
        ResponseEntity<User> response = userWebController.getUser(id, sessionToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userController, times(1)).getUser(id);
    }

    @Test
    public void testGetAllOk(){
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "username", "password", null, null, null));
        String sessionToken = "StringValue";

        when(userController.getAll()).thenReturn(userList);

        ResponseEntity<List<User>> response = userWebController.getUsers(sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
        verify(userController, times(1)).getAll();
    }

    @Test
    public void testGetAllNoContent(){
        List<User> userList = new ArrayList<>();
        String sessionToken = "StringValue";

        when(userController.getAll()).thenReturn(userList);

        ResponseEntity<List<User>> response = userWebController.getUsers(sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userController, times(1)).getAll();
    }

    @Test
    public void  testUpdateUserOk() throws UserNotExistException, ProfileNotExistException, CityNotExistException {
        UserProfile profile = new UserProfile(1, "name", "lastname", 123);
        City city = new City(1, "prefix", "name", new Province(1, "name"));
        UserDto dto = new UserDto();
        dto.setId(1);
        dto.setUsername("username");
        dto.setPassword("password");
        dto.setEmployee(true);
        dto.setProfileId(1);
        dto.setCityId(1);
        String sessionToken = "StringValue";

        when(profileController.getProfile(dto.getProfileId())).thenReturn(profile);
        when(cityController.getCity(dto.getCityId())).thenReturn(city);

        User user = new User(dto.getId(),dto.getUsername(),dto.getPassword(), dto.isEmployee(), profile, city);
        when(userController.updateUser(user)).thenReturn(user);

        ResponseEntity<User> response = userWebController.updateUser(dto, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(user, response.getBody());
        verify(profileController, times(1)).getProfile(dto.getProfileId());
        verify(cityController, times(1)).getCity(dto.getCityId());
    }

    @Test
    public void removeUserOk() throws UserNotExistException, ProfileNotExistException, CityNotExistException {
        UserDto dto = new UserDto();
        UserProfile profile = new UserProfile(1, "name", "lastname", 123);
        City city = new City(1, "prefix", "name", null);
        dto.setId(1);
        dto.setUsername("username");
        dto.setPassword("password");
        dto.setEmployee(true);
        dto.setProfileId(1);
        dto.setCityId(1);
        String sessionToken = "StringValue";

        when(profileController.getProfile(dto.getProfileId())).thenReturn(profile);
        when(cityController.getCity(dto.getCityId())).thenReturn(city);

        User user = new User(dto.getId(),dto.getUsername(),dto.getPassword(), dto.isEmployee(), profile, city);
        doNothing().when(userController).removeUser(user);
        ResponseEntity<User> response = userWebController.removeUser(dto, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
       //assertEquals(user, response.getBody());
        verify(profileController, times(1)).getProfile(dto.getProfileId());
        verify(cityController, times(1)).getCity(dto.getCityId());
        //verify(userController, times(1)).removeUser(user);
    }
}
