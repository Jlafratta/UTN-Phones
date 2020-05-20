package edu.phones.dao;

import edu.phones.domain.UserProfile;

import java.util.List;

public interface ProfileDao extends AbstractDao<UserProfile> {

    /** CRUD **/
    UserProfile add(UserProfile profile);
    Integer remove(UserProfile profile);
    Integer update(UserProfile profile);
    UserProfile getById(Integer id);
    List<UserProfile> getAll();
}
