package edu.phones.controller;

import edu.phones.domain.PhoneLine;
import edu.phones.exceptions.notExist.PhoneLineNotExistException;
import edu.phones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PhoneLineController {

    PhoneLineService lineService;

    @Autowired
    public PhoneLineController(PhoneLineService lineService) {
        this.lineService = lineService;
    }

    /** CRUD **/
    public PhoneLine createLine(PhoneLine line){
        return lineService.createLine(line);
    }

    public void removeLine(PhoneLine line) throws PhoneLineNotExistException {
        lineService.removeLine(line);
    }

    public PhoneLine updateLine(PhoneLine line) throws PhoneLineNotExistException {
        return lineService.updateLine(line);
    }

    public PhoneLine getLine(Integer id){
        return lineService.getLine(id);
    }

    public List<PhoneLine> getAll(){
        return lineService.getAll();
    }
}
