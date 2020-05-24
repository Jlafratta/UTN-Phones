package edu.phones.controller;

import edu.phones.domain.User;
import edu.phones.domain.UserType;
import edu.phones.exceptions.notExist.TypeNotExistException;
import edu.phones.service.UserTypeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserTypeControllerTest {

    UserTypeController typeController;
    @Mock
    UserTypeService typeService;

    @Before
    public void setUp(){
        initMocks(this);
        typeController = new UserTypeController(typeService);
    }

    @Test
    public void testCreateTypeOk(){
        UserType toAdd = new UserType("TYPE");
        UserType added = new UserType(1, "TYPE");
        when(typeService.createType(toAdd)).thenReturn(added);
        typeController.createType(toAdd);

        assertEquals(toAdd.getName(), added.getName());
        verify(typeService, times(1)).createType(toAdd);
    }

    @Test
    public void testRemoveTypeOk() throws TypeNotExistException {
        UserType toRemove = new UserType(1, "TYPE");
        doNothing().when(typeService).remove(toRemove);
        typeController.removeType(toRemove);
        verify(typeService, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateTypeOk() throws TypeNotExistException {
        UserType toUpdate = new UserType(1, "TYPE");
        UserType updated = new UserType(1, "TYPE2");
        when(typeService.updateType(toUpdate)).thenReturn(updated);
        typeController.updateType(toUpdate);
        assertEquals(toUpdate.getTypeId(), updated.getTypeId());
        verify(typeService, times(1)).updateType(toUpdate);
    }

    @Test
    public void testGetTypeOk(){
        UserType type = new UserType(1, "TYPE");
        when(typeService.getType(1)).thenReturn(type);
        typeController.getType(1);
        assertEquals(Integer.valueOf(1), type.getTypeId());
        verify(typeService, times(1)).getType(1);
    }

    @Test
    public void testGetAllOk(){
        List<UserType> typeList = new ArrayList<>();
        typeList.add(new UserType(1, "TYPE"));
        typeList.add(new UserType(2, "TYPE2"));
        when(typeService.getAll()).thenReturn(typeList);
        typeService.getAll();
        assertEquals(2, typeList.size());
        verify(typeService, times(1)).getAll();
    }


}
