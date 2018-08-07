package services;

import controller.commands.LoginCommand;
import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.UserDao;
import model.enteties.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginService {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));

    public static boolean checkLogin(String login, String password) {
        log.info("Start class LoginLogic checkLogin()");
        ArrayList<User> listOfAllUsers = null;
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            log.info("Start transaction");
            UserDao user = DaoFactory.getUserDao(connection);
            listOfAllUsers = user.getAll();
            connection.commit();
            log.info("End transaction");
        } catch (SQLException e) {
            log.error("Transaction fail");
            e.printStackTrace();
        }
        for (User users : listOfAllUsers) {
            if (users.getEmail().equals(login) && users.getPassword().equals(password)) {
                log.info("Find user");
                return true;
            }
        }
        log.info("No such user");
        return false;
    }
}
