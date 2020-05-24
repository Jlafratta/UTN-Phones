package edu.phones.controller;

import edu.phones.domain.City;
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
    public void testCreateCityOk(){
        City toAdd = new City ("prefix", "name", null);
        City added = new City(1, "prefix", "name", null);
        when(cityService.createCity(toAdd)).thenReturn(added);
        cityController.createCity(toAdd);

        assertEquals(toAdd.getPrefix(), added.getPrefix());
        assertEquals(toAdd.getName(), added.getName());
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
        cityController.updateCity(toUpdate);

        assertEquals(toUpdate.getCityId(), updated.getCityId());
        verify(cityService, times(1)).updateCity(toUpdate);
    }

    @Test
    public void testGetCityOk(){
        City city = new City(1, "prefix", "name", null);
        Integer id = 1;
        when(cityService.getCity(id)).thenReturn(city);
        cityController.getCity(id);

        assertEquals(id, city.getCityId());
        verify(cityService, times(1)).getCity(id);
    }

    @Test
    public void testGetAllOk(){
        List<City> cities = new ArrayList<>();
        cities.add(new City(1, "prefix", "name", null));
        when(cityService.getAll()).thenReturn(cities);
        cityController.getAll();

        verify(cityService, times(1)).getAll();
    }
}
