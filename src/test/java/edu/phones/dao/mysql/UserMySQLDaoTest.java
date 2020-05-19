package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.UserAlreadyExistsException;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static edu.phones.dao.mysql.MySQLUtils.*;

public class UserMySQLDaoTest {

    UserMySQLDao userDao;
    @Mock
    Connection connect;
    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    @Before
    public void setUp(){
        initMocks(this);
        userDao = new UserMySQLDao(connect);
    }

    /** getByUsername tests **/
    @Test
    public void testGetByUsernameOk() throws SQLException {
        // mock del ps
        when(connect.prepareStatement(GET_BY_USERNAME_USER_QUERY)).thenReturn(ps);
        doNothing().when(ps).setString(1, "username");
        doNothing().when(ps).setString(2, "password");

        // mock del rs
        when(ps.executeQuery()).thenReturn(rs);
        // simulo el if como valido
        when(rs.next()).thenReturn(true);
        // simulo la devolucion de datos de la query (los valores no tienen importancia)
        when(rs.getInt("id_user")).thenReturn(3);
        when(rs.getInt("id_profile")).thenReturn(5);
        when(rs.getInt("id_city")).thenReturn(7);
        // a todos los string que traiga la query les asigno 'StringValue'
        when(rs.getString(any())).thenReturn("StringValue");

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        // invoco el metodo a testear
        User user = userDao.getByUsername("username", "password");

        // Asserts para comparar lo retornado (los valores tienen que ser los mismos que simulo devolver en la query para que este ok)
        assertEquals(Integer.valueOf(3), user.getUserId());
        assertEquals(Integer.valueOf(5), user.getUserProfile().getProfileId());
        assertEquals(Integer.valueOf(7), user.getCity().getCityId());

        // verifico que el ps haga los setString 2 veces,
        // para los campos correspondientes (username y password son los unicos 2 campos String)
        verify(ps, times(2)).setString(anyInt(), anyString());

    }

    @Test(expected = RuntimeException.class)
    public void testGetByUserNameSQLError() throws SQLException {
        when(connect.prepareStatement(GET_BY_USERNAME_USER_QUERY)).thenThrow(new SQLException());
        User user = userDao.getByUsername("username", "password");
    }

    @Test
    public void testGetByUsernameNotFound() throws SQLException {
        when(connect.prepareStatement(GET_BY_USERNAME_USER_QUERY)).thenReturn(ps);

        doNothing().when(ps).setString(1, "username");
        doNothing().when(ps).setString(2, "password");

        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        User user = userDao.getByUsername("username", "password");
        // el usuario retornado es = a null
        assertNull(user);
    }

    /** add tests **/
    @Test
    public void testAddOk() throws SQLException, UserAlreadyExistsException {
        User toAdd =  new User("username", "password", new UserProfile(1,"name", "lastname", 1), new City(3,"1", "cityName", new Province(1,"provinceName")));
        when(connect.prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)). thenReturn(ps);
        doNothing().when(ps).setString(1,toAdd.getUsername());
        doNothing().when(ps).setString(2,toAdd.getPassword());
        doNothing().when(ps).setInt(3, toAdd.getUserProfile().getProfileId());
        doNothing().when(ps).setInt(4, toAdd.getCity().getCityId());
        when(ps.execute()).thenReturn(true);

        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        when(rs.getInt(1)).thenReturn(1);
        toAdd.setUserId(1);

        User added = userDao.add(toAdd);

        assertEquals(toAdd.getUsername(), added.getUsername());
        verify(ps, times(1)).getGeneratedKeys();

    }

    @Test(expected = RuntimeException.class)
    public void testAddError() throws UserAlreadyExistsException, SQLException {
        User toAdd = new User( "username", "password", null, null);
        when(connect.prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenThrow(new SQLException());
        User added = userDao.add(toAdd);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testAddAlreadyExist() throws UserAlreadyExistsException, SQLException {
        User toAdd =  new User("username", "password", new UserProfile(1,"name", "lastname", 1), new City(3,"1", "cityName", new Province(1,"provinceName")));

        when(connect.prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)). thenReturn(ps);
        doNothing().when(ps).setString(1,toAdd.getUsername());
        doNothing().when(ps).setString(2,toAdd.getPassword());
        doNothing().when(ps).setInt(3, toAdd.getUserProfile().getProfileId());
        doNothing().when(ps).setInt(4, toAdd.getCity().getCityId());
        when(ps.execute()).thenThrow(new SQLException("User already exist", "SQLSTATE[23000]: Integrity constraint violation: 1062 Duplicate", MysqlErrorNumbers.ER_DUP_ENTRY, new UserAlreadyExistsException()));

        /**
         when(e.getErrorCode()).thenReturn(MysqlErrorNumbers.ER_DUP_ENTRY);
         when(connect.prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenThrow(e);
         when(e.getErrorCode()).thenReturn(MysqlErrorNumbers.ER_DUP_ENTRY);
        **/

        User added = userDao.add(toAdd);
    }

    /** update tests **/
    @Test
    public void testUpdateOk() throws SQLException {
        User toUpdate =  new User(1,"username", "password", new UserProfile(1,"name", "lastname", 1), new City(3,"1", "cityName", new Province(1,"provinceName")));
        when(connect.prepareStatement(UPDATE_USER_QUERY)).thenReturn(ps);
        doNothing().when(ps).setString(1, toUpdate.getUsername());
        doNothing().when(ps).setString(2, toUpdate.getPassword());
        doNothing().when(ps).setInt(3, toUpdate.getUserProfile().getProfileId());
        doNothing().when(ps).setInt(4, toUpdate.getCity().getCityId());
        doNothing().when(ps).setInt(5, toUpdate.getUserId());

        when(ps.executeUpdate()).thenReturn(2);

        userDao.update(toUpdate);

        verify(ps, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateError() throws SQLException {
        User toUpdate =  new User(1,"username", "password", new UserProfile(1,"name", "lastname", 1), new City(3,"1", "cityName", new Province(1,"provinceName")));
        when(connect.prepareStatement(UPDATE_USER_QUERY)).thenReturn(ps);
        doNothing().when(ps).setString(1, toUpdate.getUsername());
        doNothing().when(ps).setString(2, toUpdate.getPassword());
        doNothing().when(ps).setInt(3, toUpdate.getUserProfile().getProfileId());
        doNothing().when(ps).setInt(4, toUpdate.getCity().getCityId());
        doNothing().when(ps).setInt(5, toUpdate.getUserId());

        when(ps.executeUpdate()).thenThrow(new SQLException());

        userDao.update(toUpdate);
    }

    /** remove tests **/
    @Test
    public void testRemoveOk() throws SQLException {
        Integer id = 1;
        when(connect.prepareStatement(DELETE_USER_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, id);

        when(ps.executeUpdate()).thenReturn(1);

        userDao.remove(id);

        verify(ps, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void testRemoveError() throws SQLException {
        Integer id = 1;
        when(connect.prepareStatement(DELETE_USER_QUERY)).thenThrow(new SQLException());
        userDao.remove(id);
    }

    /** getById tests **/
    @Test
    public void testGetByIdOk() throws SQLException {
        Integer id = 1;
        when(connect.prepareStatement(GET_BY_ID_USER_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, id);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);

        when(rs.getInt("id_user")).thenReturn(id);
        when(rs.getInt("id_profile")).thenReturn(5);
        when(rs.getInt("id_city")).thenReturn(7);
        // a todos los string que traiga la query les asigno 'StringValue'
        when(rs.getString(any())).thenReturn("StringValue");

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        User user = userDao.getById(id);

        assertEquals(id, user.getUserId());
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdError() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_USER_QUERY)).thenThrow(new SQLException());
        userDao.getById(1);
    }

    /** getAll tests **/
    @Test
    public void testGetAllOk() throws SQLException {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "username", "password", null, null));

        when(connect.prepareStatement(BASE_USER_QUERY)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt("id_client")).thenReturn(1);
        when(rs.getString("username")).thenReturn("username");
        when(rs.getString("password")).thenReturn("password");


        userDao.getAll();


    }

}
