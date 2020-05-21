package edu.phones.service;

import edu.phones.dao.TariffDao;
import edu.phones.domain.Tariff;
import edu.phones.exceptions.notExist.TariffNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {

    TariffDao tariffDao;

    @Autowired
    public TariffService(@Qualifier("tariffMySQLDao")TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    public Tariff createTariff(Tariff tariff) {
        return tariffDao.add(tariff);
    }

    public void remove(Tariff tariff) throws TariffNotExistException {
        if(tariffDao.remove(tariff) == 0){
            throw new TariffNotExistException();
        }
    }

    public Tariff updateTariff(Tariff tariff) throws TariffNotExistException {
        if(tariffDao.update(tariff) > 0 ){
            return tariff;
        }else {
            throw new TariffNotExistException();
        }
    }

    public Tariff getTariff(Integer id) {
        return tariffDao.getById(id);
    }

    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }
}
