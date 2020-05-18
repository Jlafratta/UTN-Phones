package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.UserDao;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**     Los metodos que no se usan tiran un
 *  throw new UnsupportedOperationException()
 * **/

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("userMySQLDao")
public class UserMySQLDao implements UserDao {

    Connection connect;

    @Autowired
    public UserMySQLDao(Connection connect) {
        this.connect = connect;
    }

    @Override
    public User getByUsername(String username, String password) {
        try{
            PreparedStatement ps = connect.prepareStatement(GET_BY_USERNAME_USER_QUERY);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            User user = null;
            if(rs.next()){
                user = createUser(rs);
            }
            rs.close();
            ps.close();
            return user;

        }catch(SQLException e){
            throw new RuntimeException("Error al obtener datos del usuario");
        }
    }

    // Crea el usuario con la data traida del result set y lo devuelve
    //TODO implementar dao's externos en vez de hacer los new de cada objeto
    private User createUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("id_user"), rs.getString("username"), rs.getString("password"),
                        (new UserProfile(rs.getInt("id_profile"), rs.getString("name"), rs.getString("lastname"), rs.getInt("dni") )),
                         new City(rs.getInt("id_city"),rs.getString("prefix"), rs.getString("city_name"),
                            (new Province(rs.getInt("id_province"), rs.getString("province_name"))) ) );
        return user;
    }

    @Override
    public User add(User value) throws UserAlreadyExistsException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, value.getUsername());
            ps.setString(2, value.getPassword());
            ps.setInt(3, value.getUserProfile().getProfileId());
            ps.setInt(4, value.getCity().getCityId());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs != null && rs.next()){
                value.setUserId(rs.getInt(1));
            }

            return value;

        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new UserAlreadyExistsException();
            }else{
                throw new RuntimeException("Error al crear el nuevo usuario", e);
            }
        }
    }

    @Override
    public Integer update(User value) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_USER_QUERY);
            ps.setString(1, value.getUsername());
            ps.setString(2, value.getPassword());
            ps.setInt(3, value.getUserProfile().getProfileId());
            ps.setInt(4, value.getCity().getCityId());
            ps.setInt(5, value.getUserId());

            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar el usuario", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(DELETE_USER_QUERY);
            ps.setInt(1, id);
            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el usuario", e);
        }
    }

    @Override
    public Integer remove(User value) {
        return remove(value.getUserId());
    }

    @Override
    public User getById(Integer id) {

        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_USER_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            User u = null;
            if(rs.next()){
                u = createUser(rs);
            }
            rs.close();
            ps.close();

            return u;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por id", e);
        }

    }

    @Override
    public List<User> getAll() {

        try {
            PreparedStatement ps = connect.prepareStatement(BASE_USER_QUERY);
            ResultSet rs = ps.executeQuery();

            List<User> userList = new ArrayList<>();
            while(rs.next()){
                userList.add(createUser(rs));
            }

            return userList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todos los usuarios", e);
        }
    }

}
