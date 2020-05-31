package edu.phones.service;

import edu.phones.dao.mysql.ProvinceMySQLDao;
import edu.phones.domain.Province;
import edu.phones.exceptions.alreadyExist.ProvinceAlreadyExistsException;
import edu.phones.exceptions.notExist.ProvinceNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProvinceServiceTest {

    ProvinceService provinceService;
    @Mock
    ProvinceMySQLDao provinceDao;

    @Before
    public void setUp(){
        initMocks(this);
        provinceService = new ProvinceService(provinceDao);
    }

    @Test
    public void testCreateProvinceOk() throws ProvinceAlreadyExistsException {
        Province toAdd = new Province("name");
        Province added = new Province(1, "name");
        when(provinceDao.add(toAdd)).thenReturn(added);
        provinceService.createProvince(toAdd);

        assertEquals(toAdd.getName(), added.getName());
        verify(provinceDao, times(1)).add(toAdd);
    }

    @Test
    public void testRemoveProvinceOk() throws ProvinceNotExistException {
        Province toRemove = new Province(1, "name");
        when(provinceDao.remove(toRemove)).thenReturn(2);
        provinceService.remove(toRemove);

        verify(provinceDao, times(1)).remove(toRemove);
    }

    @Test(expected = ProvinceNotExistException.class)
    public void testRemoveProvinceNotExist() throws ProvinceNotExistException {
        Province toRemove = new Province(1, "name");
        when(provinceDao.remove(toRemove)).thenReturn(0);
        provinceService.remove(toRemove);
        verify(provinceDao, times(1)).remove(toRemove);
    }

    @Test
    public void testUpdateProvinceOk() throws ProvinceNotExistException {
        Province toUpdate = new Province(1, "name");
        when(provinceDao.update(toUpdate)).thenReturn(1);
        provinceService.updateProvince(toUpdate);

        verify(provinceDao, times(1)).update(toUpdate);
    }

    @Test(expected = ProvinceNotExistException.class)
    public void testUpdateProvinceNotExist() throws ProvinceNotExistException {
        Province toUpdate = new Province(1, "name");
        when(provinceDao.update(toUpdate)).thenReturn(0);
        provinceService.updateProvince(toUpdate);

        verify(provinceDao, times(1)).update(toUpdate);
    }

    @Test
    public void testGetProvinceOk(){
        Province prov = new Province(1, "name");
        Integer id = 1;
        when(provinceDao.getById(id)).thenReturn(prov);
        provinceService.getProvince(id);

        assertEquals(id, prov.getProvinceId());
        verify(provinceDao, times(1)).getById(id);
    }

    @Test
    public void testGetAllOk(){
        List<Province> provList = new ArrayList<>();
        provList.add(new Province(1, "name"));
        when(provinceDao.getAll()).thenReturn(provList);
        provinceService.getAll();

        verify(provinceDao, times(1)).getAll();
    }

}
