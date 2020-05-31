package edu.phones.dao;

import edu.phones.domain.Call;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;

import java.util.List;

public interface CallDao extends AbstractDao<Call>{

    /** CRUD **/
    Call add(Call call) throws CallAlreadyExistsException;
    Integer remove(Call call);
    Integer update(Call call);
    Call getById(Integer id);
    List<Call> getAll();
}
