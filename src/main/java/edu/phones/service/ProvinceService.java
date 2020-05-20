package edu.phones.service;

import edu.phones.dao.ProvinceDao;
import edu.phones.dao.mysql.ProvinceMySQLDao;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.exceptions.CityNotExistException;
import edu.phones.exceptions.ProvinceNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    ProvinceDao provinceDao;

    @Autowired
    public ProvinceService(@Qualifier("provinceMySQLDao")ProvinceDao provinceDao) {
        this.provinceDao = provinceDao;
    }

    public Province createProvince(Province province) {
        return provinceDao.add(province);
    }

    public void remove(Province province) throws ProvinceNotExistException {
        if (provinceDao.remove(province) == 0) {
            throw new ProvinceNotExistException();
        }
    }

    public Province updateProvince(Province province) throws ProvinceNotExistException {
        if (provinceDao.update(province) > 0) {
            return province;
        } else {
            throw new ProvinceNotExistException();
        }
    }

    public Province getProvince(Integer provinceId) {
        return provinceDao.getById(provinceId);
    }

    public List<Province> getAll() {
        return provinceDao.getAll();
    }
}
