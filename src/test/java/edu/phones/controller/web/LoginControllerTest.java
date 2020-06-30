package edu.phones.controller.web;

import edu.phones.controller.UserController;
import edu.phones.domain.User;
import edu.phones.dto.LoginRequestDto;
import edu.phones.exceptions.InvalidLoginException;
import edu.phones.exceptions.ValidationException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginControllerTest {

    @Mock
    UserController userController;
    @Mock
    SessionManager sessionManager;

    LoginController loginController;

    @Before
    public void setUp(){
        initMocks(this);
        this.loginController = new LoginController(userController, sessionManager);
    }

    @Test
    public void testLoginOk() throws ValidationException, UserNotExistException, InvalidLoginException {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("username");
        dto.setPassword("password");
        User user = new User(1, "username", "password", null, null, null);
        String token = "StringValue";

        when(userController.login(dto.getUsername(), dto.getPassword())).thenReturn(user);
        when(sessionManager.createSession(user)).thenReturn(token);

        ResponseEntity response = loginController.login(dto);

        assertNotNull(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getHeaders().getFirst("Authorization"));
        verify(userController, times(1)).login(dto.getUsername(), dto.getPassword());
        verify(sessionManager, times(1)).createSession(user);
    }

    @Test(expected = InvalidLoginException.class)
    public void testLoginInvalidData() throws ValidationException, UserNotExistException, InvalidLoginException {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("username");
        dto.setPassword("password");

        when(userController.login(dto.getUsername(), dto.getPassword())).thenThrow(new UserNotExistException());
        ResponseEntity response = loginController.login(dto);

        verify(userController, times(1)).login(dto.getUsername(), dto.getPassword());
    }

    @Test
    public void testLogoutOk() {
        String token = "StringValue";
        doNothing().when(sessionManager).removeSession(token);
        loginController.logout(token);
    }

}
