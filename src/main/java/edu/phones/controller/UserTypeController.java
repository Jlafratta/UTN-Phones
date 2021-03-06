package edu.phones.controller;

import edu.phones.domain.UserType;
import edu.phones.exceptions.alreadyExist.TypeAlreadyExistsException;
import edu.phones.exceptions.notExist.TypeNotExistException;
import edu.phones.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserTypeController {

    UserTypeService typeService;

    @Autowired
    public UserTypeController(UserTypeService typeService) {
        this.typeService = typeService;
    }

    /** CRUD **/
    public UserType createType(UserType type) throws TypeAlreadyExistsException {
        return typeService.createType(type);
    }

    public void removeType(UserType type) throws TypeNotExistException {
        typeService.remove(type);
    }

    public UserType updateType(UserType type) throws TypeNotExistException {
        return typeService.updateType(type);
    }

    public UserType getType(Integer typeId){
        return typeService.getType(typeId);
    }

    public List<UserType> getAll(){
        return typeService.getAll();
    }
}
