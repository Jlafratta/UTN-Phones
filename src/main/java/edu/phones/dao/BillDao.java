package edu.phones.dao;

import edu.phones.domain.Bill;

import java.util.List;

public interface BillDao extends AbstractDao<Bill>{

    /** CRUD **/
    Bill add(Bill bill);
    Integer remove(Bill bill);
    Integer update(Bill bill);
    Bill getById(Integer id);
    List<Bill> getAll();
}
