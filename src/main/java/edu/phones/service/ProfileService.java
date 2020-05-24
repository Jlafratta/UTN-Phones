package edu.phones.service;

import edu.phones.dao.UserProfileDao;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    UserProfileDao profileDao;

    @Autowired
    public ProfileService(@Qualifier("profileMySQLDao") UserProfileDao profileDao) {
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
