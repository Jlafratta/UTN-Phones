package edu.phones.service;

import edu.phones.dao.mysql.CallMySQLDao;
import edu.phones.domain.Call;
import edu.phones.exceptions.notExist.CallNotExistException;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallServiceTest {

    CallService callService;
    @Mock
    CallMySQLDao callDao;

    @Before
    public void setUp(){
        initMocks(this);
        callService = new CallService(callDao);
    }

    @Test
    public void testCreateCallOk(){
        Call toAdd = new Call(120, 1.0, 2.0, null, null, null, null, null);
        Call added = new Call(1, 120, 1.0, 2.0, null, null, null, null, null);
        when(callDao.add(toAdd)).thenReturn(added);
        callService.createCall(toAdd);

        assertEquals(Integer.valueOf(1), added.getCallId());
        assertEquals(toAdd.getDuration(), added.getDuration());
        verify(callDao, times(1)).add(toAdd);
    }

    @Test
    public void testRemoveCallOk() throws CallNotExistException {
        Call toRemove = new Call(1, 120, 1.0, 2.0, null, null, null, null, null);
        when(callDao.remove(toRemove)).thenReturn(1);
        callService.removeCall(toRemove);

        verify(callDao, times(1)).remove(toRemove);
    }

    @Test(expected = CallNotExistException.class)
    public void testRemoveCallNotExist() throws CallNotExistException {
        Call toRemove = new Call(1, 120, 1.0, 2.0, null, null, null, null, null);
        when(callDao.remove(toRemove)).thenReturn(0);
        callService.removeCall(toRemove);

        verify(callDao, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateCallOk() throws CallNotExistException {
        Call toUpdate = new Call(1, 120, 1.0, 2.0, null, null, null, null, null);
        when(callDao.update(toUpdate)).thenReturn(1);
        callService.updateCall(toUpdate);

        verify(callDao, times(1)).update(toUpdate);
    }

    @Test(expected = CallNotExistException.class)
    public void testUpdateCallNotExist() throws CallNotExistException {
        Call toUpdate = new Call(1, 120, 1.0, 2.0, null, null, null, null, null);
        when(callDao.update(toUpdate)).thenReturn(0);
        callService.updateCall(toUpdate);

        verify(callDao, times(1)).update(toUpdate);
    }

    @Test
    public void testGetCallOk(){
        Call call = new Call(1,120, 1.0, 2.0, null, null, null, null, null);
        Integer id = 1;
        when(callDao.getById(id)).thenReturn(call);
        callService.getCall(id);

        assertEquals(id, call.getCallId());
        verify(callDao, times(1)).getById(id);
    }

    @Test
    public void testGetAllOk(){
        List<Call> calls = new ArrayList<>();
        calls.add(new Call(1,120, 1.0, 2.0, null, null, null, null, null));
        when(callDao.getAll()).thenReturn(calls);
        callService.getAll();

        verify(callDao, times(1)).getAll();
    }
}
