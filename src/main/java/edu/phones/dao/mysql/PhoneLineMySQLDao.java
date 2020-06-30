package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.PhoneLineDao;
import edu.phones.dao.UserDao;
import edu.phones.dao.UserTypeDao;
import edu.phones.domain.PhoneLine;
import edu.phones.domain.User;
import edu.phones.dto.LineRequestDto;
import edu.phones.exceptions.alreadyExist.PhoneLineAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("phoneLineMySQLDao")
public class PhoneLineMySQLDao implements PhoneLineDao {

    final Connection connect;
    UserDao userDao;
    UserTypeDao typeDao;

    @Autowired
    public PhoneLineMySQLDao(Connection connect, @Qualifier("userMySQLDao")UserDao userDao, @Qualifier("userTypeMySQLDao")UserTypeDao typeDao) {
        this.connect = connect;
        this.userDao = userDao;
        this.typeDao = typeDao;
    }

    @Override
    public List<LineRequestDto> getTopTen(User user) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_TOP_TEN_USER_QUERY);
            ps.setInt(1, user.getUserId());
            ResultSet rs = ps.executeQuery();

            List<LineRequestDto> topTen = new ArrayList<>();
            while(rs.next()){
                topTen.add(createLineRequestDto(rs));
            }
            rs.close();
            ps.close();
            return topTen;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el top 10 de destinos", e);
        }
    }

    /** CRUD **/
    @Override
    public PhoneLine add(PhoneLine line) throws PhoneLineAlreadyExistsException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_PLINE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, line.getNumber());
            ps.setBoolean(2, line.getState());
            ps.setInt(3, line.getUser().getUserId());
            ps.setInt(4, line.getUserType().getTypeId());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs != null && rs.next()){
                line.setpLineId(rs.getInt(1));
            }

            ps.close();

            return line;

        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new PhoneLineAlreadyExistsException();
            }else{
                throw new RuntimeException("Error al crear la linea", e);
            }
        }
    }

    @Override
    public Integer remove(PhoneLine line) {
        return remove(line.getpLineId());
    }

    @Override
    public Integer update(PhoneLine line) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_PLINE_QUERY);
            ps.setString(1, line.getNumber());
            ps.setBoolean(2, line.getState());
            ps.setInt(3, line.getUser().getUserId());
            ps.setInt(4, line.getUserType().getTypeId());
            ps.setInt(5, line.getpLineId());

            Integer rowsAffected = ps.executeUpdate();
            ps.close();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar la linea", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(DELETE_PLINE_QUERY);
            ps.setInt(1, id);
            Integer rowsAffected = ps.executeUpdate();
            ps.close();
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la linea", e);
        }
    }

    @Override
    public PhoneLine getById(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_PLINE_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            PhoneLine line = null;
            if(rs.next()){
                line = createLine(rs);
            }
            rs.close();
            ps.close();

            return line;

        } catch (SQLException e) {

            throw new RuntimeException("Error al buscar por id", e);
        }
    }

    @Override
    public List<PhoneLine> getAll() {
        try {
            PreparedStatement ps = connect.prepareStatement(BASE_PLINE_QUERY);
            ResultSet rs = ps.executeQuery();

            List<PhoneLine> lineList = new ArrayList<>();
            while(rs.next()){
                lineList.add(createLine(rs));
            }

            rs.close();
            ps.close();

            return lineList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las lineas telefonicas", e);
        }
    }

    private PhoneLine createLine(ResultSet rs) throws SQLException {
        return new PhoneLine(rs.getInt("id_pline"), rs.getString("phone_number"), rs.getBoolean("state"),
                userDao.getById(rs.getInt("id_user")),
                typeDao.getById(rs.getInt("id_type")));
    }

    private LineRequestDto createLineRequestDto(ResultSet rs) throws SQLException {
        return new LineRequestDto(rs.getString("pnumber_destination"), rs.getString("city_destination_name"), rs.getInt("cantCalls"));
    }
}
