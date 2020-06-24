package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.CityDao;
import edu.phones.dao.ProvinceDao;
import edu.phones.domain.City;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.CityAlreadyExistException;
import edu.phones.exceptions.notExist.CityNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("cityMySQLDao")
public class CityMySQLDao implements CityDao {

    final Connection connect;
    ProvinceDao provinceDao;

    @Autowired
    public CityMySQLDao(Connection connect, @Qualifier("provinceMySQLDao") ProvinceDao provinceDao) {
        this.connect = connect;
        this.provinceDao = provinceDao;
    }

    /** CRUD **/
    @Override
    public City add(City city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer update(City city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(City city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public City getById(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_CITY_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            City city = null;
            if(rs.next()){
                city = createCity(rs);
            }
            rs.close();
            ps.close();

            return city;

        } catch (SQLException e) {

            throw new RuntimeException("Error al buscar por id", e);
        }
    }

    @Override
    public List<City> getAll() {
        try {
            PreparedStatement ps = connect.prepareStatement(BASE_CITY_QUERY);
            ResultSet rs = ps.executeQuery();

            List<City> cityList = new ArrayList<>();
            while(rs.next()){
                cityList.add(createCity(rs));
            }

            return cityList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las ciudades", e);
        }
    }

    private City createCity(ResultSet rs) throws SQLException {
        return new City(rs.getInt("id_city"), rs.getString("prefix"), rs.getString("city_name"),
                    provinceDao.getById(rs.getInt("id_province")));
    }
}
