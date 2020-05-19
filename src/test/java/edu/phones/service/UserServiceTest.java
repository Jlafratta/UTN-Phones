package edu.phones.service;

import edu.phones.dao.mysql.UserMySQLDao;
import edu.phones.domain.User;
import edu.phones.exceptions.UserAlreadyExistsException;
import edu.phones.exceptions.UserNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    UserService userService;
    @Mock
    UserMySQLDao userDao;

    @Before
    public void setUp(){
        initMocks(this);
        userService = new UserService(userDao);
    }

    /** login tests **/
    @Test
    public void testLoginOk() throws UserNotExistException {
        User logged = new User(1, "username", "password", null, null);
        when(userDao.getByUsername("username", "password")).thenReturn(logged);
        User returned = userService.login("username", "password");

        assertEquals(logged.getUserId(), returned.getUserId());
        assertEquals(logged.getUsername(), returned.getUsername());
        verify(userDao, times(1)).getByUsername("username", "password");
    }

    @Test(expected = UserNotExistException.class)
    public void testLoginUserNotExist() throws UserNotExistException {
        when(userDao.getByUsername("username", "password")).thenReturn(null);
        userService.login("username", "password");
    }

    /** getById test **/
    @Test
    public void testGetByIdOk(){
        User toSearch = new User(1, "username", "password", null, null);
        when(userDao.getById(1)).thenReturn(toSearch);
        userService.getById(1);
        verify(userDao, times(1)).getById(1);
    }

    /** getAll test **/
    @Test
    public void testGetAllOk(){
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "username", "password", null, null));
        when(userDao.getAll()).thenReturn(userList);
        userService.getAll();

        verify(userDao, times(1)).getAll();
    }

    /** addUser test **/
    @Test
    public void testAddUser() throws UserAlreadyExistsException {
        User toAdd = new User( "username", "password", null, null);
        User added = new User(1, "username", "password", null, null);
        when(userDao.add(toAdd)).thenReturn(added);
        User returned = userService.addUser(toAdd);

        assertEquals(toAdd.getUsername(), returned.getUsername());
        verify(userDao, times(1)).add(toAdd);
    }

    /** modifyUser tests **/
    @Test
    public void testModifyUserOk() throws UserNotExistException {
        User toModify = new User(1, "username", "password", null, null);
        when(userDao.update(toModify)).thenReturn(1);
        User modified = userService.modifyUser(toModify);

        assertEquals(toModify.getUserId(), modified.getUserId());
        assertEquals(toModify.getUsername(), modified.getUsername());
        verify(userDao, times(1)).update(toModify);
    }

    @Test(expected = UserNotExistException.class)
    public void testModifyUserNotExists() throws UserNotExistException {
        User toModify = new User(1, "username", "password", null, null);
        when(userDao.update(toModify)).thenReturn(0);
        userService.modifyUser(toModify);
    }

    /** removeUser test **/
    @Test
    public void testRemoveUserOk() throws UserNotExistException {
        User toRemove = new User(1, "username", "password", null, null);
        when(userDao.remove(toRemove)).thenReturn(5);
        userService.removeUser(toRemove);
        verify(userDao, times(1)).remove(toRemove);
    }

    @Test(expected = UserNotExistException.class)
    public void testRemoveUserNotExist() throws UserNotExistException {
        when(userDao.remove((User) any())).thenReturn(0);
        userService.removeUser((User) any());
        verify(userDao, times(1)).remove((User) any());
    }

}