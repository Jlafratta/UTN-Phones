package edu.phones.controller;

import edu.phones.domain.User;
import edu.phones.exceptions.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    UserController userController;
    @Mock
    UserService userService;

    @Before
    public void setUp(){
        initMocks(this);    // Inicializa todos los @Mock que encuentre en esta clase
        userController = new UserController(userService);   // Inicializo el controller con el service mockeado
    }

    @Test   // Las excepciones van en el encabezado debido a que son propias de algunos metodos, no se espera que sean lanzadas en el test
    public void testLoginOk() throws UserNotExistException, ValidationException {
        // userProfile y city van null debido a que no es lo que se esta testeando
        User loggedUser = new User(1, "username", "password", null, null);

        when(userService.login("username", "password")).thenReturn(loggedUser);
        User returnedUser = userController.login("username", "password");

        assertEquals(loggedUser.getUserId(), returnedUser.getUserId());
        assertEquals(loggedUser.getUsername(), returnedUser.getUsername());

        //Verifico que la llamada se haga 1 sola vez
        verify(userService, times(1)).login("username", "password");
    }

    // Declaro la excepcion que espero que sea lanzada en el test
    @Test(expected = UserNotExistException.class)
    public void testLoginUserNotExist() throws UserNotExistException, ValidationException {

        when(userService.login("username", "password")).thenThrow(new UserNotExistException());
        userController.login("username", "password");
    }

    @Test(expected = ValidationException.class)
    public void testLoginInvalidData() throws ValidationException, UserNotExistException {
        userController.login(null, "password");
    }
}
