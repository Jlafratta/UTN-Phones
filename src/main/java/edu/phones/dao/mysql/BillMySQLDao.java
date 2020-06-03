package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.BillDao;
import edu.phones.dao.PhoneLineDao;
import edu.phones.domain.Bill;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.BillAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.phones.dao.mysql.MySQLUtils.*;

@Repository
@Qualifier("billMySQLDao")
public class BillMySQLDao implements BillDao {

    final Connection connect;
    PhoneLineDao lineDao;

    @Autowired
    public BillMySQLDao(Connection connect, @Qualifier("phoneLineMySQLDao")PhoneLineDao lineDao) {
        this.connect = connect;
        this.lineDao = lineDao;
    }

    @Override
    public List<Bill> getByUserFilterByDate(User currentUser, Date dFrom, Date dTo) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_USER_FILTER_BY_DATE_BILLS_QUERY);
            ps.setInt(1, currentUser.getUserId());
            ps.setDate(2, new java.sql.Date(dFrom.getTime()));
            ps.setDate(3, new java.sql.Date(dTo.getTime()));
            ResultSet rs = ps.executeQuery();

            List<Bill> bills = new ArrayList<>();
            while (rs.next()){
                bills.add(createBill(rs));
            }

            return bills;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las facturas", e);
        }
    }

    @Override
    public List<Bill> getByUser(User currentUser) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_USER_BILLS_QUERY);
            ps.setInt(1, currentUser.getUserId());
            ResultSet rs = ps.executeQuery();

            List<Bill> bills = new ArrayList<>();
            while (rs.next()){
                bills.add(createBill(rs));
            }

            return bills;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las facturas", e);
        }
    }

    @Override
    public Bill add(Bill bill) throws BillAlreadyExistsException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_BILLS_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, bill.getCost());
            ps.setDouble(2, bill.getTotal());
            ps.setDate(3, bill.getDate());
            ps.setDate(4, bill.getExpireDate());
            ps.setInt(5, bill.getCountCalls());
            ps.setInt(6, bill.getpLine().getpLineId());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs != null && rs.next()){
                bill.setBillId(rs.getInt(1));
            }

            return bill;

        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new BillAlreadyExistsException();
            }else{
                throw new RuntimeException("Error al crear la nueva factura", e);
            }
        }
    }

    @Override
    public Integer remove(Bill bill) {
        return remove(bill.getBillId());
    }

    @Override
    public Integer update(Bill bill) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_BILLS_QUERY);
            ps.setDouble(1, bill.getCost());
            ps.setDouble(2, bill.getTotal());
            ps.setDate(3, bill.getDate());
            ps.setDate(4, bill.getExpireDate());
            ps.setInt(5, bill.getCountCalls());
            ps.setInt(6, bill.getpLine().getpLineId());
            ps.setInt(7, bill.getBillId());

            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar la factura", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(DELETE_BILLS_QUERY);
            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la factura", e);
        }
    }

    @Override
    public Bill getById(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_BILLS_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Bill b = null;
            if(rs.next()){
                b = createBill(rs);
            }
            rs.close();
            ps.close();

            return b;

        } catch (SQLException e) {

            throw new RuntimeException("Error al buscar por id", e);
        }
    }

    @Override
    public List<Bill> getAll() {
        try {
            PreparedStatement ps = connect.prepareStatement(BASE_BILLS_QUERY);
            ResultSet rs = ps.executeQuery();

            List<Bill> billList = new ArrayList<>();
            while(rs.next()){
                billList.add(createBill(rs));
            }

            return billList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las facturas", e);
        }
    }

    private Bill createBill(ResultSet rs) throws SQLException {
        return new Bill(rs.getInt("id_bill"), rs.getDouble("cost"), rs.getDouble("total"), rs.getDate("bill_date"), rs.getDate("expire_date"), rs.getInt("calls_count"),
                lineDao.getById(rs.getInt("id_pline")));
    }
}
