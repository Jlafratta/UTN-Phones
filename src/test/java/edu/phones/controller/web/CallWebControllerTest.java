package edu.phones.controller.web;

import edu.phones.controller.CallController;
import edu.phones.dto.CallQuantityDto;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallWebControllerTest {

    @Mock
    CallController callController;
    @Mock
    SessionManager sessionManager;

    CallWebController callWebController;

    @Before
    public void setUp(){
        initMocks(this);
        this.callWebController = new CallWebController(callController, sessionManager);
    }

    @Test
    public void testGetCantCallsFromChristmasOk(){

        String sessionToken = "StringValue";
        CallQuantityDto dto = new CallQuantityDto(10);

        when(callController.getCallsFromChristmas()).thenReturn(dto);

        ResponseEntity<CallQuantityDto> response = callWebController.getCantCallsFromChristmas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(callController, times(1)).getCallsFromChristmas();
    }

    @Test
    public void testGetCantCallsFromChristmasNotFound(){
        String sessionToken = "StringValue";
        CallQuantityDto dto = new CallQuantityDto(10);

        when(callController.getCallsFromChristmas()).thenReturn(null);

        ResponseEntity<CallQuantityDto> response = callWebController.getCantCallsFromChristmas();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull( response.getBody());
        verify(callController, times(1)).getCallsFromChristmas();

    }

}
