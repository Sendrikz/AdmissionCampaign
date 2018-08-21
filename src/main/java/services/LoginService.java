package services;

import controller.commands.LoginCommand;
import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.RoleDao;
import model.dao.dao_interfaces.SubjectDao;
import model.dao.dao_interfaces.UserDao;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginService {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));

    public static User checkLogin(String login, String password) {
        log.info("Start class LoginLogic checkLogin()");
        ArrayList<User> listOfAllUsers = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        UserDao user = DaoFactory.getUserDao(connection);
        listOfAllUsers = user.getAll();
        connectionManager.close(connection);
        for (User users : listOfAllUsers) {
            if (users.getEmail().equals(login) && users.getPassword().equals(password)) {
                log.info("Find user");
                return users;
            }
        }
        log.info("No such user");
        return null;
    }

    public static void addUser(String lastName, String firstName, String patronymic,
                               String birthday, String city, String email, String password) {
        log.info("Start class LoginLogic addUser()");
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        RoleDao roleDao = DaoFactory.getRoleDao(connection);
        User user = new User(lastName, firstName, patronymic, birthday, city, email, password,
                roleDao.findIdByRoleName("Student"));
        UserDao userDao = DaoFactory.getUserDao(connection);
        userDao.add(user);
        connectionManager.close(connection);
    }

    public static String getRoleById(int roleId) {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        RoleDao role = DaoFactory.getRoleDao(connection);
        String res = role.findById(roleId).getName();
        connectionManager.close(connection);
        return res;
    }

    public static ArrayList<Subject> getAllSubjects() {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        SubjectDao subject = DaoFactory.getSubjectDao(connection);
        ArrayList<Subject> listOfSubjects = subject.getAll();
        connectionManager.close(connection);
        return listOfSubjects;
    }
}
