package services;

import services.exceptions.NoSuchRoleException;
import utils.Strings;
import model.builder.UserBuilder;
import model.connection.ConnectionManager;
import model.dao.factory.DaoFactory;
import model.dao.RoleDao;
import model.dao.SubjectDao;
import model.dao.UserDao;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.exceptions.NoSuchUserException;

import java.io.Closeable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

public class LoginService implements Closeable {

    private Connection connection;
    private UserDao userDao;
    private RoleDao roleDao;
    private static final Logger log = Logger.getLogger(LoginService.class);

    public LoginService() {
        connection = ConnectionManager.getInstance().getConnection();
        userDao = DaoFactory.getUserDao(connection);
        roleDao = DaoFactory.getRoleDao(connection);
    }

    public User checkLogin(String login, String password) throws NoSuchUserException {
        log.info("Start class LoginLogic checkLogin()");

        Optional<User> user = userDao.getUserByEmailAndPassword(login, password);
        if (user.isPresent()) {
            log.info("Find user");
            return user.get();
        }

        log.info("No such user");
        throw new NoSuchUserException();
    }

    public void addUser(String lastName, String firstName, String patronymic,
                               String birthday, String city, String email, String password) {
        log.info("Start class LoginLogic addUser()");

        User user = new UserBuilder().setLastName(lastName).setFirstName(firstName)
                .setPatronymic(patronymic).setBirthday(birthday).setCity(city)
                .setEmail(email).setPassword(password).setRole(roleDao.findIdByRoleName(Strings.STUDENT))
                .createUser();

        userDao.add(user);
    }

    public String getRoleById(int roleId) throws NoSuchRoleException {
        log.info("Start class LoginLogic getRoleById()");
        if (roleDao.findById(roleId).isPresent()) {
            return roleDao.findById(roleId).get().getName();
        }
        throw new NoSuchRoleException();
    }

    public ArrayList<Subject> getAllSubjects() {
        log.info("Start class LoginLogic getAllSubjects()");
        SubjectDao subject = DaoFactory.getSubjectDao(connection);

        return subject.getAll();
    }

    @Override
    public void close() {
        ConnectionManager.getInstance().close(connection);
    }

}
