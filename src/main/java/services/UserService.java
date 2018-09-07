package services;

import model.connection.ConnectionManager;
import model.dao.factory.DaoFactory;
import model.dao.UserDao;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.exceptions.NoSuchSpecialtyException;

import java.io.Closeable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class UserService implements Closeable {

    private Connection connection;
    private UserDao userDao;
    private static final Logger log = Logger.getLogger(UserService.class);

    public UserService() {
        connection = ConnectionManager.getInstance().getConnection();
        userDao = DaoFactory.getUserDao(connection);
    }

    public UserService(Boolean isTest) {
        if (isTest) {
            this.connection = ConnectionManager.getInstance().getConnectionToTestBD();
            userDao = DaoFactory.getUserDao(connection);
        } else {
            connection = ConnectionManager.getInstance().getConnection();
            userDao = DaoFactory.getUserDao(connection);
        }
    }

    public boolean addUserToSubject(User user, Subject subject, boolean checked,
                                    BigDecimal grade) {
        log.info("Start class UserService method addUserToSubject()");

        ArrayList<Subject> allSubjectsByUser = allSubjectsByUser(user);
        if (!allSubjectsByUser.contains(subject)) {
            log.info("There is no user in table User_Subject");
            userDao.addUserToSubject(user, subject, checked, grade);
            return true;
        }

        log.info("There is user in table User_Subject");
        return false;
    }

    private ArrayList<Subject> allSubjectsByUser(User user) {
        log.info("Start class UserService method allSubjectsByUser()");

        ArrayList<Subject> allSubjectsByUser;
        log.info("allSubjectsByUser()");
        allSubjectsByUser = userDao.getAllSubjectsByUser(user.getId());
        log.debug("List of subject by user id: " + allSubjectsByUser);

        return allSubjectsByUser;
    }

    public boolean addUserToSpecialty(User user, Specialty specialty, boolean checked) {
        log.info("Start class UserService method addUserToSpecialty()");

        log.debug("User to add: " + user);
        log.debug("Specialty to add: " + specialty);
        if (!getAllSpecialtiesByUser(user).containsKey(specialty)) {
            log.info("There is no such user in table user_specialty");
            userDao.addUserToSpecialty(user, specialty, checked);
            return true;
        }

        log.info("There is user in table user_specialty");
        return false;
    }

    private HashMap<Specialty, Boolean> getAllSpecialtiesByUser(User user) {
        log.info("Start class UserService method getAllSpecialtiesByUser()");

        HashMap<Specialty, Boolean> specialtyBooalenHashMap =
                userDao.getAllSpecialtiesByUser(user.getId());
        log.debug("Map of specialties by user: " + specialtyBooalenHashMap);

        return specialtyBooalenHashMap;
    }

    public HashMap<Subject, BigDecimal> getAllCheckedSubjectsByUser(int id) {
        log.info("Start class UserService method getAllCheckedSubjectsByUser()");

        return userDao.getAllCheckedSubjectsByUser(id);
    }

    public ArrayList<User> getAll() {
        log.info("Start class UserService method getAll()");

        return userDao.getAll();
    }

    public ArrayList<User> getAllStudents(int id) {
        log.info("Start class UserService method getAllStudents()");

        return userDao.getAllStudents(id);
    }

    public ArrayList<Subject> getAllSubjectsByUser(int id) {
        log.info("Start class UserService method getAllSubjectsByUser()");

        return userDao.getAllSubjectsByUser(id);
    }

    public Optional<Specialty> getPassedSpecialtyByUser(int id) {
        log.info("Start class UserService method getPassedSpecialtyByUser()");
        Specialty specialty = null;
        if (userDao.getPassedSpecialtyByUser(id).isPresent()) {
            specialty = userDao.getPassedSpecialtyByUser(id).get();
            log.debug("Specialty: " + specialty);
        }
        return Optional.ofNullable(specialty);
    }

    @Override
    public void close() {
        ConnectionManager.getInstance().close(connection);
    }
}
