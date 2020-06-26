package edu.phones.dao;

import edu.phones.domain.Province;
import edu.phones.domain.UserType;
import edu.phones.exceptions.alreadyExist.TypeAlreadyExistsException;

import java.util.List;

public interface UserTypeDao extends AbstractDao<UserType>{

    /** CRUD **/
    UserType add(UserType type) throws TypeAlreadyExistsException;

    Integer remove(UserType type);

    Integer update(UserType type);

    UserType getById(Integer id);

    List<UserType> getAll();
}
