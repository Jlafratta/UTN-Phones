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

    /** GETs **/

    protected static String GET_BY_USERNAME_USER_QUERY = BASE_USER_QUERY +  " WHERE u.username = ? " +
                                                                            "AND u.password = ?";

    // byId
    protected static String GET_BY_ID_USER_QUERY = BASE_USER_QUERY + " WHERE u.id_user = ?";

    protected static String GET_BY_ID_TYPE_QUERY = BASE_TYPE_QUERY + " WHERE ut.id_type = ?";

    /** INSERTs **/

    protected static String INSERT_USER_QUERY = "INSERT INTO `users` (username, password, id_profile, id_city)" +
                                                "VALUES (?, ?, ?, ?)";

    protected static String INSERT_PROFILE_QUERY = "INSERT INTO `user_profile` (name, lastname, dni)" +
                                                   " VALUES (?, ?, ?)";

    protected static String INSERT_CITY_QUERY = "INSERT INTO `cities` (prefix, city_name, id_province) " +
                                                "VALUES (?, ?, ?) ";

    protected static String INSERT_TYPE_QUERY = "INSERT INTO `user_types` ( type_name)" +
                                                "VALUES (?)";

    /** UPDATEs **/

    protected static String UPDATE_USER_QUERY = "UPDATE `users` SET username = ?, password = ?, id_profile = ?, id_city = ? " +
                                                "WHERE id_user = ?";

    protected static String UPDATE_TYPE_QUERY = "UPDATE `user_types` SET type_name =? " +
                                                "WHERE id_type = ?";

    /** DELETEs **/

    protected static String DELETE_USER_QUERY = "DELETE FROM `users` WHERE id_user = ?";

    protected static String DELETE_TYPE_QUERY = "DELETE FROM `user_types` WHERE id_type = ?";

}
