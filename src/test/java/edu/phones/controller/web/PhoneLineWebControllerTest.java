package edu.phones.controller.web;

import edu.phones.controller.PhoneLineController;
import edu.phones.controller.UserController;
import edu.phones.controller.UserTypeController;
import edu.phones.domain.PhoneLine;
import edu.phones.domain.User;
import edu.phones.domain.UserType;
import edu.phones.dto.LineDto;
import edu.phones.dto.StateDto;
import edu.phones.exceptions.notExist.PhoneLineNotExistException;
import edu.phones.exceptions.notExist.TypeNotExistException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineWebControllerTest {

    @Mock
    PhoneLineController lineController;
    @Mock
    UserController userController;
    @Mock
    UserTypeController userTypeController;
    @Mock
    SessionManager sessionManager;

    PhoneLineWebController lineWebController;

    @Before
    public void setUp(){
        initMocks(this);
        this.lineWebController = new PhoneLineWebController(lineController, userController, userTypeController, sessionManager);
    }

    @Test
    public void testChangeStateOk() throws PhoneLineNotExistException {
        PhoneLine line = new PhoneLine(1, "2231111111", true, null, null);
        PhoneLine lineUpdated = new PhoneLine(1, "2231111111", false, null, null);
        StateDto dto = new StateDto();
        dto.setState(false);
        String sessionToken = "StringValue";
        Integer id = 1;

        when(lineController.getLine(id)).thenReturn(line);
        when(lineController.updateLine(line)).thenReturn(lineUpdated);

        ResponseEntity<PhoneLine> response = lineWebController.changeState(id, dto, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lineUpdated, response.getBody());
        verify(lineController, times(1)).getLine(id);
        verify(lineController, times(1)).updateLine(line);
    }

    @Test
    public void testChangeStateNotModified() throws PhoneLineNotExistException {
        PhoneLine line = new PhoneLine(1, "2231111111", true, null, null);
        StateDto dto = new StateDto();
        dto.setState(true);
        String sessionToken = "StringValue";
        Integer id = 1;

        when(lineController.getLine(id)).thenReturn(line);
        when(lineController.updateLine(line)).thenReturn(line);

        ResponseEntity<PhoneLine> response = lineWebController.changeState(id, dto, sessionToken);

        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        verify(lineController, times(1)).getLine(id);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testeChangeStateNotExists() throws PhoneLineNotExistException {
        StateDto dto = new StateDto();
        dto.setState(true);
        String sessionToken = "StringValue";
        Integer id = 1;

        when(lineController.getLine(id)).thenReturn(null);

        ResponseEntity<PhoneLine> response = lineWebController.changeState(id, dto, sessionToken);

        verify(lineController, times(1)).getLine(id);
    }

    @Test
    public void testGetLineOk(){
        PhoneLine line = new PhoneLine(1, "2231111111", true, null, null);
        String sessionToken = "StringValue";
        Integer id = 1;

        when(lineController.getLine(id)).thenReturn(line);

        ResponseEntity<PhoneLine> response = lineWebController.getLine(id, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(line, response.getBody());
        verify(lineController, times(1)).getLine(id);
    }

    @Test
    public void testGetLineNotFound(){
        String sessionToken = "StringValue";
        Integer id = 1;

        when(lineController.getLine(id)).thenReturn(null);

        ResponseEntity<PhoneLine> response = lineWebController.getLine(id, sessionToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(lineController, times(1)).getLine(id);
    }

    @Test
    public void testGetAllLinesOk(){
        List<PhoneLine> lines = new ArrayList<>();
        lines.add(new PhoneLine(1, "2231111111", true, null, null));
        String sessionToken = "StringValue";

        when(lineController.getAll()).thenReturn(lines);

        ResponseEntity<List<PhoneLine>> response = lineWebController.getLines(sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lines, response.getBody());
        verify(lineController, times(1)).getAll();
    }

    @Test
    public void testGetAllNotFound(){
        List<PhoneLine> lines = new ArrayList<>();
        String sessionToken = "StringValue";

        when(lineController.getAll()).thenReturn(lines);

        ResponseEntity<List<PhoneLine>> response = lineWebController.getLines(sessionToken);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(lineController, times(1)).getAll();
    }

    @Test
    public void testRemoveLineOk() throws PhoneLineNotExistException, TypeNotExistException, UserNotExistException {
        User user = new User(1, "username", "password", null, null, null);
        UserType type = new UserType(1, "FIJO");
        LineDto dto = new LineDto();
        dto.setLineId(1);
        dto.setNumber("2231111111");
        dto.setState(true);
        dto.setUserId(1);
        dto.setTypeId(1);
        String sessionToken = "StringValue";

        when(userController.getUser(dto.getUserId())).thenReturn(user);
        when(userTypeController.getType(dto.getTypeId())).thenReturn(type);
        PhoneLine line = new PhoneLine(dto.getLineId(), dto.getNumber(), dto.getState(), user, type);
        doNothing().when(lineController).removeLine(line);

        ResponseEntity<PhoneLine> response = lineWebController.removeLine(dto, sessionToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(line, response.getBody());
        verify(userController, times(1)).getUser(dto.getUserId());
        verify(userTypeController, times(1)).getType(dto.getTypeId());
        //verify(lineController, times(1)).removeLine(line);
    }


}
