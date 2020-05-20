package edu.phones.service;

import edu.phones.dao.ProfileDao;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.ProfileNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    ProfileDao profileDao;

    @Autowired
    public ProfileService(@Qualifier("profileMysqlDao")ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public UserProfile createProfile(UserProfile newProfile) {
        return profileDao.add(newProfile);
    }

    public UserProfile getProfile(Integer id) {
        return profileDao.getById(id);
    }

    public UserProfile updateProfile(UserProfile toModify) throws ProfileNotExistException {
        if(profileDao.update(toModify) > 0){
            return toModify;
        }else {
            throw new ProfileNotExistException();
        }
    }
}
