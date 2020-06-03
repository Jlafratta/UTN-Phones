package edu.phones.dao;

import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.CallRequestDto;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;

import java.util.Date;
import java.util.List;

public interface CallDao extends AbstractDao<Call>{

    /** CRUD **/
    Call add(Call call) throws CallAlreadyExistsException;
    Integer remove(Call call);
    Integer update(Call call);
    Call getById(Integer id);
    List<Call> getAll();

    List<Call> getByOriginUserFilterByDate(User currentUser, Date from, Date to);

    List<Call> getByOriginUser(User currentUser);

    CallRequestDto getDurationByMonth(User currentUser, String date);
}
