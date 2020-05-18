package edu.phones.dao.mysql;

public class MySQLUtils {

    protected static String BASE_USER_QUERY =   "SELECT * FROM `users` AS u " +
                                                "INNER JOIN `user_profile` AS p " +
                                                "ON u.id_profile = p.id_profile " +
                                                "INNER JOIN `cities` AS c " +
                                                "ON u.id_city = c.id_city " +
                                                "INNER JOIN `provinces` as pr " +
                                                "ON c.id_province = pr.id_province";

    protected static String GET_BY_USERNAME_USER_QUERY = BASE_USER_QUERY +  " WHERE u.username = ? " +
                                                                            "AND u.password = ?";

    protected static String GET_BY_ID_USER_QUERY = BASE_USER_QUERY + " WHERE u.id_user = ?";

    protected static String INSERT_USER_QUERY = "INSERT INTO `users` (username, password, id_profile, id_city)" +
                                                "VALUES (?, ?, ?, ?)";

    protected static String UPDATE_USER_QUERY = "UPDATE `users` SET username = ?, password = ?, id_profile = ?, id_city = ? " +
                                                "WHERE id_user = ?";

    protected static String DELETE_USER_QUERY = "DELETE FROM `users` WHERE id_user = ?";

}
