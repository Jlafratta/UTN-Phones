package edu.phones.service;

import edu.phones.dao.mysql.CityMySQLDao;
import edu.phones.domain.City;
import edu.phones.exceptions.notExist.CityNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityServiceTest {

    CityService cityService;
    @Mock
    CityMySQLDao cityDao;

    @Before
    public void setUp(){
        initMocks(this);
        cityService = new CityService(cityDao);
    }

    @Test
    public void testCreateCityOk(){
        City toAdd = new City("prefix", "name", null);
        City added = new City(1, "prefix", "name", null);
        when(cityDao.add(toAdd)).thenReturn(added);
        cityService.createCity(toAdd);

        assertEquals(toAdd.getPrefix(), added.getPrefix());
        assertEquals(toAdd.getName(), added.getName());
        verify(cityDao, times(1)).add(toAdd);
    }

    @Test
    public void testRemoveCityOk() throws CityNotExistException {
        City toRemove = new City(1, "prefix", "name", null);
        when(cityDao.remove(toRemove)).thenReturn(1);
        cityService.remove(toRemove);

        verify(cityDao, times(1)).remove(toRemove);
    }

    @Test(expected = CityNotExistException.class)
    public void testRemoveCityNotExist() throws CityNotExistException {
        City toRemove = new City(1, "prefix", "name", null);
        when(cityDao.remove(toRemove)).thenReturn(0);
        cityService.remove(toRemove);

        verify(cityDao, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateCity() throws CityNotExistException {
        City toUpdate = new City(1, "prefix", "name", null);
        when(cityDao.update(toUpdate)).thenReturn(2);
        cityService.updateCity(toUpdate);

        verify(cityDao, times(1)).update(toUpdate);
    }

    @Test(expected = CityNotExistException.class)
    public void testUpdateCityNotExist() throws CityNotExistException {
        City toUpdate = new City(1, "prefix", "name", null);
        when(cityDao.update(toUpdate)).thenReturn(0);
        cityService.updateCity(toUpdate);

        verify(cityDao, times(1));
    }

    @Test
    public void testGetCity(){
        City city = new City(1, "prefix", "name", null);
        Integer id = 1;
        when(cityDao.getById(id)).thenReturn(city);
        cityService.getCity(id);

        assertEquals(id, city.getCityId());
        verify(cityDao, times(1)).getById(id);
    }

    @Test
    public void testGetAllOk(){
        List<City> cities = new ArrayList<>();
        cities.add(new City(1, "prefix", "name", null));
        when(cityDao.getAll()).thenReturn(cities);
        cityService.getAll();

        verify(cityDao, times(1)).getAll();
    }
}
