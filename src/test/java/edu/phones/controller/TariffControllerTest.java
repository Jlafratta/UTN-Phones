package edu.phones.controller;

import edu.phones.domain.Tariff;
import edu.phones.exceptions.alreadyExist.TarriffAlreadyExistsException;
import edu.phones.exceptions.notExist.TariffNotExistException;
import edu.phones.service.TariffService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TariffControllerTest {

    @Mock
    TariffService tariffService;

    TariffController tariffController;

    @Before
    public void setUp(){
        initMocks(this);
        this.tariffController = new TariffController(tariffService);
    }

    @Test
    public void testCreateTariffOk() throws TarriffAlreadyExistsException {
        Tariff toAdd = new Tariff(22311, 1.0, 1.0);

        when(tariffService.createTariff(toAdd)).thenReturn(toAdd);

        Tariff added = tariffController.createTariff(toAdd);

        assertNotNull(added);
        assertEquals(toAdd.getKey(), added.getKey());
        verify(tariffService, times(1)).createTariff(toAdd);
    }

    @Test
    public void testRemoveTariffOk() throws TariffNotExistException {
        Tariff toRemove =  new Tariff(22311, 1.0, 1.0);
        doNothing().when(tariffService).remove(toRemove);
        tariffController.remove(toRemove);
        verify(tariffService, times(1)).remove(toRemove);
    }

    @Test
    public void updateTariffOk() throws TariffNotExistException {
        Tariff toUpdate =  new Tariff(22311, 1.0, 1.0);

        when(tariffService.updateTariff(toUpdate)).thenReturn( new Tariff(22311, 2.0, 2.0));

        Tariff updated = tariffController.updateTariff(toUpdate);

        assertNotNull(updated);
        assertEquals(toUpdate.getKey(), updated.getKey());
        verify(tariffService, times(1)).updateTariff(toUpdate);
    }

    @Test
    public void testGetTariffOk(){
        Integer id = 22311;

        when(tariffService.getTariff(id)).thenReturn(new Tariff(22311, 1.0, 1.0));

        Tariff getted = tariffController.getTariff(id);

        assertNotNull(getted);
        assertEquals(id, getted.getKey());
        verify(tariffService, times(1)).getTariff(id);
    }

    @Test
    public void testGetAllTariffsOk(){
        List<Tariff> tariffList = new ArrayList<>();
        tariffList.add( new Tariff(22311, 1.0, 1.0));
        when(tariffService.getAll()).thenReturn(tariffList);

        List<Tariff> tariffsGetted = tariffController.getAll();

        assertNotNull(tariffsGetted);
        assertEquals(tariffList, tariffsGetted);
        verify(tariffService, times(1)).getAll();
    }
}
