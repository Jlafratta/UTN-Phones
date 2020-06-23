package edu.phones.dao;

import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.exceptions.alreadyExist.ProvinceAlreadyExistsException;

import java.util.List;

public interface ProvinceDao extends AbstractDao<Province>{

    /** CRUD **/
    Province add(Province province) throws ProvinceAlreadyExistsException;
    Integer remove(Province province);
    Integer update(Province province);
    Province getById(Integer id);
    List<Province> getAll();
}
