package edu.phones.dao.mysql;

public class MySQLUtils {

    /* Custom */

    protected static String GET_DURATION_BY_MONTH_CALLS_QUERY = "SELECT up.name as Nombre, up.lastname as Apellido, sum(c.duration) as Duracion_total " +
                                                                "from user_profile as up " +
                                                                "inner join users as u " +
                                                                "on u.id_profile = up.id_profile " +
                                                                "inner join phone_lines as pl " +
                                                                "on u.id_user = pl.id_user " +
                                                                "inner join calls as c " +
                                                                "on c.pline_origin = pl.id_pline " +
                                                                "where u.id_user = ? AND c.call_date >= ? AND c.call_date <= ?";

    protected static String GET_BY_ORIGIN_USER_FILTER_BY_DATE_CALLS_QUERY = "SELECT * FROM users as u " +
                                                                            "INNER JOIN phone_lines as pl " +
                                                                            "ON u.id_user = pl.id_user " +
                                                                            "INNER JOIN calls as c " +
                                                                            "ON c.pline_origin = pl.id_pline " +
                                                                            "WHERE u.id_user = ? " +
                                                                            "AND c.call_date >= ? AND c.call_date <= ?";

    protected static String GET_BY_ORIGIN_USER_CALLS_QUERY = "SELECT * FROM users as u " +
                                                             "INNER JOIN phone_lines as pl " +
                                                             "ON u.id_user = pl.id_user " +
                                                             "INNER JOIN calls as c " +
                                                             "ON c.pline_origin = pl.id_pline " +
                                                             "WHERE u.id_user = ? ";

    protected static String GET_BY_USER_FILTER_BY_DATE_BILLS_QUERY = "SELECT * FROM users as u " +
                                                                     "INNER JOIN phone_lines as pl " +
                                                                     "ON u.id_user = pl.id_user " +
                                                                     "INNER JOIN bills as b " +
                                                                     "ON b.id_pline = pl.id_pline " +
                                                                     "WHERE u.id_user = ? " +
                                                                     "AND b.bill_date >= ? AND b.bill_date <= ?";

    protected static String GET_BY_USER_BILLS_QUERY = "SELECT * FROM users as u " +
                                                      "INNER JOIN phone_lines as pl " +
                                                      "ON u.id_user = pl.id_user " +
                                                      "INNER JOIN bills as b " +
                                                      "ON b.id_pline = pl.id_pline " +
                                                      "WHERE u.id_user = ?";


    protected  static String GET_BY_USER_PROVINCE_DNI_QUERY = "select u.id_user as id_user , u.username as username , u.password  as password , u.id_profile as id_profile , u.id_city as id_city  from users as u " +
            "inner join user_profile as up " +
            "on u.id_profile = up.id_profile " +
            "inner join cities as c " +
            "on c.id_city = u.id_city " +
            "inner join provinces as p " +
            "on c.id_province = p.id_province " +
            "where p.province_name = ? and (up.dni % 2 = ? ) ";



    /* BASEs */

    protected static String BASE_USER_QUERY =   "SELECT * FROM `users` AS u";

    protected static String BASE_TYPE_QUERY = " SELECT * FROM `user_types` AS ut";

    protected static String BASE_PLINE_QUERY = " SELECT * FROM `phone_lines` AS pl";

    protected static String BASE_CITY_QUERY ="SELECT * FROM `cities` AS c";

    protected static String BASE_PROVINCE_QUERY = "SELECT * FROM `provinces` as p";

    protected static String BASE_BILLS_QUERY =" SELECT * FROM `bills` as b";

    protected static String BASE_CALLS_QUERY = " SELECT * FROM `calls` as c";

    protected  static String BASE_PROFILE_QUERY  = "SELECT * FROM `user_profile` AS up";

    protected  static String BASE_TARIFF_QUERY  = "SELECT * FROM `tariff` AS t";



    /* GETs */

    protected static String GET_BY_USERNAME_USER_QUERY = BASE_USER_QUERY +  " WHERE u.username = ? AND u.password = ?";

    // byId
    protected static String GET_BY_ID_USER_QUERY = BASE_USER_QUERY + " WHERE u.id_user = ?";

    protected static String GET_BY_ID_TYPE_QUERY = BASE_TYPE_QUERY + " WHERE ut.id_type = ?";

    protected static String GET_BY_ID_PLINE_QUERY = BASE_PLINE_QUERY + " WHERE pl.id_pline = ?";

    protected static String GET_BY_ID_CITY_QUERY = BASE_CITY_QUERY + " WHERE c.id_city = ?";

    protected static String GET_BY_ID_PROVINCE_QUERY = BASE_PROVINCE_QUERY + " WHERE p.id_province = ?";

    protected static String GET_BY_ID_BILLS_QUERY = BASE_BILLS_QUERY + " WHERE b.id_bill = ?";

    protected static String GET_BY_ID_CALLS_QUERY = BASE_CALLS_QUERY + " WHERE c.id_call = ?";

    protected static String GET_BY_ID_PROFILE_QUERY = BASE_PROFILE_QUERY + " WHERE  up.id_profile = ?";

    protected static String GET_BY_ID_TARIFF_QUERY = BASE_TARIFF_QUERY + " WHERE t.tariff_key = ?";



    /* INSERTs */

    protected static String INSERT_USER_QUERY = "INSERT INTO `users` (username, password, id_profile, id_city)" +
            "VALUES (?, ?, ?, ?)";

    protected static String INSERT_PROFILE_QUERY = "INSERT INTO `user_profile` (name, lastname, dni)" +
            " VALUES (?, ?, ?)";

    protected static String INSERT_CITY_QUERY = "INSERT INTO `cities` (prefix, city_name, id_province) " +
            "VALUES (?, ?, ?) ";

    protected static String INSERT_TYPE_QUERY = "INSERT INTO `user_types` ( type_name)" +
            "VALUES (?)";

    protected static String INSERT_PLINE_QUERY = "INSERT INTO `phone_lines` (phone_number, state, id_user, id_type)" +
            "VALUES (?, ?, ?, ?)";

    protected static String INSERT_PROVINCE_QUERY = "INSERT INTO `provinces` (province_name)" +
            "VALUES (?)";

    protected static String INSERT_BILLS_QUERY = "INSERT INTO `bills` (cost, total, bill_date, expire_date, calls_count, id_pline)" +
            "VALUES (?, ?, ?, ?, ? , ? )";

    protected static String INSERT_TARIFF_QUERY = "INSERT INTO `tariff` (tariff_key, value)" +
            "VALUES (?, ?)";

    protected static String INSERT_CALLS_QUERY = "INSERT INTO `calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination, pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

    /* UPDATEs */

    protected static String UPDATE_USER_QUERY = "UPDATE `users` SET username = ?, password = ?, id_profile = ?, id_city = ? " +
            "WHERE id_user = ?";

    protected static String UPDATE_TYPE_QUERY = "UPDATE `user_types` SET type_name =? " +
            "WHERE id_type = ?";

    protected static String UPDATE_PLINE_QUERY = "UPDATE `phone_lines` SET phone_number = ?, state = ?, id_user = ?, id_type = ? " +
            "WHERE id_pline = ?";

    protected static String UPDATE_PROVINCE_QUERY = "UPDATE `provinces` SET province_name = ? " +
            "WHERE id_province = ?";

    protected static String UPDATE_CITY_QUERY = "UPDATE `cities` SET prefix = ?, city_name = ?, id_province = ? " +
            "WHERE id_city = ?";

    protected static String UPDATE_PROFILE_QUERY = "UPDATE `user_profile` SET name = ?, lastname = ?, dni = ? " +
            "WHERE id_profile = ?";

    protected static String UPDATE_BILLS_QUERY = "UPDATE `bills` SET cost = ?, total = ?, bill_date = ?, expire_date = ? , calls_count = ?, id_pline= ? " +
            "WHERE id_bill = ?";

    protected static String UPDATE_TARIFF_QUERY = "UPDATE `tariff` SET value = ? " +
            "WHERE id_tariff = ?";

    protected static String UPDATE_CALLS_QUERY = "UPDATE `calls` SET duration = ?, cost = ?, total_price = ?, call_date = ? ,pnumber_origin = ?, pnumber_destination = ?, pline_origin = ?, pline_destination = ?, city_origin = ?, city_destination = ? , id_bill =? , tariff_key = ? " +
            "WHERE id_call = ?";

    /* DELETEs */

    protected static String DELETE_USER_QUERY = "DELETE FROM `users` WHERE id_user = ?";

    protected static String DELETE_TYPE_QUERY = "DELETE FROM `user_types` WHERE id_type = ?";

    protected static String DELETE_PLINE_QUERY = "DELETE FROM `phone_line` WHERE id_pline = ?";

    protected static String DELETE_BILLS_QUERY = "DELETE FROM `bills` WHERE id_bill = ?";

    protected static String DELETE_CALLS_QUERY = "DELETE FROM `calls` WHERE id_call = ?";

    protected static String DELETE_TARIFF_QUERY = "DELETE FROM `tariff` WHERE id_tariff = ?";

    //protected static String DELETE_PROFILE_QUERY = "DELETE FROM `user_profile` WHERE id_profile = ?";

    protected static String DELETE_PROVINCE_QUERY = "DELETE FROM `provinces` WHERE id_province = ?";

    protected static String DELETE_CITY_QUERY = "DELETE FROM `cities` WHERE id_city = ?";













}