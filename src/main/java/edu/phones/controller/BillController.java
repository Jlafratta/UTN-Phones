package edu.phones.controller;

import edu.phones.domain.Bill;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.BillAlreadyExistsException;
import edu.phones.exceptions.notExist.BillNotExistException;
import edu.phones.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class BillController {

    BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    public Bill createBill(Bill bill) throws BillAlreadyExistsException {
        return billService.createBill(bill);
    }

    public void removeBill(Bill bill) throws BillNotExistException {
        billService.removeBill(bill);
    }

    public Bill updateBill(Bill bill) throws BillNotExistException {
        return billService.updateBill(bill);
    }

    public Bill getBill(Integer id){
        return billService.getBill(id);
    }

    public List<Bill> getAll(){
        return billService.getAll();
    }

    public List<Bill> getByUserFilterByDate(User currentUser, Date dFrom, Date dTo) {
        return billService.getByUserFilterByDate(currentUser, dFrom, dTo);
    }

    public List<Bill> getByUser(User currentUser) {
        return billService.getByUser(currentUser);
    }
}
