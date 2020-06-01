
package edu.phones;

import edu.phones.controller.UserController;
import edu.phones.controller.UserProfileController;
import edu.phones.controller.UserTypeController;
import edu.phones.dao.UserDao;

import edu.phones.dao.mysql.*;
import edu.phones.domain.*;
import edu.phones.exceptions.alreadyExist.ProfileAlreadyExistException;
import edu.phones.exceptions.alreadyExist.TypeAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.exceptions.notExist.TypeNotExistException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.service.UserProfileService;
import edu.phones.service.UserService;
import edu.phones.service.UserTypeService;

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
        UserProfileMySQLDao profileDao = new UserProfileMySQLDao(connect);
        UserTypeMySQLDao typeDao = new UserTypeMySQLDao(connect);

        // Service
        UserService userService = new UserService(userDao);
        UserProfileService profileService = new UserProfileService(profileDao);
        UserTypeService typeService = new UserTypeService(typeDao);

        // Controller
        UserController userController = new UserController(userService);
        UserProfileController profileController = new UserProfileController(profileService);
        UserTypeController typeController = new UserTypeController(typeService);

        try {


            /** USER  **/

            System.out.println("\nLOGIN:");
            User loggedUser = userController.login("admin", "admin");
            System.out.println(loggedUser);

            System.out.println("\nGET BY ID:");
            User gettedById = userController.getUser(2);
            System.out.println(gettedById);

            System.out.println("\nGET ALL: \n");
            List<User> usersList = userController.getAll();
            System.out.println(usersList);

            System.out.println("\nADD USER:");
            User newUser = new User("LaGorri", "asd123", new UserProfile(1,"Julian", "Lafratta", 41307441), new City(3,"223", "Mar del Plata", new Province(1,"Buenos Aires")));
            newUser = userController.createUser(newUser);
            System.out.println(newUser);

            System.out.println("\nMODIFY USER:");
            newUser.setUsername("LaGorriPapa");
            newUser = userController.updateUser(newUser);
            System.out.println(userController.getUser(newUser.getUserId()));

            System.out.println("\nREMOVE USER:");
            userController.removeUser(newUser);
            System.out.println(userController.getAll());

            /** USER PROFILE **/

            System.out.println("\nADD PROFILE:");
            UserProfile newProfile = new UserProfile("profileName", "profileLastname", 12312312);
            newProfile = profileController.createProfile(newProfile);
            System.out.println(newProfile);

            System.out.println("\nUPDATE PROFILE:");
            newProfile.setName("NAMEEEE");
            newProfile = profileController.updateProfile(newProfile);
            System.out.println(newProfile);

            /** USER TYPE **/

            System.out.println("\nADD TYPE:");
            UserType newType = new UserType("userType3");
            newType = typeController.createType(newType);
            System.out.println(newType);

            System.out.println("\nUPDATE:");
            newType.setName("type2");
            newType = typeController.updateType(newType);
            System.out.println(newType);

            System.out.println("\nREMOVE:");
            typeController.removeType(newType);















        }  catch (UserNotExistException | ValidationException | UserAlreadyExistsException | ProfileAlreadyExistException | ProfileNotExistException | TypeAlreadyExistsException | TypeNotExistException e) {
            e.printStackTrace();
        }

    }
}

