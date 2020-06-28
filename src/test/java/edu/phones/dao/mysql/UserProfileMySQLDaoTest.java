package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.phones.dao.mysql.MySQLUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserProfileMySQLDaoTest {

    @Mock
    Connection connect;
    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    UserProfileMySQLDao profileDao;

    @Before
    public void setUp(){
        initMocks(this);
        this.profileDao = new UserProfileMySQLDao(connect);
    }

    @Test
    public void testAddOk() throws SQLException, ProfileAlreadyExistException {
        when(connect.prepareStatement(INSERT_PROFILE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        doNothing().when(ps).setString(1, "name");
        doNothing().when(ps).setString(2, "lastname");
        doNothing().when(ps).setInt(3, 123);
        when(ps.execute()).thenReturn(true);

        when(ps.getGeneratedKeys()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        UserProfile profile = profileDao.add(new UserProfile("name", "lastname", 123));

        assertNotNull(profile);
        assertEquals(Integer.valueOf(1), profile.getProfileId());
        verify(ps, times(1)).execute();
        verify(rs, times(1)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testAddError() throws SQLException, ProfileAlreadyExistException {
        when(connect.prepareStatement(INSERT_PROFILE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenThrow(new SQLException());
        UserProfile profile = profileDao.add(new UserProfile("name", "lastname", 123));
    }

    @Test(expected = ProfileAlreadyExistException.class)
    public void testAddAlreadyExists() throws SQLException, ProfileAlreadyExistException {
        when(connect.prepareStatement(INSERT_PROFILE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        doNothing().when(ps).setString(1, "name");
        doNothing().when(ps).setString(2, "lastname");
        doNothing().when(ps).setInt(3, 123);
        when(ps.execute()).thenThrow(new SQLException("User Profile already exist", "SQLSTATE[23000]: Integrity constraint violation: 1062 Duplicate",MysqlErrorNumbers.ER_DUP_ENTRY, new ProfileAlreadyExistException()));

        when(ps.getGeneratedKeys()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        UserProfile profile = profileDao.add(new UserProfile("name", "lastname", 123));

        assertNotNull(profile);
        assertEquals(Integer.valueOf(1), profile.getProfileId());
        verify(ps, times(1)).execute();
        verify(rs, times(1)).next();
    }

    @Test
    public void testUpdateOk() throws SQLException {
        when(connect.prepareStatement(UPDATE_PROFILE_QUERY)).thenReturn(ps);
        doNothing().when(ps).setString(1, "name");
        doNothing().when(ps).setString(2, "lastname");
        doNothing().when(ps).setInt(3, 123);
        doNothing().when(ps).setInt(4, 1);

        when(ps.executeUpdate()).thenReturn(1);

        Integer rows = profileDao.update(new UserProfile(1,"name", "lastname", 123));

        assertEquals(Integer.valueOf(1), rows);
        verify(ps, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateError() throws SQLException {
        when(connect.prepareStatement(UPDATE_PROFILE_QUERY)).thenThrow(new SQLException());
        Integer rows = profileDao.update(new UserProfile(1,"name", "lastname", 123));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveByIdUnsupported(){
        profileDao.remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveUnsupported(){
        profileDao.remove(new UserProfile(1,"name", "lastname", 123));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAllUnsupported(){
        profileDao.getAll();
    }

    @Test
    public void testGetByIdOk() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_PROFILE_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt("id_profile")).thenReturn(1);
        when(rs.getString("name")).thenReturn("name");
        when(rs.getString("lastname")).thenReturn("lastname");
        when(rs.getInt("dni")).thenReturn(123);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        UserProfile profile = profileDao.getById(1);

        assertNotNull(profile);
        assertEquals(Integer.valueOf(1), profile.getProfileId());
        verify(rs, times(1)).next();
        verify(ps, times(1)).executeQuery();
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdError() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_PROFILE_QUERY)).thenThrow(new SQLException());
        UserProfile profile = profileDao.getById(1);
    }


}
