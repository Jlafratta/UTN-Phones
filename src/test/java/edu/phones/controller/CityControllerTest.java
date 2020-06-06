package edu.phones.controller;

import edu.phones.domain.City;
import edu.phones.exceptions.alreadyExist.CityAlreadyExistException;
import edu.phones.exceptions.notExist.CityNotExistException;
import edu.phones.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {

    CityController cityController;
    @Mock
    CityService cityService;

    @Before
    public void setUp(){
        initMocks(this);
        cityController = new CityController(cityService);
    }

    @Test
    public void testCreateCityOk() throws CityAlreadyExistException {
        City toAdd = new City ("prefix", "name", null);
        City added = new City(1, "prefix", "name", null);
        when(cityService.createCity(toAdd)).thenReturn(added);

        City city = cityController.createCity(toAdd);

        assertEquals(added.getPrefix(), city.getPrefix());
        assertEquals(added.getCityId(), city.getCityId());
        verify(cityService, times(1)).createCity(toAdd);
    }

    @Test
    public void testRemoveCityOk() throws CityNotExistException {
        City toRemove = new City(1, "prefix", "name", null);
        doNothing().when(cityService).remove(toRemove);

        cityController.removeCity(toRemove);

        verify(cityService, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateCityOk() throws CityNotExistException {
        City toUpdate = new City (1,"prefix", "name", null);
        City updated = new City(1, "prefix2", "name2", null);
        when(cityService.updateCity(toUpdate)).thenReturn(updated);

        City city = cityController.updateCity(toUpdate);

        assertEquals(updated.getCityId(), city.getCityId());
        verify(cityService, times(1)).updateCity(toUpdate);
    }

    @Test
    public void testGetCityOk(){
        City city = new City(1, "prefix", "name", null);
        Integer id = 1;
        when(cityService.getCity(id)).thenReturn(city);

        City getted = cityController.getCity(id);

        assertEquals(city.getCityId(), getted.getCityId());
        verify(cityService, times(1)).getCity(id);
    }

    @Test
    public void testGetAllOk(){
        List<City> cities = new ArrayList<>();
        cities.add(new City(1, "prefix", "name", null));
        when(cityService.getAll()).thenReturn(cities);

        List<City> cityList = cityController.getAll();

        assertEquals(cities.size(), cityList.size());
        verify(cityService, times(1)).getAll();
    }
}
