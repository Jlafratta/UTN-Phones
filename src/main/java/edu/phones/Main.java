package edu.phones;

import edu.phones.controller.UserController;
import edu.phones.dao.UserDao;
import edu.phones.dao.factory.AbstractDaoFactory;
import edu.phones.dao.mysql.UserMySQLDao;
import edu.phones.domain.User;
import edu.phones.exceptions.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Properties config = new Properties();
        config.load(new FileInputStream("./conf/app.properties"));

        // Genero el factory
        AbstractDaoFactory daoFactory =  (AbstractDaoFactory) Class.forName(config.getProperty("db.dao.factory")).getDeclaredConstructor(Properties.class).newInstance(config);

        // Traigo el dao que me provee el factory
        UserDao userDao = daoFactory.getUserDao();
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
