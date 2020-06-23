package edu.phones.service;

import edu.phones.dao.BillDao;
import edu.phones.domain.Bill;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.BillAlreadyExistsException;
import edu.phones.exceptions.notExist.BillNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillService {

    BillDao billDao;

    @Autowired
    public BillService(@Qualifier("billMySQLDao") BillDao billDao) {
        this.billDao = billDao;
    }

    public Bill createBill(Bill bill) throws BillAlreadyExistsException {
        return billDao.add(bill);
    }

    public void removeBill(Bill bill) throws BillNotExistException {
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

    public List<Bill> getByUserFilterByDate(User currentUser, Date dFrom, Date dTo) {
        return billDao.getByUserFilterByDate(currentUser, dFrom, dTo);
    }

    public List<Bill> getByUser(User currentUser) {
        return billDao.getByUser(currentUser);
    }
}
