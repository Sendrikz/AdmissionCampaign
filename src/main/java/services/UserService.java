package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.UserDao;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.ClassTool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);

    public static boolean addUserToSubject(User user, Subject subject, boolean checked, BigDecimal grade) {
        log.info("Start class UserService method addUserToSubject()");
        ArrayList<Subject> allSubjectsByUser = allSubjectsByUser(user);
        if (!allSubjectsByUser.contains(subject)) {
            log.info("There is no user in table User_Subject");
            Connection connection = ConnectionManager.getInstance().getConnection();
            UserDao userDao = DaoFactory.getUserDao(connection);
            userDao.addUserToSubject(user, subject, checked, grade);
            ConnectionManager.getInstance().close(connection);
            return true;
        }
        log.info("There is user in table User_Subject");
        return false;
    }

    private static ArrayList<Subject> allSubjectsByUser(User user) {
        log.info("Start class UserService method allSubjectsByUser()");
        ArrayList<Subject> allSubjectsByUser;
        log.info("allSubjectsByUser()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        allSubjectsByUser = userDao.getAllSubjectsByUser(user.getId());
        log.debug("List of subject by user id: " + allSubjectsByUser);
        ConnectionManager.getInstance().close(connection);
        return allSubjectsByUser;
    }

    public static boolean addUserToSpecialty(User user, Specialty specialty, boolean checked) {
        log.info("Start class UserService method addUserToSpecialty()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        log.debug("User to add: " + user);
        log.debug("Specialty to add: " + specialty);
        if (!getAllSpecialtiesByUser(user).containsKey(specialty)) {
            log.info("There is no such user in table user_specialty");
            userDao.addUserToSpecialty(user, specialty, checked);
            return true;
        }
        log.info("There is user in table user_specialty");
        ConnectionManager.getInstance().close(connection);
        return false;
    }

    public static HashMap<Specialty, Boolean> getAllSpecialtiesByUser(User user) {
        log.info("Start class UserService method getAllSpecialtiesByUser()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        HashMap<Specialty, Boolean> specialtyBooalenHashMap =
                userDao.getAllSpecialtiesByUser(user.getId());
        log.debug("Map of specialties by user: " + specialtyBooalenHashMap);
        ConnectionManager.getInstance().close(connection);
        return specialtyBooalenHashMap;
    }

    public static HashMap<Subject, BigDecimal> getAllCheckedSubjectsByUser(int id) {
        log.info("Start class UserService method getAllCheckedSubjectsByUser()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        HashMap<Subject, BigDecimal> subjectBigDecimalHashMap =
                userDao.getAllCheckedSubjectsByUser(id);
        ConnectionManager.getInstance().close(connection);
        return subjectBigDecimalHashMap;
    }

    public static ArrayList<User> getAll() {
        log.info("Start class UserService method getAll()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao  userDao = DaoFactory.getUserDao(connection);
        ArrayList<User> listOfAllUsers = userDao.getAll();
        ConnectionManager.getInstance().close(connection);
        return listOfAllUsers;
    }

    public static ArrayList<User> getAllStudents(int id) {
        log.info("Start class UserService method getAllStudents()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        ArrayList<User> listOfAllStudents = userDao.getAllStudents(id);
        ConnectionManager.getInstance().close(connection);
        return listOfAllStudents;
    }

    public static ArrayList<Subject> getAllSubjectsByUser(int id) {
        log.info("Start class UserService method getAllSubjectsByUser()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        ArrayList<Subject> listOfSubjects = userDao.getAllSubjectsByUser(id);
        ConnectionManager.getInstance().close(connection);
        return listOfSubjects;
    }

    public static Specialty getPassedSpecialtyByUser(int id) {
        log.info("Start class UserService method getPassedSpecialtyByUser()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        Specialty specialty = userDao.getPassedSpecialtyByUser(id);
        log.debug("Specialty: " + specialty);
        ConnectionManager.getInstance().close(connection);
        return specialty;
    }
}
