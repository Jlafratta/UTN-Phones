package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.UserTypeDao;
import edu.phones.domain.User;
import edu.phones.domain.UserType;
import edu.phones.exceptions.alreadyExist.TypeAlreadyExistsException;
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

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("userTypeMySQLDao")
public class UserTypeMySQLDao implements UserTypeDao {

    final Connection connect;

    @Autowired
    public UserTypeMySQLDao(Connection connect) {
        this.connect = connect;
    }

    @Override
    public UserType add(UserType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(UserType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer update(UserType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserType getById(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_TYPE_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            UserType type = null;
            if(rs.next()){
                type = createType(rs);
            }
            rs.close();
            ps.close();

            return type;

        } catch (SQLException e) {

            throw new RuntimeException("Error al buscar por id", e);
        }
    }

    @Override
    public List<UserType> getAll() {
        try {
            PreparedStatement ps = connect.prepareStatement(BASE_TYPE_QUERY);
            ResultSet rs = ps.executeQuery();

            List<UserType> typeList = new ArrayList<>();
            while(rs.next()){
                typeList.add(createType(rs));
            }

            return typeList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todos los tipos", e);
        }
    }

    private UserType createType(ResultSet rs) throws SQLException {
        return new UserType(rs.getInt("id_type"), rs.getString("type_name"));
    }
}
