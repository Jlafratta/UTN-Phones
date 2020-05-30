package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.PhoneLineDao;
import edu.phones.domain.PhoneLine;
import edu.phones.exceptions.alreadyExist.PhoneLineAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
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
@Qualifier("phoneLineMySQLDao")
public class PhoneLineMySQLDao implements PhoneLineDao {

    final Connection connect;

    @Autowired
    public PhoneLineMySQLDao(Connection connect) {
        this.connect = connect;
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
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la linea", e);
        }
    }

    // TODO getById & getAll
    @Override
    public PhoneLine getById(Integer id) {
        return null;
    }

    @Override
    public List<PhoneLine> getAll() {
        return null;
    }
}
