package edu.phones.service;

import edu.phones.dao.mysql.UserTypeMySQLDao;
import edu.phones.domain.User;
import edu.phones.domain.UserType;
import edu.phones.exceptions.alreadyExist.TypeAlreadyExistsException;
import edu.phones.exceptions.notExist.TypeNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserTypeServiceTest {

    UserTypeService typeService;
    @Mock
    UserTypeMySQLDao typeDao;

    @Before
    public void setUp(){
        initMocks(this);
        typeService = new UserTypeService(typeDao);
    }

    @Test
    public void testCreateTypeOk() throws TypeAlreadyExistsException {
        UserType toAdd = new UserType("name");
        UserType added = new UserType(1, "name");
        when(typeService.createType(toAdd)).thenReturn(added);

        UserType type = typeService.createType(toAdd);

        assertEquals(added.getTypeId(), type.getTypeId());
        verify(typeDao, times(1)).add(toAdd);
    }

    @Test
    public void testRemoveTypeOk() throws TypeNotExistException {
        UserType toRemove = new UserType(1, "name");
        when(typeDao.remove(toRemove)).thenReturn(2);
        typeService.remove(toRemove);
        verify(typeDao, times(1)).remove(toRemove);
    }

    @Test(expected = TypeNotExistException.class)
    public void testRemoveTypeNotExist() throws TypeNotExistException {
        UserType toRemove = new UserType(1, "name");
        when(typeDao.remove(toRemove)).thenReturn(0);
        typeService.remove(toRemove);
        verify(typeDao, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateTypeOk() throws TypeNotExistException {
        UserType toUpdate = new UserType(1, "name");
        when(typeDao.update(toUpdate)).thenReturn(1);

        UserType type = typeService.updateType(toUpdate);

        assertEquals(toUpdate.getTypeId(), type.getTypeId());
        verify(typeDao, times(1)).update(toUpdate);
    }

    @Test(expected = TypeNotExistException.class)
    public void testUpdateTypeNotExist() throws TypeNotExistException {
        UserType toUpdate = new UserType(1, "name");
        when(typeDao.update(toUpdate)).thenReturn(0);
        typeService.updateType(toUpdate);

        verify(typeDao, times(1)).update(toUpdate);
    }

    @Test
    public void testGetTypeOk(){
        UserType type = new UserType(1, "name");
        Integer id = 1;
        when(typeDao.getById(id)).thenReturn(type);

        UserType getted = typeService.getType(id);

        assertEquals(type.getTypeId(), getted.getTypeId());
        verify(typeDao, times(1)).getById(id);
    }

    @Test
    public void testGetAllOk(){
        List<UserType> types = new ArrayList<>();
        types.add(new UserType(1, "name"));
        when(typeDao.getAll()).thenReturn(types);

        List<UserType> userTypes = typeService.getAll();

        assertEquals(types.size(), userTypes.size());
        verify(typeDao, times(1)).getAll();
    }
}
