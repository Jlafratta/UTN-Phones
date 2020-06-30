package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.BillDao;
import edu.phones.dao.PhoneLineDao;
import edu.phones.dao.TariffDao;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.AddCallDto;
import edu.phones.dto.CallRequestDto;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallMySQLDaoTest {

    @Mock
    Connection connection;
    @Mock
    PhoneLineDao lineDao;
    @Mock
    BillDao billDao;
    @Mock
    TariffDao tariffDao;

    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    CallMySQLDao callDao;

    @Before
    public void setUp(){
        initMocks(this);
        this.callDao = new CallMySQLDao(connection, lineDao, billDao, tariffDao);
    }

    @Test
    public void testGetByOriginUserIdOk() throws SQLException {
        int id = 1;
        int page = 1;
        int size = 1;
        when(connection.prepareStatement(GET_BY_ORIGIN_USER_ID_DTO_CALLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, id);
        doNothing().when(ps).setInt(2, size);
        doNothing().when(ps).setInt(3, page);
        when(ps.executeQuery()).thenReturn(rs);

        List<CallRequestDto> calls = new ArrayList<>();
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getString("pnumber_origin")).thenReturn("2231111111");
        when(rs.getString("city_origin_name")).thenReturn("city1");
        when(rs.getString("pnumber_destination")).thenReturn("2213333333");
        when(rs.getString("city_destination_name")).thenReturn("city2");
        when(rs.getDouble("total_price")).thenReturn(1.0);
        when(rs.getInt("duration")).thenReturn(1);
        when(rs.getDate("call_date")).thenReturn(new Date(new java.util.Date().getTime()));

        calls = callDao.getByOriginUserId(1, page, size);

        assertNotNull(calls);
        assertEquals(1, calls.size());
        verify(ps, times(1)).executeQuery();
        verify(rs, times(2)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testGetByOriginUserIdError() throws SQLException {
        int id = 1;
        int page = 1;
        int size = 1;
        when(connection.prepareStatement(GET_BY_ORIGIN_USER_ID_DTO_CALLS_QUERY)).thenThrow(new SQLException());

        List<CallRequestDto> calls = callDao.getByOriginUserId(1, page, size);
    }

    @Test
    public void testGetByOriginUserIdAllNoContent() throws SQLException {
        when(connection.prepareStatement(GET_BY_ORIGIN_USER_ID_CALLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        List<Call> calls = callDao.getByOriginUserIdAll(1);

        assertTrue(calls.isEmpty());
        verify(connection, times(1)).prepareStatement(GET_BY_ORIGIN_USER_ID_CALLS_QUERY);
        verify(ps, times(1)).executeQuery();
    }

    @Test(expected = RuntimeException.class)
    public void testGetByOriginUserIdAllError() throws SQLException {
        when(connection.prepareStatement(GET_BY_ORIGIN_USER_ID_CALLS_QUERY)).thenThrow(new SQLException());
        List<Call> calls = callDao.getByOriginUserIdAll(1);
    }

    @Test
    public void testGetByOriginUserFilterByDateNoContent() throws SQLException {
        Date from = new Date(new java.util.Date().getTime());
        Date to = new Date(new java.util.Date().getTime());
        Integer page =1;
        Integer cant = 5;
        when(connection.prepareStatement(GET_BY_ORIGIN_USER_FILTER_BY_DATE_CALLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        doNothing().when(ps).setDate(2, from);
        doNothing().when(ps).setDate(3, to);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        List<CallRequestDto> calls = callDao.getByOriginUserFilterByDate(new User(1, "username", "password", false, null, null), from, to,page,cant);

        assertTrue(calls.isEmpty());
        verify(connection, times(1)).prepareStatement(GET_BY_ORIGIN_USER_FILTER_BY_DATE_CALLS_QUERY);
        verify(ps, times(1)).executeQuery();
    }

    @Test(expected = RuntimeException.class)
    public void testGetByOriginUserFilterByDateError() throws SQLException {
        Date from = new Date(new java.util.Date().getTime());
        Date to = new Date(new java.util.Date().getTime());
        Integer page =1;
        Integer cant = 5;
        when(connection.prepareStatement(GET_BY_ORIGIN_USER_FILTER_BY_DATE_CALLS_QUERY)).thenThrow(new SQLException());
        List<CallRequestDto> calls = callDao.getByOriginUserFilterByDate(new User(1, "username", "password", false, null, null), from, to,page,cant);
    }

    @Test
    public void testAddCallNull() throws SQLException, ParseException, CallAlreadyExistsException {
        AddCallDto dto = new AddCallDto();
        dto.setFrom("2231111111");
        dto.setTo("2601111111");
        dto.setDuration(1);
        dto.setDate("01/01/2020");

        when(connection.prepareStatement(INSERT_CALLS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        doNothing().when(ps).setInt(1, dto.getDuration());
        doNothing().when(ps).setDate(2, new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDate()) .getTime()));
        doNothing().when(ps).setString(3, dto.getFrom());
        doNothing().when(ps).setString(4, dto.getTo());

        when(ps.execute()).thenReturn(true);
        when(ps.getGeneratedKeys()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        Call call = callDao.add(dto);

        assertNull(call);
        verify(connection, times(1)).prepareStatement(INSERT_CALLS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
        verify(ps, times(1)).execute();
        verify(ps, times(1)).getGeneratedKeys();
    }

    @Test(expected = RuntimeException.class)
    public void testAddCallError() throws SQLException, CallAlreadyExistsException {
        AddCallDto dto = new AddCallDto();
        when(connection.prepareStatement(INSERT_CALLS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenThrow(new SQLException());
        Call call = callDao.add(dto);
    }

    @Test(expected = CallAlreadyExistsException.class)
    public void testAddCallAlreadyExists() throws CallAlreadyExistsException, SQLException, ParseException {
        AddCallDto dto = new AddCallDto();
        dto.setFrom("2231111111");
        dto.setTo("2601111111");
        dto.setDuration(1);
        dto.setDate("01/01/2020");
        when(connection.prepareStatement(INSERT_CALLS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(ps);
        doNothing().when(ps).setInt(1, dto.getDuration());
        doNothing().when(ps).setDate(2, new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDate()) .getTime()));
        doNothing().when(ps).setString(3, dto.getFrom());
        doNothing().when(ps).setString(4, dto.getTo());
        when(ps.execute()).thenThrow(new SQLException("Call already exist", "SQLSTATE[23000]: Integrity constraint violation: 1062 Duplicate", MysqlErrorNumbers.ER_DUP_ENTRY, new CallAlreadyExistsException() ));
        Call call = callDao.add(dto);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddUnsupported(){
        callDao.add(new Call(1, 120, 1.0, 2.0, 2.0, 4.0, null, null, null, null, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveUnsupported(){
        callDao.remove(new Call(1, 120, 1.0, 2.0, 2.0, 4.0, null, null, null, null, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveIdUnsupported(){
        callDao.remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdateUnsupported(){
        callDao.update(new Call(1, 120, 1.0, 2.0, 2.0, 4.0, null, null, null, null, null));
    }

    @Test
    public void testGetByIdOk() throws SQLException {
        when(connection.prepareStatement(GET_BY_ID_CALLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);

        when(rs.getInt("id_call")).thenReturn(1);
        when(rs.getInt("duration")).thenReturn(1);
        when(rs.getDouble(any())).thenReturn(1.0);
        when(rs.getDate("call_date")).thenReturn(new Date(new java.util.Date().getTime()));
        when(lineDao.getById(rs.getInt("pline_origin"))).thenReturn(null);
        when(lineDao.getById(rs.getInt("pline_destination"))).thenReturn(null);
        when(tariffDao.getById(rs.getInt("tariff_key"))).thenReturn(null);

        doNothing().when(rs).close();
        doNothing().when(ps).close();

        Call call = callDao.getById(1);

        assertNotNull(call);
        assertEquals(Integer.valueOf(1), call.getCallId());
        verify(connection, times(1)).prepareStatement(GET_BY_ID_CALLS_QUERY);
        verify(ps, times(1)).executeQuery();
        verify(rs, times(4)).getDouble(any());
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdError() throws SQLException {
        when(connection.prepareStatement(GET_BY_ID_CALLS_QUERY)).thenThrow(new SQLException());
        Call call = callDao.getById(1);
    }

    @Test
    public void testGetAllNotFound() throws SQLException {
        when(connection.prepareStatement(BASE_CALLS_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);
        List<Call> calls = callDao.getAll();

        assertTrue(calls.isEmpty());
        verify(connection, times(1)).prepareStatement(BASE_CALLS_QUERY);
        verify(ps, times(1)).executeQuery();
        verify(rs, times(1)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllError() throws SQLException {
        when(connection.prepareStatement(BASE_CALLS_QUERY)).thenThrow(new SQLException());
        List<Call> calls = callDao.getAll();
    }
}
