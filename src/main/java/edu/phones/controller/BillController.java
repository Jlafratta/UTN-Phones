package edu.phones.controller;

import edu.phones.domain.Bill;
import edu.phones.exceptions.notExist.BillNotExistException;
import edu.phones.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BillController {

    BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    public Bill createBill(Bill bill){
        return billService.createBill(bill);
    }

    public void remove(Bill bill) throws BillNotExistException {
        billService.remove(bill);
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
}
