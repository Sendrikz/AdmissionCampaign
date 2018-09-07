package commands;

import controller.commands.LoginCommand;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.Strings;
import utils.property_loaders.LoadConfigProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import static org.junit.Assert.assertEquals;

public class LoginCommandTest extends Mockito {

    private Connection connection;
    private Role role;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        role = new RoleBuilder().setName(Roles.STUDENT.getName()).createRole();
        RoleDao roleDao = DaoFactory.getRoleDao(connection);
        roleDao.add(role);
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

    @Test
    public void executeLoginFailTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        User user = setUpNewKostya();
        UserDao userDao = DaoFactory.getUserDao(connection);
        userDao.clearAllUsers();
        LoginCommand loginCommand = new LoginCommand(true);
        when(request.getParameter(Strings.LOGIN)).thenReturn(user.getEmail());
        when(request.getParameter(Strings.PASSWORD)).thenReturn(user.getPassword());
        when(request.getSession()).thenReturn(session);

        assertEquals(LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_INDEX),
                loginCommand.execute(request));

    }

//    @Test(expected = NoSuchSpecialtyException.class)
//    public void executeAuthorizeUserTest() {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpSession session = mock(HttpSession.class);
//        User user = setUpNewKostya();
//        user.setPassword("677589babd592d8efdd32c2b0e05a2e9");
//        UserDao userDao = DaoFactory.getUserDao(connection);
//        userDao.clearAllUsers();
//        userDao.add(user);
//        user.setPassword("Okay1919");
//        LoginCommand loginCommand = new LoginCommand(connection);
//        when(request.getParameter(Strings.LOGIN)).thenReturn(user.getEmail());
//        when(request.getParameter(Strings.PASSWORD)).thenReturn(user.getPassword());
//        when(request.getSession()).thenReturn(session);
//        when(request.getSession().getAttribute(Strings.LOGINED_USER)).thenReturn(user);
//        loginCommand.execute(request);
//    }

}
