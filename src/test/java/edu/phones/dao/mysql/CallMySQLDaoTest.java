package edu.phones.dao.mysql;

import edu.phones.dao.BillDao;
import edu.phones.dao.CallDao;
import edu.phones.dao.PhoneLineDao;
import edu.phones.dao.TariffDao;
import edu.phones.dto.CallQuantityDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.phones.dao.mysql.MySQLUtils.GET_QUANTITY_FROM_CHRISTMAS_CALL_QUERY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallMySQLDaoTest {

    @Mock
    Connection connect;
    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;
    @Mock
    PhoneLineDao lineDao;
    @Mock
    BillDao billDao;
    @Mock
    TariffDao tariffDao;

    CallDao callDao;

    @Before
    public void setUp(){
        initMocks(this);
        this.callDao = new CallMySQLDao(connect, lineDao, billDao, tariffDao);
    }

    @Test
    public void getCallsFromChristmasOk() throws SQLException {
        CallQuantityDto dto;
        when(connect.prepareStatement(GET_QUANTITY_FROM_CHRISTMAS_CALL_QUERY)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);

        when(rs.getInt("Cant_llamadas")).thenReturn(10);
        doNothing().when(rs).close();
        doNothing().when(ps).close();

        dto = callDao.getCallsFromChristmas();

        assertEquals(Integer.valueOf(10), dto.getQuantity());
        verify(rs, times(1)).getInt("Cant_llamadas");
    }

    @Test
    public void getCallsFromChristmasNotFound() throws SQLException {
        CallQuantityDto dto;
        when(connect.prepareStatement(GET_QUANTITY_FROM_CHRISTMAS_CALL_QUERY)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        dto = callDao.getCallsFromChristmas();

        assertNull(dto);
    }
    @Test(expected = RuntimeException.class)
    public void getCallsFromChristmasError() throws SQLException {
        when(connect.prepareStatement(GET_QUANTITY_FROM_CHRISTMAS_CALL_QUERY)).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(new SQLException());
        callDao.getCallsFromChristmas();
    }
}
