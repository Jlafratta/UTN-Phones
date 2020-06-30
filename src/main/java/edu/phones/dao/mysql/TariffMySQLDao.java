package edu.phones.dao.mysql;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import edu.phones.dao.TariffDao;
import edu.phones.domain.Tariff;
import edu.phones.exceptions.alreadyExist.TarriffAlreadyExistsException;
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
@Qualifier("tariffMySQLDao")
public class TariffMySQLDao implements TariffDao {

    final Connection connect;

    @Autowired
    public TariffMySQLDao(Connection connect) {
        this.connect = connect;
    }

    /* CRUD */
    @Override
    public Tariff add(Tariff tariff) throws TarriffAlreadyExistsException {
        try {
            PreparedStatement ps = connect.prepareStatement(INSERT_TARIFF_QUERY);
            ps.setInt(1, tariff.getKey());
            ps.setDouble(2, tariff.getCost());
            ps.setDouble(3, tariff.getPrice());
            ps.execute();
            ps.close();

            return tariff;

        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY){
                throw new TarriffAlreadyExistsException();
            }else{
                throw new RuntimeException("Error al crear la tarifa", e);
            }
        }
    }

    @Override
    public Integer remove(Tariff tariff) {
        return remove(tariff.getKey());
    }

    @Override
    public Integer update(Tariff tariff) {
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_TARIFF_QUERY);
            ps.setInt(1, tariff.getKey());
            ps.setDouble(2, tariff.getCost());
            ps.setDouble(3, tariff.getPrice());


            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected; // Retorno la cantidad de campos modificados

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar la tarifa", e);
        }
    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(DELETE_TARIFF_QUERY);
            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la tarifa", e);
        }
    }

    @Override
    public Tariff getById(Integer id) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_BY_ID_TARIFF_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Tariff tariff = null;
            if(rs.next()){
                tariff = createTariff(rs);
            }
            rs.close();
            ps.close();

            return tariff;

        } catch (SQLException e) {

            throw new RuntimeException("Error al buscar por id", e);
        }
    }

    @Override
    public List<Tariff> getAll() {
        return null;
    }

    @Override
    public List<Tariff> getAll(Integer page, Integer size) {
        try {
            PreparedStatement ps = connect.prepareStatement(GET_TARIFF_QUERY_PAGINATION);
            ps.setInt(1,size );
            ps.setInt(2,page);
            ResultSet rs = ps.executeQuery();

            List<Tariff> tariffList = new ArrayList<>();
            while(rs.next()){
                tariffList.add(createTariff(rs));
            }

            return tariffList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al traer todas las tarifas", e);
        }
    }

    private Tariff createTariff(ResultSet rs) throws SQLException {
        return new Tariff(rs.getInt("tariff_key"), rs.getDouble("cost"), rs.getDouble("price"));

    }
}