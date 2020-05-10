package edu.phones.dao.mysql;

import edu.phones.dao.UserDao;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**     Los metodos que no se usan tiran un
 *  throw new UnsupportedOperationException()
 * **/

import static edu.phones.dao.mysql.MySQLUtils.*;

public class UserMySQLDao implements UserDao {

    Connection connect;

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
    private User createUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("id_user"), rs.getString("username"), rs.getString("password"),
                        (new UserProfile(rs.getInt("id_profile"), rs.getString("name"), rs.getString("lastname"), rs.getInt("dni") )),
                         new City(rs.getInt("id_city"),rs.getString("prefix"), rs.getString("city_name"),
                            (new Province(rs.getInt("id_province"), rs.getString("province_name"))) ) );
        return user;
    }

    @Override
    public User add(User value) {
        return null;
    }

    @Override
    public Integer update(User value) {
        return null;
    }

    @Override
    public Integer remove(Integer id) {
        return null;
    }

    @Override
    public Integer remove(User value) {
        return null;
    }

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

}
