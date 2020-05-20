package edu.phones.service;

import edu.phones.dao.mysql.ProfileMySQLDao;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.ProfileNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    ProfileMySQLDao profileMySQLDao;

    @Autowired
    public ProfileService(ProfileMySQLDao profileMySQLDao) {
        this.profileMySQLDao = profileMySQLDao;
    }

    public UserProfile newProfile(UserProfile newProfile) {
        return profileMySQLDao.add(newProfile);
    }

    public UserProfile getById(Integer id) {
        return profileMySQLDao.getById(id);
    }

    public UserProfile modifyProfile(UserProfile toModify) throws ProfileNotExistException {
        if(profileMySQLDao.update(toModify) > 0){
            return toModify;
        }else {
            throw new ProfileNotExistException();
        }
    }
}
