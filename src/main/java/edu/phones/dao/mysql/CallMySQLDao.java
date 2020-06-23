package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.BillDao;
import edu.phones.dao.CallDao;
import edu.phones.dao.PhoneLineDao;
import edu.phones.dao.TariffDao;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.AddCallDto;
import edu.phones.exceptions.alreadyExist.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("callMySQLDao")
public class CallMySQLDao implements CallDao {

    final Connection connect;
    PhoneLineDao lineDao;
    BillDao billDao;
    TariffDao tariffDao;

    @Autowired
    public CallMySQLDao(Connection connect, @Qualifier("phoneLineMySQLDao") PhoneLineDao lineDao, @Qualifier("billMySQLDao") BillDao billDao, @Qualifier("tariffMySQLDao") TariffDao tariffDao) {
        this.connect = connect;
        this.lineDao = lineDao;
        this.billDao = billDao;
        this.tariffDao = tariffDao;
    }

    @Override
    public List<Call> getByOriginUserId(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ORIGIN_USER_ID_CALLS_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            List<Call> calls = new ArrayList<>();
            while (rs.next()){
                calls.add(createCall(rs));
            }

            return calls;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las llamdas", e);
        }
    }

    @Override
    public List<Call> getByOriginUserFilterByDate(User currentUser, Date from, Date to) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ORIGIN_USER_FILTER_BY_DATE_CALLS_QUERY);
            ps.setInt(1, currentUser.getUserId());
            ps.setDate(2, new java.sql.Date(from.getTime()));
            ps.setDate(3, new java.sql.Date(to.getTime()));
            ResultSet rs = ps.executeQuery();

            List<Call> calls = new ArrayList<>();
            while (rs.next()){
                calls.add(createCall(rs));
            }

            return calls;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las llamdas", e);
        }
    }

    @Override
    public List<Call> getByOriginUser(User currentUser) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ORIGIN_USER_CALLS_QUERY);
            ps.setInt(1, currentUser.getUserId());
            ResultSet rs = ps.executeQuery();

            List<Call> calls = new ArrayList<>();
            while (rs.next()){
                calls.add(createCall(rs));
            }

            return calls;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las llamdas", e);
        }
    }

    @Override
    public Call add(AddCallDto call) throws CallAlreadyExistsException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_CALLS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, call.getDuration());
            ps.setDate(2, new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(call.getDate()) .getTime()));
            ps.setString(3, call.getFrom());
            ps.setString(4, call.getTo());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            Call newCall = null;
            if(rs != null && rs.next()){
                newCall = getById(rs.getInt(1));
            }
            return newCall;

        }catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new CallAlreadyExistsException();
            }else{
                throw new RuntimeException(e.getMessage(), e);
            }
        }catch(ParseException e) {
            throw new RuntimeException("Error en el formato de fecha", e) ;
        }
    }

    @Override
    public Call add(Call value)  {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(Call call) {
        return remove(call.getCallId());
    }

    @Override
    public Integer update(Call call) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_CALLS_QUERY);
            ps.setInt(1, call.getDuration());
            ps.setDouble(2, call.getCost());
            ps.setDouble(3, call.getTotalCost());
            ps.setDouble(4, call.getPrice());
            ps.setDouble(5, call.getTotalPrice());
            ps.setDate(6, new java.sql.Date(call.getDate().getTime()));
            ps.setString(7, call.getOrigin().getNumber());
            ps.setString(8, call.getDestination().getNumber());
            ps.setInt(9, call.getOrigin().getpLineId());
            ps.setInt(10, call.getDestination().getpLineId());
            ps.setInt(11, call.getOrigin().getUser().getCity().getCityId());
            ps.setInt(12, call.getDestination().getUser().getCity().getCityId());
            ps.setInt(13, call.getBill().getBillId());
            ps.setInt(14, call.getTariff().getKey());
            ps.setInt(15, call.getCallId());

            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar la llamada", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(DELETE_CALLS_QUERY);
            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la llamada", e);
        }
    }

    @Override
    public Call getById(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_CALLS_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Call call = null;
            if(rs.next()){
                call = createCall(rs);
            }
            rs.close();
            ps.close();

            return call;

        } catch (SQLException e) {

            throw new RuntimeException("Error al buscar por id", e);
        }
    }

    @Override
    public List<Call> getAll() {
        try {
            PreparedStatement ps = connect.prepareStatement(BASE_CALLS_QUERY);
            ResultSet rs = ps.executeQuery();

            List<Call> callList = new ArrayList<>();
            while(rs.next()){
                callList.add(createCall(rs));
            }

            return callList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las llamdas", e);
        }
    }

    private Call createCall(ResultSet rs) throws SQLException {
        return new Call(rs.getInt("id_call"), rs.getInt("duration"), rs.getDouble("cost"), rs.getDouble("total_cost"), rs.getDouble("price"), rs.getDouble("total_price"), rs.getDate("call_date"),
                lineDao.getById(rs.getInt("pline_origin")),
                lineDao.getById(rs.getInt("pline_destination")),
                null,
                tariffDao.getById(rs.getInt("tariff_key")));
    }
}
