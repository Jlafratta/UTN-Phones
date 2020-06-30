package edu.phones.dao;

import edu.phones.domain.PhoneLine;
import edu.phones.domain.User;
import edu.phones.dto.LineRequestDto;
import edu.phones.exceptions.alreadyExist.PhoneLineAlreadyExistsException;

import java.util.List;

public interface PhoneLineDao extends AbstractDao<PhoneLine>{

    /** CRUD **/
    PhoneLine add(PhoneLine line) throws PhoneLineAlreadyExistsException;

    Integer remove(PhoneLine line);

    Integer update(PhoneLine line);

    PhoneLine getById(Integer id);

    List<PhoneLine> getAll();

    List<LineRequestDto> getTopTen(User currentUser);
}
