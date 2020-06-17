package edu.phones.controller;

import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.AddCallDto;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;
import edu.phones.exceptions.notExist.CallNotExistException;
import edu.phones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class CallController {

    CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    public Call createCall(AddCallDto call) throws CallAlreadyExistsException {
        return callService.createCall(call);
    }

    public void removeCall(Call call) throws CallNotExistException {
        callService.removeCall(call);
    }

    public Call updateCall(Call call) throws CallNotExistException {
        return callService.updateCall(call);
    }

    public Call getCall(Integer id){
        return callService.getCall(id);
    }

    public List<Call> getAll(){
        return callService.getAll();
    }

    public List<Call> getByOriginUserFilterByDate(User currentUser, Date from, Date to) {
        return callService.getByOriginUserFilterByDate(currentUser, from, to);
    }

    public List<Call> getByOriginUser(User currentUser) {
        return callService.getByOriginUser(currentUser);
    }
}
