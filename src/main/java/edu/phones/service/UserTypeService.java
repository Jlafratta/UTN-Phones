package edu.phones.service;

import edu.phones.dao.UserTypeDao;
import edu.phones.domain.UserType;
import edu.phones.exceptions.notExist.TypeNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    UserTypeDao typeDao;

    @Autowired
    public UserTypeService(@Qualifier("userTypeMySQLDao") UserTypeDao typeDao) {
        this.typeDao = typeDao;
    }

    /** CRUD **/
    public UserType createType(UserType type) {
        return typeDao.add(type);
    }

    public void remove(UserType type) throws TypeNotExistException {
        if (typeDao.remove(type) == 0) {
            throw new TypeNotExistException();
        }
    }

    public UserType updateType(UserType type) throws TypeNotExistException {
        if (typeDao.update(type) > 0) {
            return type;
        } else {
            throw new TypeNotExistException();
        }
    }

    public UserType getType(Integer typeId) {
        return typeDao.getById(typeId);
    }

    public List<UserType> getAll() {
        return typeDao.getAll();
    }
}
