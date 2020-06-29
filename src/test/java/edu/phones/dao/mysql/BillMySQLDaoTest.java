package edu.phones.dao.mysql;

import edu.phones.dao.BillDao;
import edu.phones.dao.PhoneLineDao;
import edu.phones.domain.Bill;
import edu.phones.domain.PhoneLine;
import edu.phones.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.management.RuntimeMBeanException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillMySQLDaoTest {

    @Mock
    Connection connect;
    @Mock
    PhoneLineDao lineDao;
    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    BillMySQLDao billDao;

    @Before
    public void setUp(){
        initMocks(this);
        this.billDao = new BillMySQLDao(connect, lineDao);
    }

    @Test
    public void testGetByUserFilterByDateOk() throws SQLException {
        User currentUser = new User(1, "username", "password", null, null, null);
        Date dFrom = new Date();
        Date dTo = new Date();
        when(connect.prepareStatement(GET_BY_USER_FILTER_BY_DATE_BILLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, currentUser.getUserId());
        doNothing().when(ps).setDate(2, new java.sql.Date(dFrom.getTime()));
        doNothing().when(ps).setDate(2, new java.sql.Date(dTo.getTime()));
        when(ps.executeQuery()).thenReturn(rs);

        List<Bill> bills = new ArrayList<>();
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("id_bill")).thenReturn(1);
        when(rs.getDouble("cost")).thenReturn(1.0);
        when(rs.getDouble("price")).thenReturn(1.0);
        when(rs.getDate("bill_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getDate("expire_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getInt("calls_count")).thenReturn(1);
        when(lineDao.getById(rs.getInt("id_pline"))).thenReturn(new PhoneLine(1, "2231111111", true, null, null));

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        bills = billDao.getByUserFilterByDate(currentUser, dFrom, dTo);

        assertEquals(1, bills.size());
        verify(rs, times(2)).next();
        verify(ps, times(1)).executeQuery();
        verify(lineDao, times(1)).getById(rs.getInt("id_pline"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByUserFilterByDateError() throws SQLException {
        User currentUser = new User(1, "username", "password", null, null, null);
        Date dFrom = new Date();
        Date dTo = new Date();
        when(connect.prepareStatement(GET_BY_USER_FILTER_BY_DATE_BILLS_QUERY)).thenThrow(new SQLException());

        List<Bill> bills = billDao.getByUserFilterByDate(currentUser, dFrom, dTo);
    }

    @Test
    public void testGetByUserOk() throws SQLException {
        User currentUser = new User(1, "username", "password", null, null, null);
        when(connect.prepareStatement(GET_BY_USER_BILLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, currentUser.getUserId());
        when(ps.executeQuery()).thenReturn(rs);

        List<Bill> bills = new ArrayList<>();
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("id_bill")).thenReturn(1);
        when(rs.getDouble("cost")).thenReturn(1.0);
        when(rs.getDouble("price")).thenReturn(1.0);
        when(rs.getDate("bill_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getDate("expire_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getInt("calls_count")).thenReturn(1);
        when(lineDao.getById(rs.getInt("id_pline"))).thenReturn(new PhoneLine(1, "2231111111", true, null, null));

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        bills = billDao.getByUser(currentUser);

        assertEquals(1, bills.size());
        verify(rs, times(2)).next();
        verify(ps, times(1)).executeQuery();
        verify(lineDao, times(1)).getById(rs.getInt("id_pline"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByUserError() throws SQLException {
        User currentUser = new User(1, "username", "password", null, null, null);
        when(connect.prepareStatement(GET_BY_USER_BILLS_QUERY)).thenThrow(new SQLException());
        List<Bill> bills = billDao.getByUser(currentUser);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddUnsupported(){
        billDao.add(new Bill(1.0, 1.0, null, null, 1, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveUnsupported(){
        billDao.remove(new Bill(1.0, 1.0, null, null, 1, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdateUnsupported(){
        billDao.update(new Bill(1.0, 1.0, null, null, 1, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveByIdUnsupported(){
        billDao.remove(1);
    }

    @Test
    public void testGetByIdOk() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_BILLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt("id_bill")).thenReturn(1);
        when(rs.getDouble("cost")).thenReturn(1.0);
        when(rs.getDouble("price")).thenReturn(1.0);
        when(rs.getDate("bill_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getDate("expire_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getInt("calls_count")).thenReturn(1);
        when(lineDao.getById(rs.getInt("id_pline"))).thenReturn(new PhoneLine(1, "2231111111", true, null, null));

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        Bill bill = billDao.getById(1);

        assertEquals(Integer.valueOf(1), bill.getBillId());
        verify(rs, times(1)).next();
        verify(ps, times(1)).executeQuery();
        verify(lineDao, times(1)).getById(rs.getInt("id_pline"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdError() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_BILLS_QUERY)).thenThrow(new SQLException());
        Bill bill = billDao.getById(1);
    }

    @Test
    public void testGetAllOk() throws SQLException {
        when(connect.prepareStatement(BASE_BILLS_QUERY)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        List<Bill> bills = new ArrayList<>();
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("id_bill")).thenReturn(1);
        when(rs.getDouble("cost")).thenReturn(1.0);
        when(rs.getDouble("price")).thenReturn(1.0);
        when(rs.getDate("bill_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getDate("expire_date")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getInt("calls_count")).thenReturn(1);
        when(lineDao.getById(rs.getInt("id_pline"))).thenReturn(new PhoneLine(1, "2231111111", true, null, null));

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        bills = billDao.getAll();

        assertEquals(1, bills.size());
        verify(rs, times(2)).next();
        verify(ps, times(1)).executeQuery();
        verify(lineDao, times(1)).getById(rs.getInt("id_pline"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllErrro() throws SQLException {
        when(connect.prepareStatement(BASE_BILLS_QUERY)).thenThrow(new SQLException());
        List<Bill> bills = billDao.getAll();
    }
}
