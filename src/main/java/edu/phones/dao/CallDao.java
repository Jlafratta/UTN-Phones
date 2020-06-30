package edu.phones.dao;

import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.AddCallDto;
import edu.phones.dto.CallRequestDto;
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

    List<CallRequestDto> getByOriginUserFilterByDate(User currentUser, Date from, Date to,Integer page, Integer offset);

    List<CallRequestDto> getByOriginUserId(Integer id,Integer page , Integer offset);

    List<Call> getByOriginUserIdAll(Integer userId);
}
