package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.UserDao;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {

    private static final Logger log = Logger.getLogger(String.valueOf(UserService.class));

    public static boolean addUserToSubject(User user, Subject subject, boolean checked, BigDecimal grade) {
        log.info("Start class UserService method addUserToSubject()");
        ArrayList<Subject> allSubjectsByUser = allSubjectsByUser(user);
        if (!allSubjectsByUser.contains(subject)) {
            log.info("There is no user in table User_Subject");
            ConnectionManager connectionManager = new ConnectionManager();
            Connection connection = connectionManager.getConnection();
            try {
                connection.setAutoCommit(false);
                log.info("Start transaction");
                UserDao userDao = DaoFactory.getUserDao(connection);
                userDao.addUserToSubject(user, subject, checked, grade);
                connection.commit();
                log.info("End transaction");
            } catch (SQLException e) {
                log.info("Transaction fail");
                e.printStackTrace();
            }
            connectionManager.close(connection);
            return true;
        }
        log.info("There is user in table User_Subject");
        return false;
    }

    private static ArrayList<Subject> allSubjectsByUser(User user) {
        ArrayList<Subject> allSubjectsByUser = null;
        log.info("allSubjectsByUser()");
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            log.info("Start transaction");
            UserDao userDao = DaoFactory.getUserDao(connection);
            allSubjectsByUser = userDao.getAllSubjectsByUser(user.getId());
            log.debug("List of subject by user id: " + allSubjectsByUser);
            connection.commit();
            log.info("End transaction");
        } catch (SQLException e) {
            log.info("Transaction fail");
            e.printStackTrace();
        }
        connectionManager.close(connection);
        return allSubjectsByUser;
    }
}
