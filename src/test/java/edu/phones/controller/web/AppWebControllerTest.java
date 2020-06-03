package edu.phones.controller.web;

import edu.phones.controller.BillController;
import edu.phones.controller.CallController;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.CallRequestDto;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppWebControllerTest {

    @Mock
    CallController callController;
    @Mock
    BillController billController;
    @Mock
    SessionManager sessionManager;

    AppWebController appWebController;

    @Before
    public void setUp() {
        initMocks(this);
        this.appWebController = new AppWebController(callController, billController, sessionManager);
    }

    @Test
    public void testGetCallsByUserFilterByDateOk() throws UserNotExistException, ParseException {

        String from = "01/01/2020";
        String to = "01/01/2021";
        String sessionToken = "StringValue";
        List<Call> calls = new ArrayList<>();
        calls.add(new Call(1, 1, 1.0, 1.0, null, null, null, null, null));
        User currentUser = new User (1, "username", "password", null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(callController.getByOriginUserFilterByDate(currentUser,  new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to))).thenReturn(calls);

        ResponseEntity<List<Call>> response =  appWebController.getCalls(from, to, sessionToken);

        assertNotNull(calls);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), calls);
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(callController, times(1)).getByOriginUserFilterByDate(currentUser, new SimpleDateFormat("dd/MM/yyyy").parse(from), new SimpleDateFormat("dd/MM/yyyy").parse(to));
    }

    @Test
    public void testGetCallsByUserOk() throws UserNotExistException, ParseException {
        String from = null;
        String to = null;
        String sessionToken = "StringValue";
        List<Call> calls = new ArrayList<>();
        calls.add(new Call(1, 1, 1.0, 1.0, null, null, null, null, null));
        User currentUser = new User (1, "username", "password", null, null);

        when(sessionManager.getCurrentUser(sessionToken)).thenReturn(currentUser);
        when(callController.getByOriginUser(currentUser)).thenReturn(calls);

        ResponseEntity<List<Call>> response =  appWebController.getCalls(from, to, sessionToken);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), calls);
        verify(sessionManager, times(1)).getCurrentUser(sessionToken);
        verify(callController, times(1)).getByOriginUser(currentUser);
    }

    @Test
    public void testGetCallsDurationOk() throws UserNotExistException, ParseException {
        String month = "06";
        String sessiontoken = "StringValue";
        String date = "01-06-2020";
        User currentUser = new User (1, "username", "password", null, null);
        CallRequestDto dto = new CallRequestDto("name", "lastname", 10);

        when(sessionManager.getCurrentUser(sessiontoken)).thenReturn(currentUser);
        when(callController.getDurationByMonth(currentUser, date)).thenReturn(dto);

        ResponseEntity<CallRequestDto> response = appWebController.getCallsDuration(month, sessiontoken);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), dto);
        verify(sessionManager, times(1)).getCurrentUser(sessiontoken);
        verify(callController, times(1)).getDurationByMonth(currentUser, date);
    }

    @Test
    public void testGetCallsDurationBadRequest() throws UserNotExistException, ParseException {
        String month = null;
        String sessiontoken = "StringValue";
        User currentUser = new User (1, "username", "password", null, null);
        when(sessionManager.getCurrentUser(sessiontoken)).thenReturn(currentUser);

        ResponseEntity<CallRequestDto> response =  appWebController.getCallsDuration(month, sessiontoken);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(sessionManager, times(1)).getCurrentUser(sessiontoken);
    }

}
