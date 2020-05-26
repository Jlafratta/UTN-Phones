package edu.phones.service;

import edu.phones.dao.mysql.BillMySQLDao;
import edu.phones.domain.Bill;
import edu.phones.exceptions.notExist.BillNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillServiceTest {

    BillService billService;
    @Mock
    BillMySQLDao billDao;

    @Before
    public void setUp(){
        initMocks(this);
        billService = new BillService(billDao);
    }

    @Test
    public void testCreateBillOk(){
        Bill toAdd = new Bill(1.0, 1.0, null, null, 1, null);
        Bill added = new Bill(1,1.0, 1.0, null, null, 1, null);
        when(billDao.add(toAdd)).thenReturn(added);
        billService.createBill(toAdd);

        assertEquals(toAdd.getCost(), added.getCost());
        assertEquals(toAdd.getTotal(), added.getTotal());
        assertEquals(Integer.valueOf(1), added.getBillId());
        verify(billDao, times(1)).add(toAdd);
    }

    @Test
    public void testRemoveBillOk() throws BillNotExistException {
        Bill toRemove = new Bill(1,1.0, 1.0, null, null, 1, null);
        when(billDao.remove(toRemove)).thenReturn(1);
        billService.removeBill(toRemove);

        verify(billDao, times(1)).remove(toRemove);
    }

    @Test(expected = BillNotExistException.class)
    public void testRemoveBillNotExist() throws BillNotExistException {
        Bill toRemove = new Bill(1,1.0, 1.0, null, null, 1, null);
        when(billDao.remove(toRemove)).thenReturn(0);
        billService.removeBill(toRemove);

        verify(billDao, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateBillOk() throws BillNotExistException {
        Bill toUpdate = new Bill(1, 1.0, 1.0, null, null, 1, null);
        when(billDao.update(toUpdate)).thenReturn(1);
        billService.updateBill(toUpdate);

        verify(billDao, times(1)).update(toUpdate);
    }

    @Test(expected = BillNotExistException.class)
    public void testUpdateBillNotExist() throws BillNotExistException {
        Bill toUpdate = new Bill(1, 1.0, 1.0, null, null, 1, null);
        when(billDao.update(toUpdate)).thenReturn(0);
        billService.updateBill(toUpdate);

        verify(billDao, times(1)).update(toUpdate);
    }

    @Test
    public void testGetBillOk(){
        Bill bill = new Bill(1, 1.0, 1.0, null, null, 1, null);
        Integer id = 1;
        when(billDao.getById(id)).thenReturn(bill);
        billService.getBill(id);

        assertEquals(id, bill.getBillId());
        verify(billDao, times(1)).getById(id);
    }

    @Test
    public void testGetAllOk(){
        List<Bill> bills = new ArrayList<>();
        bills.add(new Bill(1, 1.0, 1.0, null, null, 1, null));
        when(billDao.getAll()).thenReturn(bills);
        billService.getAll();

        verify(billDao, times(1)).getAll();
    }
}
