package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.CityDao;
import edu.phones.domain.City;
import edu.phones.exceptions.alreadyExist.CityAlreadyExistException;
import edu.phones.exceptions.notExist.CityNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;

// TODO completar los metodos del dao segun corresponda

@Repository
@Qualifier("cityMySQLDao")
public class CityMySQLDao implements CityDao {

    final Connection connect;

    @Autowired
    public CityMySQLDao(Connection connect) {
        this.connect = connect;
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
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Integer remove(City city) {
        return null;
    }

    @Override
    public City getById(Integer id) {
        return null;
    }

    @Override
    public List<City> getAll() {
        return null;
    }
}
