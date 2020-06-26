package edu.phones.service;

import edu.phones.dao.mysql.PhoneLineMySQLDao;
import edu.phones.domain.PhoneLine;
import edu.phones.exceptions.alreadyExist.PhoneLineAlreadyExistsException;
import edu.phones.exceptions.notExist.PhoneLineNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineServiceTest {

    PhoneLineService lineService;
    @Mock
    PhoneLineMySQLDao lineDao;

    @Before
    public void setUp(){
        initMocks(this);
        lineService = new PhoneLineService(lineDao);
    }

    @Test
    public void testCreateLineOk() throws PhoneLineAlreadyExistsException {
        PhoneLine toAdd = new PhoneLine("2235997823", true, null, null);
        PhoneLine added = new PhoneLine(1, "2235997823", true, null, null);
        when(lineDao.add(toAdd)).thenReturn(added);

        PhoneLine line = lineService.createLine(toAdd);

        assertEquals(added.getpLineId(), line.getpLineId());
        assertEquals(added.getNumber(), line.getNumber());
        verify(lineDao, times(1)).add(toAdd);
    }

    @Test
    public void testRemoveLineOk() throws PhoneLineNotExistException {
        PhoneLine toRemove = new PhoneLine(1, "2235997823", true, null, null);
        when(lineDao.remove(toRemove)).thenReturn(3);
        lineService.removeLine(toRemove);

        verify(lineDao, times(1)).remove(toRemove);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testRemoveLineNotExist() throws PhoneLineNotExistException {
        PhoneLine toRemove = new PhoneLine(1, "2235997823", true, null, null);
        when(lineDao.remove(toRemove)).thenReturn(0);
        lineService.removeLine(toRemove);

        verify(lineDao, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateLineOk() throws PhoneLineNotExistException {
        PhoneLine toUpdate = new PhoneLine(1, "2235997823", true, null, null);
        when(lineDao.update(toUpdate)).thenReturn(1);

        PhoneLine line = lineService.updateLine(toUpdate);

        assertEquals(toUpdate.getpLineId(), line.getpLineId());
        verify(lineDao, times(1)).update(toUpdate);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testUpdateLineNotExist() throws PhoneLineNotExistException {
        PhoneLine toUpdate = new PhoneLine(1, "2235997823", true, null, null);
        when(lineDao.update(toUpdate)).thenReturn(0);
        lineService.updateLine(toUpdate);

        verify(lineDao, times(1)).update(toUpdate);
    }

    @Test
    public void testGetLineOk(){
        PhoneLine line = new PhoneLine(1, "2235997823", true, null, null);
        Integer id = 1;
        when(lineDao.getById(id)).thenReturn(line);

        PhoneLine getted = lineService.getLine(id);

        assertEquals(line.getpLineId(), getted.getpLineId());
        verify(lineDao, times(1)).getById(id);
    }

    @Test
    public void testGetAllOk(){
        List<PhoneLine> lines = new ArrayList<>();
        lines.add(new PhoneLine(1, "2235997823", true, null, null));
        when(lineDao.getAll()).thenReturn(lines);

        List<PhoneLine> lineList = lineService.getAll();

        assertEquals(lines.size(), lineList.size());
        verify(lineDao, times(1)).getAll();
    }
}
