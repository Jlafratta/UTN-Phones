package edu.phones.dao;

import edu.phones.domain.Bill;
import edu.phones.exceptions.alreadyExist.BillAlreadyExistsException;

import java.util.List;

public interface BillDao extends AbstractDao<Bill>{

    /** CRUD **/
    Bill add(Bill bill) throws BillAlreadyExistsException;
    Integer remove(Bill bill);
    Integer update(Bill bill);
    Bill getById(Integer id);
    List<Bill> getAll();
}
