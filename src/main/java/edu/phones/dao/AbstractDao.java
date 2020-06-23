package edu.phones.dao;

import edu.phones.exceptions.alreadyExist.*;
import edu.phones.exceptions.notExist.CityNotExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;

import java.util.List;

public interface AbstractDao<T> {

    T add(T value) throws UserAlreadyExistsException, CityAlreadyExistException, ProfileAlreadyExistException, TypeAlreadyExistsException, ProvinceAlreadyExistsException, PhoneLineAlreadyExistsException, BillAlreadyExistsException, CallAlreadyExistsException, TarriffAlreadyExistsException;

    Integer update(T value);

    Integer remove(Integer id);

    Integer remove(T value);

    T getById(Integer id);

    List<T> getAll();
}
