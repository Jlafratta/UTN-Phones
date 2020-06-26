package edu.phones.dao;

import edu.phones.domain.Bill;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.BillAlreadyExistsException;

import java.util.Date;
import java.util.List;

public interface BillDao extends AbstractDao<Bill>{

    /** CRUD **/
    Bill add(Bill bill);

    Integer remove(Bill bill);

    Integer update(Bill bill);

    Bill getById(Integer id);

    List<Bill> getAll();

    List<Bill> getByUserFilterByDate(User currentUser, Date dFrom, Date dTo);

    List<Bill> getByUser(User currentUser);
}
