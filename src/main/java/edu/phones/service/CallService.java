package edu.phones.service;

import edu.phones.dao.CallDao;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.AddCallDto;
import edu.phones.dto.CallRequestDto;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;
import edu.phones.exceptions.notExist.CallNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CallService {

    CallDao callDao;

    @Autowired
    public CallService(@Qualifier("callMySQLDao") CallDao callDao) {
        this.callDao = callDao;
    }

    public Call createCall(AddCallDto call) throws CallAlreadyExistsException {
        return callDao.add(call);
    }

    public void removeCall(Call call) throws CallNotExistException {
        if(callDao.remove(call) == 0){
            throw new CallNotExistException();
        }
    }

    public Call updateCall(Call call) throws CallNotExistException {
        if(callDao.update(call) > 0){
            return call;
        }else{
            throw new CallNotExistException();
        }
    }

    public Call getCall(Integer id) {
        return callDao.getById(id);
    }

    public List<Call> getAll() {
        return callDao.getAll();
    }

    public List<CallRequestDto> getByOriginUserFilterByDate(User currentUser, Date from, Date to,Integer page, Integer cant) {
        page = cant * (page-1);
        return callDao.getByOriginUserFilterByDate(currentUser, from, to, page, cant);
    }

    public List<CallRequestDto> getByOriginUserId(Integer id,Integer page, Integer cant) {
        page = cant * (page-1);
        return callDao.getByOriginUserId(id,page,cant);
    }

    public List<Call> getByOriginUserIdAll(Integer userId) {
        return callDao.getByOriginUserIdAll(userId);
    }
}
