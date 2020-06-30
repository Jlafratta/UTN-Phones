package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.domain.Tariff;
import edu.phones.exceptions.alreadyExist.TarriffAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TariffMySQLDaoTest {

    @Mock
    Connection connect;
    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    TariffMySQLDao tariffDao;

    @Before
    public void setUp(){
        initMocks(this);
        this.tariffDao = new TariffMySQLDao(connect);
    }

    @Test
    public void testAddOk() throws SQLException, TarriffAlreadyExistsException {
        when(connect.prepareStatement(INSERT_TARIFF_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 22311);
        doNothing().when(ps).setDouble(2, 1.0);
        doNothing().when(ps).setDouble(3, 1.0);
        when(ps.execute()).thenReturn(true);
        doNothing().when(ps).close();

        Tariff tariff = tariffDao.add(new Tariff(22311, 1.0, 1.0));

        assertNotNull(tariff);
        assertEquals(Integer.valueOf(22311), tariff.getKey());
        verify(ps, times(1)).execute();
    }

    @Test(expected = RuntimeException.class)
    public void testAddError() throws SQLException, TarriffAlreadyExistsException {
        when(connect.prepareStatement(INSERT_TARIFF_QUERY)).thenThrow(new SQLException());
        Tariff tariff = tariffDao.add(new Tariff(22311, 1.0, 1.0));
    }

    @Test(expected = TarriffAlreadyExistsException.class)
    public void testAddAlreadyExists() throws SQLException, TarriffAlreadyExistsException {
        when(connect.prepareStatement(INSERT_TARIFF_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 22311);
        doNothing().when(ps).setDouble(2, 1.0);
        doNothing().when(ps).setDouble(3, 1.0);
        when(ps.execute()).thenThrow(new SQLException("Tariff already exist", "SQLSTATE[23000]: Integrity constraint violation: 1062 Duplicate",MysqlErrorNumbers.ER_DUP_ENTRY, new TarriffAlreadyExistsException()));

        Tariff tariff = tariffDao.add(new Tariff(22311, 1.0, 1.0));
    }

    @Test
    public void testUpdateOk() throws SQLException {
        when(connect.prepareStatement(UPDATE_TARIFF_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 22311);
        doNothing().when(ps).setDouble(2, 1.0);
        doNothing().when(ps).setDouble(3, 1.0);
        when(ps.executeUpdate()).thenReturn(1);
        Integer rows = tariffDao.update(new Tariff(22311, 1.0, 1.0));

        assertEquals(Integer.valueOf(1), rows);
        verify(ps, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateError() throws SQLException {
        when(connect.prepareStatement(UPDATE_TARIFF_QUERY)).thenThrow(new SQLException());
        Integer rows = tariffDao.update(new Tariff(22311, 1.0, 1.0));
    }

    @Test
    public void testRemoveOk() throws SQLException {
        when(connect.prepareStatement(DELETE_TARIFF_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeUpdate()).thenReturn(1);
        Integer rows = tariffDao.remove(1);
        assertEquals(Integer.valueOf(1), rows);
        verify(ps, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void testRemoveError() throws SQLException {
        when(connect.prepareStatement(DELETE_TARIFF_QUERY)).thenThrow(new SQLException());
        Integer rows = tariffDao.remove(1);
    }

    @Test
    public void testGetByIdOk() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_TARIFF_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt("tariff_key")).thenReturn(22311);
        when(rs.getDouble("cost")).thenReturn(1.0);
        when(rs.getDouble("price")).thenReturn(1.0);

        Tariff tariff = tariffDao.getById(1);

        assertNotNull(tariff);
        assertEquals(Integer.valueOf(22311), tariff.getKey());
        verify(ps, times(1)).executeQuery();
        verify(rs, times(1)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdError() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_TARIFF_QUERY)).thenThrow(new SQLException());
        Tariff tariff = tariffDao.getById(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAllUnsupported(){
        List<Tariff> tariffList = tariffDao.getAll();
    }

    @Test
    public void testGetAllOk() throws SQLException {
        int page = 1;
        int size = 1;
        when(connect.prepareStatement(GET_TARIFF_QUERY_PAGINATION)).thenReturn(ps);
        doNothing().when(ps).setInt(1, size);
        doNothing().when(ps).setInt(2, page);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("tariff_key")).thenReturn(22311);
        when(rs.getDouble("cost")).thenReturn(1.0);
        when(rs.getDouble("price")).thenReturn(1.0);

        List<Tariff> tariffList = tariffDao.getAll(page, size);

        assertNotNull(tariffList);
        assertEquals(1, tariffList.size());
        verify(ps, times(1)).executeQuery();
        verify(rs, times(2)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllError() throws SQLException {
        int page = 1;
        int size = 1;
        when(connect.prepareStatement(BASE_TARIFF_QUERY)).thenThrow(new SQLException());
        List<Tariff> tariffList = tariffDao.getAll(page, size);
    }
}
