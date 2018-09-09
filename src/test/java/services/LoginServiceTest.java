package services;

import model.builder.RoleBuilder;
import model.builder.UserBuilder;
import model.connection.ConnectionManager;
import model.dao.RoleDao;
import model.dao.UserDao;
import model.dao.factory.DaoFactory;
import model.enteties.Role;
import model.enteties.User;
import model.enteties_enum.Roles;
import model.enteties_enum.Users;
import org.junit.*;
import services.exceptions.NoSuchRoleException;
import services.exceptions.NoSuchUserException;

import java.sql.Connection;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class LoginServiceTest {

    private Connection connection;
    private Role role;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        role = new RoleBuilder().setName(Roles.STUDENT.getName()).createRole();
    }

    @After
    public void tearDown() {
        ConnectionManager.getInstance().close(connection);
    }

    private User setUpNewKostya() {
        return new UserBuilder()
                .setLastName(Users.KOSTYA.getLastName())
                .setFirstName(Users.KOSTYA.getFirstName())
                .setPatronymic(Users.KOSTYA.getPatronymic())
                .setBirthday(Users.KOSTYA.getBirthday())
                .setCity(Users.KOSTYA.getCity())
                .setEmail(Users.KOSTYA.getEmail())
                .setPassword(Users.KOSTYA.getPassword())
                .setRole(role.getId())
                .createUser();
    }

    @Test(expected = NoSuchUserException.class)
    public void checkLoginTest() throws NoSuchUserException {
        UserDao userDao = DaoFactory.getUserDao(connection);
        userDao.clearAllUsers();
        User user = setUpNewKostya();
        try (LoginService loginService = new LoginService(true)) {
            loginService.checkLogin(user.getEmail(), user.getPassword());
        }
    }

    @Test(expected = NoSuchRoleException.class)
    public void getRoleByIdTest() throws NoSuchRoleException {
        RoleDao roleDao = DaoFactory.getRoleDao(connection);
        roleDao.clearAllRoles();
        try (LoginService loginService = new LoginService(true)) {
            loginService.getRoleById(role.getId());
        }
    }

}
