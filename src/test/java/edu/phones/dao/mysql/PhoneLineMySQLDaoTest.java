package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.UserDao;
import edu.phones.dao.UserTypeDao;
import edu.phones.domain.*;
import edu.phones.exceptions.alreadyExist.PhoneLineAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineMySQLDaoTest {

    @Mock
    Connection connect;
    @Mock
    UserDao userDao;
    @Mock
    UserTypeDao typeDao;

    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    PhoneLineMySQLDao lineDao;

    @Before
    public void setUp(){
        initMocks(this);
        this.lineDao = new PhoneLineMySQLDao(connect, userDao, typeDao);
    }

    @Test
    public void testGetTopTenNotFound() throws SQLException {
        when(connect.prepareStatement(GET_TOP_TEN_USER_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        List<PhoneLine> topTen = lineDao.getTopTen(new User(1, "username", "password", false, null, null));

        assertTrue(topTen.isEmpty());
        verify(ps, times(1)).executeQuery();
        verify(rs, times(1)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testGetTopTenError() throws SQLException {
        when(connect.prepareStatement(GET_TOP_TEN_USER_QUERY)).thenThrow(new SQLException());
        List<PhoneLine> topTen = lineDao.getTopTen(new User(1, "username", "password", false, null, null));
    }

    @Test
    public void testAddCallOk() throws SQLException, PhoneLineAlreadyExistsException {
        when(connect.prepareStatement(INSERT_PLINE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        doNothing().when(ps).setString(1, "2231111111");
        doNothing().when(ps).setBoolean(2, false);
        doNothing().when(ps).setInt(3, 1);
        doNothing().when(ps).setInt(4, 1);
        when(ps.execute()).thenReturn(true);
        when(ps.getGeneratedKeys()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        PhoneLine line = lineDao.add(new PhoneLine("2231111111", false,
                new User(1, "username", "password", null, null, null),
                new UserType(1, "FIJO")));

        assertEquals(Integer.valueOf(1), line.getpLineId());
        verify(ps, times(1)).getGeneratedKeys();
        verify(rs, times(1)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testAddCallError() throws SQLException, PhoneLineAlreadyExistsException {
        when(connect.prepareStatement(INSERT_PLINE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenThrow(new SQLException());
        PhoneLine line = lineDao.add(new PhoneLine("2231111111", false,
                new User(1, "username", "password", null, null, null),
                new UserType(1, "FIJO")));
    }

    @Test(expected = PhoneLineAlreadyExistsException.class)
    public void testAddCallAlreadyExists() throws SQLException, PhoneLineAlreadyExistsException {
        when(connect.prepareStatement(INSERT_PLINE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        doNothing().when(ps).setString(1, "2231111111");
        doNothing().when(ps).setBoolean(2, false);
        doNothing().when(ps).setInt(3, 1);
        doNothing().when(ps).setInt(4, 1);
        when(ps.execute()).thenThrow(new SQLException("Phone Line already exist", "SQLSTATE[23000]: Integrity constraint violation: 1062 Duplicate", MysqlErrorNumbers.ER_DUP_ENTRY, new PhoneLineAlreadyExistsException()));

        PhoneLine line = lineDao.add(new PhoneLine("2231111111", false,
                new User(1, "username", "password", null, null, null),
                new UserType(1, "FIJO")));
    }

    @Test
    public void testUpdateOk() throws SQLException {
        when(connect.prepareStatement(UPDATE_PLINE_QUERY)).thenReturn(ps);
        doNothing().when(ps).setString(1, "2231111111");
        doNothing().when(ps).setBoolean(2, true);
        doNothing().when(ps).setInt(3, 1);
        doNothing().when(ps).setInt(4, 1);
        doNothing().when(ps).setInt(5, 1);

        when(ps.executeUpdate()).thenReturn(3);
        doNothing().when(ps).close();

        Integer rows = lineDao.update(new PhoneLine(1,"2231111111", false,
                new User(1, "username", "password", null, null, null),
                new UserType(1, "FIJO")));

        assertEquals(Integer.valueOf(3), rows);
        verify(ps, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateError() throws SQLException {
        when(connect.prepareStatement(UPDATE_PLINE_QUERY)).thenThrow(new SQLException());
        Integer rows = lineDao.update(new PhoneLine("2231111111", false,
                new User(1, "username", "password", null, null, null),
                new UserType(1, "FIJO")));
    }

    @Test
    public void testRemoveByIdOk() throws SQLException {
        when(connect.prepareStatement(DELETE_PLINE_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeUpdate()).thenReturn(1);
        doNothing().when(ps).close();
        Integer rows = lineDao.remove(1);

        assertEquals(Integer.valueOf(1), rows);
        verify(ps, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void testRemoveByIdError() throws SQLException {
        when(connect.prepareStatement(DELETE_PLINE_QUERY)).thenThrow(new SQLException());
        Integer rows = lineDao.remove(1);
    }

    @Test
    public void testGetByIdOk() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_PLINE_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);
        PhoneLine line = null;
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id_pline")).thenReturn(1);
        when(rs.getString("phone_number")).thenReturn("2231111111");
        when(rs.getBoolean("state")).thenReturn(true);
        when(userDao.getById(rs.getInt("id_user"))).thenReturn(new User(1,"username", "password", true, new UserProfile(1,"name", "lastname", 1), new City(3,"1", "cityName", new Province(1,"provinceName"))) );
        when(typeDao.getById(rs.getInt("id_type"))).thenReturn(new UserType(1, "FIJO"));

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        line = lineDao.getById(1);

        assertNotNull(line);
        assertEquals(Integer.valueOf(1), line.getpLineId());
        verify(rs, times(1)).next();
        verify(ps, times(1)).executeQuery();
        verify(userDao, times(1)).getById(rs.getInt("id_user"));
        verify(typeDao, times(1)).getById(rs.getInt("id_type"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdError() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_PLINE_QUERY)).thenThrow(new SQLException());
        PhoneLine line = lineDao.getById(1);
    }

    @Test
    public void testGetAllOk() throws SQLException {
        when(connect.prepareStatement(BASE_PLINE_QUERY)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        List<PhoneLine> lineList = new ArrayList<>();
        when(rs.next()).thenReturn(true).thenReturn(false);

        when(rs.getInt("id_pline")).thenReturn(1);
        when(rs.getString("phone_number")).thenReturn("2231111111");
        when(rs.getBoolean("state")).thenReturn(true);
        when(userDao.getById(rs.getInt("id_user"))).thenReturn(new User(1,"username", "password", true, new UserProfile(1,"name", "lastname", 1), new City(3,"1", "cityName", new Province(1,"provinceName"))) );
        when(typeDao.getById(rs.getInt("id_type"))).thenReturn(new UserType(1, "FIJO"));

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        lineList = lineDao.getAll();

        assertNotNull(lineList);
        assertEquals(1, lineList.size());
        verify(rs, times(2)).next();
        verify(ps, times(1)).executeQuery();
        verify(userDao, times(1)).getById(rs.getInt("id_user"));
        verify(typeDao, times(1)).getById(rs.getInt("id_type"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllError() throws SQLException {
        when(connect.prepareStatement(BASE_PLINE_QUERY)).thenThrow(new SQLException());
        List<PhoneLine> lineList = lineDao.getAll();
    }

}
