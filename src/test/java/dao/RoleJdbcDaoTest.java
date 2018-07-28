package dao;

import model.connection.ConnectionManager;
import model.dao.RoleDao;
import model.dao.RoleJdbcDao;
import model.dao.UserDao;
import model.dao.UserJdbcDao;
import model.enteties.Role;
import model.enteties.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class RoleJdbcDaoTest {

    private RoleDao roleDao;
    private UserDao userDao;
    private Connection con;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        con = connectionManager.getConnectionToTestBD();
        roleDao = new RoleJdbcDao(con);
        userDao = new UserJdbcDao(con);
        roleDao.clearAllRoles();
    }

    @After
    public void tearDown() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTest() {
        Role role = new Role("Administrator");
        int initialId = role.getId();
        roleDao.add(role);
        assertNotEquals(initialId, role.getId());
    }

    @Test
    public void findByIdTest() {
        Role role = new Role("Administrator");
        roleDao.add(role);
        Role roleTwoTest = roleDao.findById(role.getId());
        assertEquals(role, roleTwoTest);
    }

    @Test
    public void updateTest() {
        Role role = new Role("Student");
        roleDao.add(role);
        roleDao.update(role.getId(), "Administrator");
        assertNotEquals(role, roleDao.findById(role.getId()));
    }

    @Test
    public void deleteById() {
        Role role = new Role("Student");
        roleDao.add(role);
        roleDao.deleteById(role.getId());
        assertNull(roleDao.findById(role.getId()));
    }

    @Test
    public void clearAllRolesTest() {
        Role role = new Role("Student");
        Role roleAdmin = new Role("Administrator");
        roleDao.add(role);
        roleDao.add(roleAdmin);
        roleDao.clearAllRoles();
        assertEquals(0, roleDao.getAll().size());
    }

    @Test
    public void getAllTest() {
        Role role = new Role("Student");
        Role roleAdmin = new Role("Administrator");
        roleDao.add(role);
        roleDao.add(roleAdmin);
        assertEquals(2, roleDao.getAll().size());
    }

    @Test
    public void categoryIdIsReturnedCorrectly() {
        Role role = new Role("Student");
        int originalId = role.getId();
        roleDao.add(role);
        assertNotEquals(originalId, roleDao.findById(role.getId()).getId());
    }

    @Test
    public void getAllUsersByRoleTest() {
        Role role = new Role("Student");
        roleDao.add(role);
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", role.getId());
        User user2 = new User("Wilson", "Mary", "Johnson",
                "1987-02-12", "London", "mary@gmail.com", "333", role.getId());
        User user3 = new User("Holmes", "Cherlock", "Johnson",
                "1977-02-12", "London", "holmes@gmail.com", "9876", role.getId());
        userDao.clearAllUsers();
        userDao.add(user);
        userDao.add(user2);
        assertEquals(2, roleDao.getAllUsersByRole(role.getId()).size());
        assertTrue(userDao.getAll().contains(user));
        assertTrue(userDao.getAll().contains(user2));
        assertFalse(roleDao.getAll().contains(user3));
        userDao.deleteById(user.getId());
        userDao.deleteById(user2.getId());
    }
}
