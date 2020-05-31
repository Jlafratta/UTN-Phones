
package edu.phones;

import edu.phones.controller.UserController;
import edu.phones.dao.UserDao;

import edu.phones.dao.mysql.CityMySQLDao;
import edu.phones.dao.mysql.ProvinceMySQLDao;
import edu.phones.dao.mysql.UserProfileMySQLDao;
import edu.phones.dao.mysql.UserMySQLDao;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 *      OUTDATED
 *
 *  Solo se usa para test de Controller/Service/Dao
 *
 */

public class Main {

    public static void main(String[] args) throws SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/PhonesAPI?user=root&password=");

        // Dao
        UserProfileMySQLDao profileMySQLDao = new UserProfileMySQLDao(connect);
        ProvinceMySQLDao provinceMySQLDao = new ProvinceMySQLDao(connect);
        CityMySQLDao cityMySQLDao = new CityMySQLDao(connect, provinceMySQLDao);
        UserDao userDao = new UserMySQLDao(connect, profileMySQLDao, cityMySQLDao);
        // Service
        UserService userService = new UserService(userDao);
        // Controller
        UserController userController = new UserController(userService);

        try {


            /** USER  **/

            System.out.println("\nLOGIN:");
            User loggedUser = userController.login("LaGorrita", "asd123");
            System.out.println(loggedUser);

            System.out.println("\nGET BY ID:");
            User gettedById = userController.getUser(2);
            System.out.println(gettedById);

            System.out.println("\nGET ALL: \n");
            List<User> usersList = userController.getAll();
            System.out.println(usersList);

            System.out.println("\nADD USER:");
            User newUser = new User(1, "LaGorri", "asd123", new UserProfile(1,"Julian", "Lafratta", 41307441), new City(3,"223", "Mar del Plata", new Province(1,"Buenos Aires")));
            newUser = userController.createUser(newUser);
            System.out.println(newUser);

            System.out.println("\nMODIFY USER:");
            newUser.setUsername("LaGorriPapa");
            newUser = userController.updateUser(newUser);
            System.out.println(userController.getUser(newUser.getUserId()));

            System.out.println("\nREMOVE USER:");
            userController.removeUser(newUser);
            System.out.println(userController.getAll());





        }  catch (UserNotExistException | ValidationException | UserAlreadyExistsException e) {
            e.printStackTrace();
        }

    }
}

