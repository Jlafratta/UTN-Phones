package edu.phones.dao.mysql;

public class MySQLUtils {

    protected static String BASE_USER_QUERY =   "SELECT * FROM `user` AS u " +
                                                "INNER JOIN `user_profile` AS p " +
                                                "ON u.id_profile = p.id_profile " +
                                                "INNER JOIN `city` AS c " +
                                                "ON u.id_city = c.id_city " +
                                                "INNER JOIN `province` as pr " +
                                                "ON c.id_province = pr.id_province";

    protected static String GET_BY_USERNAME_USER_QUERY = BASE_USER_QUERY +  " WHERE u.username = ? " +
                                                                            "AND u.password = ?";

}
