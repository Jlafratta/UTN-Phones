package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.UserProfileDao;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("profileMySQLDao")
public class UserProfileMySQLDao implements UserProfileDao {

    final Connection connect;

    @Autowired
    public UserProfileMySQLDao(Connection connect) {
        this.connect = connect;
    }

    /** CRUD **/
    @Override
    public UserProfile add(UserProfile profile) throws ProfileAlreadyExistException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_PROFILE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getLastname());
            ps.setInt(3, profile.getDni());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs != null && rs.next()){
                profile.setProfileId(rs.getInt(1));
            }

            return profile;

        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new ProfileAlreadyExistException();
            }else{
                throw new RuntimeException("Error al crear el nuevo perfil", e);
            }
        }
    }

    @Override
    public Integer update(UserProfile profile) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_PROFILE_QUERY);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getLastname());
            ps.setInt(3, profile.getDni());
            ps.setInt(4, profile.getProfileId());

            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar el perfil", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(UserProfile value) {
        throw new UnsupportedOperationException();
    }

    // TODO getById UserProfile
    @Override
    public UserProfile getById(Integer id) {
        return null;
    }

    @Override
    public List<UserProfile> getAll() {
        throw new UnsupportedOperationException();
    }

}
