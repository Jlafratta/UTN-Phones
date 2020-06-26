package edu.phones.service;

import edu.phones.dao.mysql.UserProfileMySQLDao;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.exceptions.notExist.TypeNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserProfileServiceTest {

    UserProfileService profileService;
    @Mock
    UserProfileMySQLDao profileDao;

    @Before
    public void setUp(){
        initMocks(this);
        profileService = new UserProfileService(profileDao);
    }

    @Test
    public void testCreateProfileOk() throws ProfileAlreadyExistException {
        UserProfile toAdd = new UserProfile("name", "lastname", 12345678);
        UserProfile added = new UserProfile(1, "name", "lastname", 12345678);
        when(profileDao.add(toAdd)).thenReturn(added);

        UserProfile profile = profileService.createProfile(toAdd);

        assertEquals(added.getProfileId(), profile.getProfileId());
        verify(profileDao, times(1)).add(toAdd);
    }

    @Test
    public void testUpdateProfileOk() throws ProfileNotExistException {
        UserProfile toUpdate = new UserProfile(1, "name", "lastname", 12345678);
        when(profileDao.update(toUpdate)).thenReturn(1);

        UserProfile profile = profileService.updateProfile(toUpdate);

        assertEquals(toUpdate.getProfileId(), profile.getProfileId());
        verify(profileDao, times(1)).update(toUpdate);
    }

    @Test(expected = ProfileNotExistException.class)
    public void testUpdateNotExist() throws ProfileNotExistException {
        UserProfile toUpdate = new UserProfile(1, "name", "lastname", 12345678);
        when(profileDao.update(toUpdate)).thenReturn(0);
        profileService.updateProfile(toUpdate);

        verify(profileDao, times(1)).update(toUpdate);
    }

    @Test
    public void testGetProfileOk(){
        UserProfile profile = new UserProfile(1, "name", "lastname", 12345678);
        Integer id = 1;
        when(profileDao.getById(id)).thenReturn(profile);

        UserProfile getted = profileService.getProfile(id);

        assertEquals(profile.getProfileId(), getted.getProfileId());
        verify(profileDao, times(1)).getById(id);
    }
}
