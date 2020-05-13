
package edu.phones;

import edu.phones.config.Configuration;
import edu.phones.controller.UserController;
import edu.phones.dao.UserDao;

import edu.phones.dao.mysql.UserMySQLDao;
import edu.phones.domain.User;
import edu.phones.exceptions.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *      OUTDATED
 *
 *  Solo se usa para test de Controller/Service/Dao
 *
 */

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/PhonesAPI?user=root&password=");

        // Dao
        UserDao userDao = new UserMySQLDao(connect);
        // Service
        UserService userService = new UserService(userDao);
        // Controller
        UserController userController = new UserController(userService);

        try {

            User loggedUser = userController.login("LaGorrita", "asd123");
            System.out.println(loggedUser);

        }  catch (UserNotExistException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }
}

