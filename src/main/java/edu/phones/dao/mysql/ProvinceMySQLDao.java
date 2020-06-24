package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.ProvinceDao;
import edu.phones.domain.Province;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.ProvinceAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("provinceMySQLDao")
public class ProvinceMySQLDao implements ProvinceDao {

    final Connection connect;

    @Autowired
    public ProvinceMySQLDao(Connection connect) {
        this.connect = connect;
    }

    /** CRUD **/
    @Override
    public Province add(Province province) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(Province province) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer update(Province province) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Province getById(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_PROVINCE_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Province prov = null;
            if(rs.next()){
                prov = createProvince(rs);
            }
            rs.close();
            ps.close();

            return prov;

        } catch (SQLException e) {

            throw new RuntimeException("Error al buscar por id", e);
        }
    }

    @Override
    public List<Province> getAll() {
        try {
            PreparedStatement ps = connect.prepareStatement(BASE_PROVINCE_QUERY);
            ResultSet rs = ps.executeQuery();

            List<Province> provList = new ArrayList<>();
            while(rs.next()){
                provList.add(createProvince(rs));
            }

            return provList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las provincias", e);
        }
    }

    private Province createProvince(ResultSet rs) throws SQLException {
        return new Province(rs.getInt("id_province"), rs.getString("province_name"));
    }
}
