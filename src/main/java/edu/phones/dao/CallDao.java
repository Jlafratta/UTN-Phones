package edu.phones.dao;

import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.AddCallDto;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;

import java.util.Date;
import java.util.List;

public interface CallDao extends AbstractDao<Call>{

    /** CRUD **/
    Call add(AddCallDto call) throws CallAlreadyExistsException;

    Integer remove(Call call);

    Integer update(Call call);

    Call getById(Integer id);

    List<Call> getAll();

    List<Call> getByOriginUserFilterByDate(User currentUser, Date from, Date to);

    List<Call> getByOriginUserId(Integer id);
}
