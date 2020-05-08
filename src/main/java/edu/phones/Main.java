package edu.phones;

import edu.phones.controller.UserController;
import edu.phones.dao.UserDao;
import edu.phones.dao.mysql.UserMySQLDao;
import edu.phones.domain.User;
import edu.phones.exceptions.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        //TODO update connection

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/PhonesAPI?user=root&password=");

        UserDao userDao = new UserMySQLDao(connect);

        UserService userService = new UserService(userDao);

        UserController userController = new UserController(userService);

        try {
            User loggedUser = userController.login("LaGorrita", "asd123");
            System.out.println(loggedUser);
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (UserNotExistException e) {
            e.printStackTrace();
        }



    }
}
