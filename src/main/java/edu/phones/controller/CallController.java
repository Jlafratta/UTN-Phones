package edu.phones.controller;

import edu.phones.domain.Call;
import edu.phones.exceptions.CallNotExistException;
import edu.phones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CallController {

    CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    public Call createCall(Call call){
        return callService.createCall(call);
    }

    public void remove(Call call) throws CallNotExistException {
        callService.remove(call);
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

}
