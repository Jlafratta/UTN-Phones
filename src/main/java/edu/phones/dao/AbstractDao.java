package edu.phones.dao;

import edu.phones.exceptions.alreadyExist.CityAlreadyExistException;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.CityNotExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;

import java.util.List;

public interface AbstractDao<T> {

    T add(T value) throws UserAlreadyExistsException, CityAlreadyExistException, ProfileAlreadyExistException;

    Integer update(T value);

    Integer remove(Integer id);

    Integer remove(T value);

    T getById(Integer id);

    List<T> getAll();
}
