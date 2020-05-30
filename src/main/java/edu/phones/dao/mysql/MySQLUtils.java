package edu.phones.dao.mysql;

public class MySQLUtils {

    /** BASEs **/

    protected static String BASE_USER_QUERY =   "SELECT * FROM `users` AS u " +
                                                "INNER JOIN `user_profile` AS p " +
                                                "ON u.id_profile = p.id_profile " +
                                                "INNER JOIN `cities` AS c " +
                                                "ON u.id_city = c.id_city " +
                                                "INNER JOIN `provinces` as pr " +
                                                "ON c.id_province = pr.id_province";

    protected static String BASE_TYPE_QUERY = " SELECT * FROM `user_types` AS ut ";

    protected static String BASE_PLINE_QUERY = " SELECT * FROM `phone_lines` AS pl " +
                                                    "INNER JOIN `users` AS u " +
                                                    "ON pl.id_user=u.id_user " +
                                                    "INNER JOIN `user_profile` AS p " +
                                                    "ON pl.id_type=p.id_type";

protected static String BASE_CITY_QUERY ="SELECT * FROM 'cities' AS c" +
                                         "INNER JOIN 'provinces' AS p " +
                                         "ON c.id_province = p.id_province ";


    /** GETs **/

    protected static String GET_BY_USERNAME_USER_QUERY = BASE_USER_QUERY +  " WHERE u.username = ? " +
                                                                            "AND u.password = ?";

    // byId
    protected static String GET_BY_ID_USER_QUERY = BASE_USER_QUERY + " WHERE u.id_user = ?";

    protected static String GET_BY_ID_TYPE_QUERY = BASE_TYPE_QUERY + " WHERE ut.id_type = ?";

    protected static String GET_BY_ID_PLINE_QUERY = BASE_PLINE_QUERY + " WHERE pl.id_pline = ?";

    protected static String GET_BY_ID_CITY_QUERY = BASE_CITY_QUERY + " WHERE pl.id_city = ?";

    /** INSERTs **/

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

    protected static String INSERT_CALLS_QUERY = "INSERT INTO `Calls` (duration, cost, total_price, call_date, pnumber_origin, pnumber_destination, pline_origin, pline_destination, city_origin, city_destination, id_bill, tariff_key)" +
                                                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

    /** UPDATEs **/

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

    /** DELETEs **/

    protected static String DELETE_USER_QUERY = "DELETE FROM `users` WHERE id_user = ?";

    protected static String DELETE_TYPE_QUERY = "DELETE FROM `user_types` WHERE id_type = ?";

    protected static String DELETE_PLINE_QUERY = "DELETE FROM `phone_line` WHERE id_pline = ?";

    protected static String DELETE_BILLS_QUERY = "DELETE FROM `bills` WHERE id_bill = ?";

    protected static String DELETE_CALLS_QUERY = "DELETE FROM `calls` WHERE id_call = ?";  ///???????????????????????

    protected static String DELETE_TARIFF_QUERY = "DELETE FROM `tariff` WHERE id_tariff = ?";

    protected static String DELETE_PROFILE_QUERY = "DELETE FROM `user_profile` WHERE id_profile = ?";

    protected static String DELETE_PROVINCE_QUERY = "DELETE FROM `provinces` WHERE id_province = ?";

    protected static String DELETE_CITY_QUERY = "DELETE FROM `cities` WHERE id_city = ?";













}
