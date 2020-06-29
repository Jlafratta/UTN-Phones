package edu.phones.controller.web;

import edu.phones.controller.*;
import edu.phones.domain.*;
import edu.phones.dto.AddCallDto;
import edu.phones.dto.CallRequestDto;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientWebControllerTest {

    @Mock
    CallController callController;
    @Mock
    BillController billController;
    @Mock
    UserController userController;
    @Mock
    PhoneLineController lineController;
    @Mock
    TariffController tariffController;
    @Mock
    SessionManager sessionManager;

    ClientWebController clientWebController;

    @Before
    public void setUp() {
        initMocks(this);
        this.clientWebController = new ClientWebController(callController, billController, userController, lineController, tariffController, sessionManager);
    }

    @Test   // "/api/calls"
    public void testGetCallsByUserFilterByDateOk() throws UserNotExistException, ParseException {

        String from = "01/01/2020";
        String to = "01/01/2021";
        String sessionToken = "StringValue";
        List<CallRequestDto> calls = new ArrayList<>();
        calls.add(new CallRequestDto( "2231111111", "StringValue", "2211111111", "StringValue", 1.0, 1, null));
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(callController.getByOriginUserFilterByDate(currentUser,  new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to))).thenReturn(calls);

        ResponseEntity<List<CallRequestDto>> response =  clientWebController.getCalls(from, to, sessionToken);

        assertNotNull(calls);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(calls, response.getBody());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(callController, times(1)).getByOriginUserFilterByDate(currentUser, new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to));
    }

    @Test   // "/api/calls"
    public void testGetCallsByUserFilterByDateNoContent() throws UserNotExistException, ParseException {

        String from = "01/01/2020";
        String to = "01/01/2021";
        String sessionToken = "StringValue";
        List<CallRequestDto> calls = new ArrayList<>();
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(callController.getByOriginUserFilterByDate(currentUser,  new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to))).thenReturn(calls);

        ResponseEntity<List<CallRequestDto>> response =  clientWebController.getCalls(from, to, sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(callController, times(1)).getByOriginUserFilterByDate(currentUser, new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to));
    }

    @Test   // "api/calls"
    public void testGetCallsByUserOk() throws UserNotExistException, ParseException {
        String from = null;
        String to = null;
        String sessionToken = "StringValue";
        List<CallRequestDto> calls = new ArrayList<>();
        calls.add(new CallRequestDto( "2231111111", "StringValue", "2211111111", "StringValue", 1.0, 1, null));
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(callController.getByOriginUserId(currentUser.getUserId())).thenReturn(calls);

        ResponseEntity<List<CallRequestDto>> response =  clientWebController.getCalls(from, to, sessionToken);

        assertNotNull(calls);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(calls, response.getBody());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(callController, times(1)).getByOriginUserId(currentUser.getUserId());
    }

    @Test   // "api/calls"
    public void testGetCallsByUserNoContent() throws UserNotExistException, ParseException {
        String from = null;
        String to = null;
        String sessionToken = "StringValue";
        List<CallRequestDto> calls = new ArrayList<>();
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(callController.getByOriginUserId(currentUser.getUserId())).thenReturn(calls);

        ResponseEntity<List<CallRequestDto>> response =  clientWebController.getCalls(from, to, sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(callController, times(1)).getByOriginUserId(currentUser.getUserId());
    }


    @Test   // "api/bills"
    public void testGetBillsByUserFilterByDateOk() throws UserNotExistException, ParseException {
        String from = "01/01/2020";
        String to = "01/01/2021";
        String sessionToken = "StringValue";
        List<Bill> bills = new ArrayList<>();
        bills.add(new Bill(1, 1.0, 1.0, null, null, 1, null));
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(billController.getByUserFilterByDate(currentUser, new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to))).thenReturn(bills);

        ResponseEntity<List<Bill>> response = clientWebController.getBills(from, to, sessionToken);

        assertNotNull(bills);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bills, response.getBody());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(billController, times(1)).getByUserFilterByDate(currentUser, new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to));
    }

    @Test   // "api/bills"
    public void testGetBillsByUserFilterByDateNoContent() throws UserNotExistException, ParseException {
        String from = "01/01/2020";
        String to = "01/01/2021";
        String sessionToken = "StringValue";
        List<Bill> bills = new ArrayList<>();
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(billController.getByUserFilterByDate(currentUser, new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to))).thenReturn(bills);

        ResponseEntity<List<Bill>> response = clientWebController.getBills(from, to, sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(billController, times(1)).getByUserFilterByDate(currentUser, new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to));
    }

    @Test   // "api/bills"
    public void testGetBillsByUserOk() throws UserNotExistException, ParseException {
        String from = null;
        String to = null;
        String sessionToken = "StringValue";
        List<Bill> bills = new ArrayList<>();
        bills.add(new Bill(1, 1.0, 1.0, null, null, 1, null));
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(billController.getByUser(currentUser)).thenReturn(bills);

        ResponseEntity<List<Bill>> response = clientWebController.getBills(from, to, sessionToken);

        assertNotNull(bills);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bills, response.getBody());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(billController, times(1)).getByUser(currentUser);
    }

    @Test   // "api/bills"
    public void testGetBillsByUserNotFound() throws UserNotExistException, ParseException {
        String from = null;
        String to = null;
        String sessionToken = "StringValue";
        List<Bill> bills = new ArrayList<>();
        User currentUser = new User (1, "username", "password", false, null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(billController.getByUser(currentUser)).thenReturn(bills);

        ResponseEntity<List<Bill>> response = clientWebController.getBills(from, to, sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(billController, times(1)).getByUser(currentUser);
    }

    @Test   // "api/lines/top10"
    public void testGetTopTenCallsOk() throws UserNotExistException {
        String sessionToken = "StringValue";
        User currentUser = new User (1, "username", "password", false, null, null);
        List<PhoneLine> topTen = new ArrayList<>();
        topTen.add(new PhoneLine(1, "2231111111", null, null, null));

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(lineController.getTopTen(currentUser)).thenReturn(topTen);

        ResponseEntity<List<PhoneLine>> response = clientWebController.getTopTenCalls(sessionToken);

        assertNotNull(topTen);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(topTen, response.getBody());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(lineController, times(1)).getTopTen(currentUser);
    }

    @Test   // "api/lines/top10"
    public void testGetTopTenCallsNoContent() throws UserNotExistException {
        String sessionToken = "StringValue";
        User currentUser = new User (1, "username", "password", false, null, null);
        List<PhoneLine> topTen = new ArrayList<>();

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(lineController.getTopTen(currentUser)).thenReturn(topTen);

        ResponseEntity<List<PhoneLine>> response = clientWebController.getTopTenCalls(sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(lineController, times(1)).getTopTen(currentUser);
    }

    @Test   // "backoffice/tairff/{fromPrefix}/{toPrefix}"
    public void testGetTariffOk(){
        String fromPrefix = "123";
        String toPrefix = "321";
        String sessionToken = "StringValue";
        Tariff tariff = new Tariff(123321, 1.0, 1.0);

        when(tariffController.getTariff(Integer.parseInt(fromPrefix + toPrefix))).thenReturn(tariff);

        ResponseEntity<Tariff> response = clientWebController.getTariff(fromPrefix, toPrefix, sessionToken);

        assertNotNull(tariff);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tariff, response.getBody());
        verify(tariffController, times(1)).getTariff(Integer.parseInt(fromPrefix + toPrefix));
    }

    @Test   // "backoffice/tairff/{fromPrefix}/{toPrefix}"
    public void testGetTariffNotFound(){
        String fromPrefix = "123";
        String toPrefix = "321";
        String sessionToken = "StringValue";

        when(tariffController.getTariff(Integer.parseInt(fromPrefix + toPrefix))).thenReturn(null);

        ResponseEntity<Tariff> response = clientWebController.getTariff(fromPrefix, toPrefix, sessionToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tariffController, times(1)).getTariff(Integer.parseInt(fromPrefix + toPrefix));
    }

    @Test   // "backoffice/tariffs"
    public void testGetAllTariffsOk(){
        String sessionToken = "StringValue";
        List<Tariff> tariffList = new ArrayList<>();
        tariffList.add(new Tariff(123321, 1.0, 1.0));

        when(tariffController.getAll()).thenReturn(tariffList);

        ResponseEntity<List<Tariff>> response = clientWebController.getAllTariffs(sessionToken);

        assertNotNull(tariffList);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tariffList, response.getBody());
        verify(tariffController, times(1)).getAll();
    }

    @Test   // "backoffice/tariffs"
    public void testGetAllTariffsNoContent(){
        String sessionToken = "StringValue";
        List<Tariff> tariffList = new ArrayList<>();

        when(tariffController.getAll()).thenReturn(tariffList);

        ResponseEntity<List<Tariff>> response = clientWebController.getAllTariffs(sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tariffController, times(1)).getAll();
    }

    @Test   // "backoffice/calls"
    public void testGetCallsByUsernameOk() throws UserNotExistException {
        String username = "username";
        String sessionToken = "StringValue";
        User user = new User(1, "username", "password", null, null, null);
        List<Call> calls = new ArrayList<>();
        calls.add(new Call(1, 120, 1.0, 2.0, 2.0, 4.0, null, null, null, null, null));

        when(userController.getByUsername(username)).thenReturn(user);
        when(callController.getByOriginUserIdAll(user.getUserId())).thenReturn(calls);

        ResponseEntity<List<Call>> response = clientWebController.getCallsByUsername(username, sessionToken);

        assertNotNull(calls);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(calls, response.getBody());
        verify(userController, times(1)).getByUsername(username);
        verify(callController, times(1)).getByOriginUserIdAll(user.getUserId());
    }

    @Test   // "backoffice/calls"
    public void testGetCallsByUsernameNoContent() throws UserNotExistException {
        String username = "username";
        String sessionToken = "StringValue";
        User user = new User(1, "username", "password", null, null, null);
        List<Call> calls = new ArrayList<>();

        when(userController.getByUsername(username)).thenReturn(user);
        when(callController.getByOriginUserIdAll(user.getUserId())).thenReturn(calls);

        ResponseEntity<List<Call>> response = clientWebController.getCallsByUsername(username, sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userController, times(1)).getByUsername(username);
        verify(callController, times(1)).getByOriginUserIdAll(user.getUserId());
    }

    @Test(expected = UserNotExistException.class)   // "backoffice/calls"
    public void testGetCallsByUsernameUserNotFound() throws UserNotExistException {
        String username = "username";
        String sessionToken = "StringValue";
        when(userController.getByUsername(username)).thenReturn(null);
        ResponseEntity<List<Call>> response = clientWebController.getCallsByUsername(username, sessionToken);
        verify(userController, times(1)).getByUsername(username);
    }

    @Test   // "backoffice/calls"
    public void testGetAllCallsOk() throws UserNotExistException {
        String username = null;
        String sessionToken = "StringValue";
        List<Call> calls = new ArrayList<>();
        calls.add(new Call(1, 120, 1.0, 2.0, 2.0, 4.0, null, null, null, null, null));

        when(callController.getAll()).thenReturn(calls);

        ResponseEntity<List<Call>> response = clientWebController.getCallsByUsername(username, sessionToken);

        assertNotNull(calls);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(calls, response.getBody());
        verify(callController, times(1)).getAll();
    }

    @Test   // "backoffice/calls/{id}"
    public void testGetCallByIdOk(){
        Integer id = 1;
        String sessionToken = "StringValue";
        Call call = new Call(1, 120, 1.0, 2.0, 2.0, 4.0, null, null, null, null, null);

        when(callController.getCall(id)).thenReturn(call);

        ResponseEntity<Call> response = clientWebController.getCallById(id, sessionToken);

        assertNotNull(call);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(call, response.getBody());
        verify(callController, times(1)).getCall(id);
    }

    @Test   // "backoffice/calls/{id}"
    public void testGetCallByIdNotFound(){
        Integer id = 1;
        String sessionToken = "StringValue";

        when(callController.getCall(id)).thenReturn(null);

        ResponseEntity<Call> response = clientWebController.getCallById(id, sessionToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(callController, times(1)).getCall(id);
    }

    @Test   // "/backoffice/bills/{id}"
    public void testGetBillByIdOk(){
        Integer id = 1;
        String sessionToken = "StringValue";
        Bill bill = new Bill(1, 1.0, 1.0, null, null, 1, null);

        when(billController.getBill(id)).thenReturn(bill);

        ResponseEntity<Bill> response = clientWebController.getBill(id, sessionToken);

        assertNotNull(bill);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bill, response.getBody());
        verify(billController, times(1)).getBill(id);
    }

    @Test   // "/backoffice/bills/{id}"
    public void testGetBillByIdNotFound(){
        Integer id = 1;
        String sessionToken = "StringValue";

        when(billController.getBill(id)).thenReturn(null);

        ResponseEntity<Bill> response = clientWebController.getBill(id, sessionToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(billController, times(1)).getBill(id);
    }

    @Test   // "/backoffice/client/{id}/bills"
    public void testBackofficeGetBillsByUserOk() throws UserNotExistException {
        User user =  new User(1, "username", "password", null, null, null);
        List<Bill> billList = new ArrayList<>();
        billList.add(new Bill(1, 1.0, 1.0, null, null, 1, null));
        Integer id = 1;
        String sessionToken = "StringValue";

        when(userController.getUser(id)).thenReturn(user);
        when(billController.getByUser(user)).thenReturn(billList);

        ResponseEntity<List<Bill>> response = clientWebController.getBillsByUser(id, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(billList, response.getBody());
        verify(userController, times(1)).getUser(id);
        verify(billController, times(1)).getByUser(user);
    }

    @Test   // "/backoffice/client/{id}/bills"
    public void testBackofficeGetBillsByUserNoContent() throws UserNotExistException {
        User user =  new User(1, "username", "password", null, null, null);
        List<Bill> billList = new ArrayList<>();
        Integer id = 1;
        String sessionToken = "StringValue";

        when(userController.getUser(id)).thenReturn(user);
        when(billController.getByUser(user)).thenReturn(billList);

        ResponseEntity<List<Bill>> response = clientWebController.getBillsByUser(id, sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userController, times(1)).getUser(id);
        verify(billController, times(1)).getByUser(user);
    }

    @Test(expected = UserNotExistException.class)   // "/backoffice/client/{id}/bills"
    public void testBackofficeGetBillsByUserNotExists() throws UserNotExistException {
        Integer id = 1;
        String sessionToken = "StringValue";

        when(userController.getUser(id)).thenReturn(null);

        ResponseEntity<List<Bill>> response = clientWebController.getBillsByUser(id, sessionToken);

        verify(userController, times(1)).getUser(id);
    }

/*
    @Test   // "/inf"
    public void testAddCallOk() throws CallAlreadyExistsException {
        AddCallDto dto = new AddCallDto();
        dto.setFrom("2231111111");
        dto.setTo("2602222222");
        dto.setDuration(1);
        dto.setDate("01/01/2020");

        Call call = new Call(1, 120, 1.0, 2.0, 2.0, 4.0, null, null, null, null, null);
        String sessionToken = "StringValue";

        when(callController.createCall(dto)).thenReturn(call);

        ResponseEntity<Call> response = clientWebController.addCall(dto, sessionToken);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(call, response.getBody());
    }*/ //TODO ver tema de la URI

    @Test(expected = CallAlreadyExistsException.class)   // "/inf"
    public void testAddCallAlreadyExist() throws CallAlreadyExistsException {
        AddCallDto dto = new AddCallDto();
        dto.setFrom("2231111111");
        dto.setTo("2602222222");
        dto.setDuration(1);
        dto.setDate("01/01/2020");
        String sessionToken = "StringValue";

        when(callController.createCall(dto)).thenThrow(new CallAlreadyExistsException());
        ResponseEntity<Call> response = clientWebController.addCall(dto, sessionToken);
    }

}

