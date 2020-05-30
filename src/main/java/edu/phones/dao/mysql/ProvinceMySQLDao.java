package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.ProvinceDao;
import edu.phones.domain.Province;
import edu.phones.exceptions.alreadyExist.ProvinceAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.PrimitiveIterator;

import static edu.phones.dao.mysql.MySQLUtils.*;

// TODO completar los metodos del dao segun corresponda

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
    public Province add(Province province) throws ProvinceAlreadyExistsException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_PROVINCE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, province.getName());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs != null && rs.next()){
                province.setProvinceId(rs.getInt(1));
            }

            return province;

        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new ProvinceAlreadyExistsException();
            }else{
                throw new RuntimeException("Error al crear la nueva provincia", e);
            }
        }
    }

    @Override
    public Integer remove(Province province) {
        return remove(province.getProvinceId());
    }

    @Override
    public Integer update(Province province) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_USER_QUERY);
            ps.setString(1, province.getName());
            ps.setInt(2, province.getProvinceId());

            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar la provincia", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(DELETE_PROVINCE_QUERY);
            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la provincia", e);
        }
    }

    // TODO getById & getAll
    @Override
    public Province getById(Integer id) {
        return null;
    }

    @Override
    public List<Province> getAll() {
        return null;
    }

    private Province createProvince(ResultSet rs) throws SQLException {
        return new Province(rs.getInt("id_province"), rs.getString("province_name"));
    }
}
