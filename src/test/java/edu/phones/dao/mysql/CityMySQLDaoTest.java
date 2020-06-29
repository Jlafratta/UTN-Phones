package edu.phones.dao.mysql;

import edu.phones.dao.ProvinceDao;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.BASE_CITY_QUERY;
import static edu.phones.dao.mysql.MySQLUtils.GET_BY_ID_CITY_QUERY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityMySQLDaoTest {

    @Mock
    Connection connect;
    @Mock
    ProvinceDao provinceDao;
    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    CityMySQLDao cityDao;

    @Before
    public void setUp(){
        initMocks(this);
        this.cityDao = new CityMySQLDao(connect, provinceDao);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddUnsupported(){
        cityDao.add(new City(1, "prefix", "name", null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdateUnsupported(){
        cityDao.update(new City(1, "prefix", "name", null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveByIdUnsupported(){
        cityDao.remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveUnsupported(){
        cityDao.remove(new City(1, "prefix", "name", null));
    }

    @Test
    public void testGetByIdOk() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_CITY_QUERY)).thenReturn(ps);
        doNothing().when(ps).setInt(1, 1);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getInt("id_city")).thenReturn(1);
        when(rs.getString("prefix")).thenReturn("prefix");
        when(rs.getString("city_name")).thenReturn("name");
        when(provinceDao.getById(rs.getInt("id_province"))).thenReturn(new Province(1, "name"));

        City city = cityDao.getById(1);

        assertNotNull(city);
        assertEquals(Integer.valueOf(1), city.getCityId());
        verify(ps, times(1)).executeQuery();
        verify(rs, times(1)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdError() throws SQLException {
        when(connect.prepareStatement(GET_BY_ID_CITY_QUERY)).thenThrow(new SQLException());
        City city = cityDao.getById(1);
    }

    @Test
    public void testGetAllOk() throws SQLException {
        when(connect.prepareStatement(BASE_CITY_QUERY)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("id_city")).thenReturn(1);
        when(rs.getString("prefix")).thenReturn("prefix");
        when(rs.getString("city_name")).thenReturn("name");
        when(provinceDao.getById(rs.getInt("id_province"))).thenReturn(new Province(1, "name"));

        List<City> cities = cityDao.getAll();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        verify(ps, times(1)).executeQuery();
        verify(rs, times(2)).next();
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllError() throws SQLException {
        when(connect.prepareStatement(BASE_CITY_QUERY)).thenThrow(new SQLException());
        List<City> cities = cityDao.getAll();
    }

}
