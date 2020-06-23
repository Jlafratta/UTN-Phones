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
    public City add(City city) throws CityAlreadyExistException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_CITY_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, city.getPrefix());
            ps.setString(2, city.getName());
            ps.setInt(3, city.getProvince().getProvinceId());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs != null && rs.next()){
                city.setCityId(rs.getInt(1));
            }

            return city;

        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new CityAlreadyExistException();
            }else{
                throw new RuntimeException("Error al crear la nueva ciudad", e);
            }
        }
    }

    @Override
    public Integer update(City city) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_CITY_QUERY);
            ps.setString(1, city.getPrefix());
            ps.setString(2, city.getName());
            ps.setInt(3, city.getProvince().getProvinceId());
            ps.setInt(4, city.getCityId());


            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar la ciudad", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(DELETE_CITY_QUERY);
            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la ciudad", e);
        }
    }

    @Override
    public Integer remove(City city) {
        return remove(city.getCityId());
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
