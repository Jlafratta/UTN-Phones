package edu.phones.controller;

import edu.phones.domain.Bill;
import edu.phones.domain.PhoneLine;
import edu.phones.exceptions.alreadyExist.BillAlreadyExistsException;
import edu.phones.exceptions.notExist.BillNotExistException;
import edu.phones.service.BillService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillControllerTest {

    BillController billController;
    @Mock
    BillService billService;

    @Before
    public void setUp(){
        initMocks(this);
        billController = new BillController(billService);
    }

    @Test
    public void testCreateBillOk() throws BillAlreadyExistsException {
        Bill toAdd = new Bill(1.0, 1.0, null, null, 1, null);
        Bill added = new Bill(1,1.0, 1.0, null, null, 1, null);
        when(billService.createBill(toAdd)).thenReturn(added);
        billController.createBill(toAdd);

        verify(billService, times(1)).createBill(toAdd);
    }

    @Test
    public void testRemoveBillOk() throws BillNotExistException {
        Bill toRemove = new Bill(1, 1.0, 1.0, null, null, 1, null);
        doNothing().when(billService).removeBill(toRemove);
        billController.removeBill(toRemove);

        verify(billService, times(1)).removeBill(toRemove);
    }

    @Test
    public void testUpdateBillOk() throws BillNotExistException {
        Bill toUpdate = new Bill(1, 1.0, 1.0, null, null, 1, null);
        Bill updated = new Bill(1, 2.0, 2.0, null, null, 1, null);
        when(billService.updateBill(toUpdate)).thenReturn(updated);
        billController.updateBill(toUpdate);

        assertEquals(toUpdate.getBillId(), updated.getBillId());
        verify(billService, times(1)).updateBill(toUpdate);
    }

    @Test
    public void testGetBillOk(){
        Bill bill = new Bill(1, 1.0, 1.0, null, null, 1, null);
        Integer id = 1;
        when(billService.getBill(id)).thenReturn(bill);
        billController.getBill(id);

        assertEquals(Integer.valueOf(id), bill.getBillId());
        verify(billService, times(1)).getBill(id);
    }

    @Test
    public void testGetAllOk(){
        List<Bill> bills = new ArrayList<>();
        bills.add(new Bill(1, 1.0, 1.0, null, null, 1, null));
        when(billService.getAll()).thenReturn(bills);
        billController.getAll();

        verify(billService, times(1)).getAll();
    }
}
