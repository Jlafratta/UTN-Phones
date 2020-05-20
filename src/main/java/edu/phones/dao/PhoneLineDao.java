package edu.phones.dao;

import edu.phones.domain.PhoneLine;

import java.util.List;

public interface PhoneLineDao extends AbstractDao<PhoneLine>{

    /** CRUD **/
    PhoneLine add(PhoneLine line);
    Integer remove(PhoneLine line);
    Integer update(PhoneLine line);
    PhoneLine getById(Integer id);
    List<PhoneLine> getAll();
}
