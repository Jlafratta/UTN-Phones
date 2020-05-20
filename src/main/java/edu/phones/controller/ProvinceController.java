package edu.phones.controller;

import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.exceptions.CityNotExistException;
import edu.phones.exceptions.ProvinceNotExistException;
import edu.phones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProvinceController {

    ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }
    
    /** CRUD **/
    public Province createProvince(Province province){
        return provinceService.createProvince(province);
    }

    public void removeCity(Province province) throws ProvinceNotExistException {
        provinceService.remove(province);
    }

    public void updateCity(Province province) throws ProvinceNotExistException {
        provinceService.updateCity(province);
    }

    public Province getCity(Integer provinceId){
        return provinceService.getCity(provinceId);
    }

    public List<Province> getAll(){
        return provinceService.getAll();
    }
}
