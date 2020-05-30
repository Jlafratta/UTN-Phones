package edu.phones.dao;

import edu.phones.domain.UserProfile;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;

import java.util.List;

public interface UserProfileDao extends AbstractDao<UserProfile> {

    /** CRUD **/
    UserProfile add(UserProfile profile) throws ProfileAlreadyExistException;
    Integer update(UserProfile profile);
    UserProfile getById(Integer id);
}
