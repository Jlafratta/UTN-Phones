package edu.phones.dao;

import edu.phones.domain.Tariff;
import edu.phones.domain.User;
import edu.phones.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface TariffDao extends AbstractDao<Tariff>{

    /** CRUD **/
    Tariff add(Tariff tariff);
    Integer remove(Tariff tariff);
    Integer update(Tariff tariff);
    Tariff getById(Integer id);
    List<Tariff> getAll();
}
