package edu.phones.controller;

import edu.phones.domain.Call;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;
import edu.phones.exceptions.notExist.CallNotExistException;
import edu.phones.service.CallService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {

    CallController callController;
    @Mock
    CallService callService;

    @Before
    public void setUp(){
        initMocks(this);
        callController = new CallController(callService);
    }

    @Test
    public void testCreateCallOk() throws CallAlreadyExistsException {
        Call toAdd = new Call(120, 1.0, 2.0, null, null, null, null, null);
        Call added = new Call(1, 120, 1.0, 2.0, null, null, null, null, null);
        when(callService.createCall(toAdd)).thenReturn(added);

        Call call = callController.createCall(toAdd);

        assertEquals(added.getCallId(), call.getCallId());
        verify(callService, times(1)).createCall(toAdd);
    }

    @Test
    public void testRemoveCallOk() throws CallNotExistException {
        Call toRemove = new Call(1, 120, 1.0, 2.0, null, null, null, null, null);
        doNothing().when(callService).removeCall(toRemove);

        callController.removeCall(toRemove);

        verify(callService, times(1)).removeCall(toRemove);
    }

    @Test
    public void testUpdateCallOk() throws CallNotExistException {
        Call toUpdate = new Call(1,120, 1.0, 2.0, null, null, null, null, null);
        Call updated = new Call(1, 120, 2.0, 4.0, null, null, null, null, null);
        when(callService.updateCall(toUpdate)).thenReturn(updated);

        Call call = callController.updateCall(toUpdate);

        assertEquals(updated.getCallId(), call.getCallId());
        verify(callService, times(1)).updateCall(toUpdate);
    }

    @Test
    public void testGetCallOk(){
        Call call = new Call(1,120, 1.0, 2.0, null, null, null, null, null);
        Integer id = 1;
        when(callService.getCall(id)).thenReturn(call);

        Call getted = callController.getCall(id);

        assertEquals(call.getCallId(), getted.getCallId());
        verify(callService, times(1)).getCall(id);
    }

    @Test
    public void testGetAllOk(){
        List<Call> calls = new ArrayList<>();
        calls.add(new Call(1,120, 1.0, 2.0, null, null, null, null, null));
        when(callService.getAll()).thenReturn(calls);

        List<Call> callList = callController.getAll();

        assertEquals(calls.size(), callList.size());
        verify(callService, times(1)).getAll();
    }
}
