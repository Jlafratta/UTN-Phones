package edu.phones.service;

import edu.phones.dao.BillDao;
import edu.phones.domain.Bill;
import edu.phones.exceptions.BillNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    BillDao billDao;

    @Autowired
    public BillService(@Qualifier("billMySQLDao") BillDao billDao) {
        this.billDao = billDao;
    }

    public Bill createBill(Bill bill) {
        return billDao.add(bill);
    }

    public void remove(Bill bill) throws BillNotExistException {
        if(billDao.remove(bill) == 0){
            throw new BillNotExistException();
        }
    }

    public Bill updateBill(Bill bill) throws BillNotExistException {
        if(billDao.update(bill) > 0){
            return bill;
        }else{
            throw new BillNotExistException();
        }
    }

    public Bill getBill(Integer id) {
        return billDao.getById(id);
    }

    public List<Bill> getAll() {
        return billDao.getAll();
    }
}
