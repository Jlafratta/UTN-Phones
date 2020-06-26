package edu.phones.controller;

import edu.phones.domain.Tariff;
import edu.phones.exceptions.alreadyExist.TarriffAlreadyExistsException;
import edu.phones.exceptions.notExist.TariffNotExistException;
import edu.phones.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TariffController {

    TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    public Tariff createTariff(Tariff tariff) throws TarriffAlreadyExistsException {
        return tariffService.createTariff(tariff);
    }

    public void remove(Tariff tariff) throws TariffNotExistException {
        tariffService.remove(tariff);
    }

    public Tariff updateTariff(Tariff tariff) throws TariffNotExistException {
        return tariffService.updateTariff(tariff);
    }

    public Tariff getTariff(Integer id){
        return tariffService.getTariff(id);
    }

    public List<Tariff>getAll(){
        return tariffService.getAll();
    }

}
