package edu.phones.dao;

import edu.phones.domain.City;
import edu.phones.domain.Province;

import java.util.List;

public interface ProvinceDao extends AbstractDao<Province>{

    /** CRUD **/
    Province add(Province province);
    Integer remove(Province province);
    Integer update(Province province);
    Province getById(Integer id);
    List<Province> getAll();
}
