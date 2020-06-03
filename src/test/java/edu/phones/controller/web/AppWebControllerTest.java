package edu.phones.controller.web;

import edu.phones.controller.BillController;
import edu.phones.controller.CallController;
import edu.phones.domain.User;
import edu.phones.dto.CallRequestDto;
import edu.phones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

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
    public void testGetCallsOk(){
        String month = "06";
        User user = new User(1, "username", "password", null, null);
        CallRequestDto dto = new CallRequestDto("name", "lastname", 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12);


        when(sessionManager.getCurrentUser(anyString())).thenReturn(user);
        when(callController.getDurationByMonth(user, month)).thenReturn(dto);


        assertEquals("name", dto.getName());
        assertEquals("lastname", dto.getLastname());
    }

}
