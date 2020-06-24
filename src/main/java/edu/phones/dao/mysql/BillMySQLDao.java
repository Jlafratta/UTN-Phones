package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.BillDao;
import edu.phones.dao.PhoneLineDao;
import edu.phones.domain.Bill;
import edu.phones.domain.User;
import edu.phones.exceptions.alreadyExist.BillAlreadyExistsException;
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
    public Bill add(Bill bill) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(Bill bill) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer update(Bill bill) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer remove(Integer id) {
        throw new UnsupportedOperationException();
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
        return new Bill(rs.getInt("id_bill"), rs.getDouble("cost"), rs.getDouble("price"), rs.getDate("bill_date"), rs.getDate("expire_date"), rs.getInt("calls_count"),
                lineDao.getById(rs.getInt("id_pline")));
    }
}
