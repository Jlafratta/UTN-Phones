package edu.phones.controller;

import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

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

    /** login tests **/
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

    /** getById test **/
    @Test
    public void testGetByIdOk(){
        User userToSearch = new User(1, "username", "password", null, null);
        when(userService.getUser(1)).thenReturn(userToSearch);
        User userFounded = userController.getUser(1);

        assertEquals(userToSearch.getUserId(), userFounded.getUserId());
        assertEquals(userToSearch.getUsername(), userFounded.getUsername());

        verify(userService, times(1)).getUser(1);
    }

    /** getAll test **/
    @Test
    public void testGetAllOk(){
        List<User> users = new ArrayList<>();
        users.add(new User(1, "username", "password", null, null));
        when(userService.getAll()).thenReturn(users);
        userController.getAll();
        verify(userService, times(1)).getAll();
    }

    /** addUser test **/
    @Test
    public void testCreateUserOk() throws UserAlreadyExistsException {
        User userToAdd = new User("username", "password", null, null);
        User userAdded = new User(1,"username", "password", null, null);
        when(userService.createUser(userToAdd)).thenReturn(userAdded);
        userController.createUser(userToAdd);

        assertEquals(userToAdd.getUsername(), userAdded.getUsername());
        verify(userService, times(1)).createUser(userToAdd);
    }

    /** modifyUser test **/
    @Test
    public void testUpdateUserOk() throws UserNotExistException {
        User toModify = new User(1,"username", "password", null, null);
        User modified = new User(1,"username2", "password2", null, null);
        when(userService.updateUser(toModify)).thenReturn(modified);
        userController.updateUser(toModify);

        assertEquals(toModify.getUserId(), modified.getUserId());
        verify(userService, times(1)).updateUser(toModify);
    }

    /** removeUser test **/
    @Test
    public void testRemoveUserOk() throws UserNotExistException {
        User toRemove = new User(1,"username", "password", null, null);
        doNothing().when(userService).removeUser(toRemove);
        userController.removeUser(toRemove);

        verify(userService, times(1)).removeUser(toRemove);
    }

}
