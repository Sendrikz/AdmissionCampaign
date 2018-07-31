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

    private Role setUpNewAdminRole() {
        return new Role("Адміністратор");
    }

    private Role setUpNewStudentRole() {
        return new Role("Студент");
    }

    @Test
    public void addTest() {
        Role role = setUpNewAdminRole();
        int initialId = role.getId();
        roleDao.add(role);
        assertNotEquals(initialId, role.getId());
    }

    @Test
    public void updateTest() {
        Role role = setUpNewStudentRole();
        roleDao.add(role);
        roleDao.update(role.getId(), "Адміністратор");
        assertNotEquals(role, roleDao.findById(role.getId()));
    }

    @Test
    public void deleteById() {
        Role role = setUpNewStudentRole();
        roleDao.add(role);
        roleDao.deleteById(role.getId());
        assertNull(roleDao.findById(role.getId()));
    }

    @Test
    public void clearAllRolesTest() {
        Role role = setUpNewStudentRole();
        Role roleAdmin = setUpNewAdminRole();
        roleDao.add(role);
        roleDao.add(roleAdmin);
        userDao.clearAllUsers();
        roleDao.clearAllRoles();
        assertEquals(0, roleDao.getAll().size());
    }

    @Test
    public void categoryIdIsReturnedCorrectly() {
        Role role = setUpNewStudentRole();
        int originalId = role.getId();
        roleDao.add(role);
        assertNotEquals(originalId, roleDao.findById(role.getId()).getId());
    }

    @Test
    public void getAllUsersByRoleTest() {
        Role role = setUpNewStudentRole();
        roleDao.add(role);
        User user = new User("Бойко", "Андрій", "Петрович",
                "1998-02-12", "Київ", "andriy@gmail.com", "123", role.getId());
        User user2 = new User("Гринчук", "Костянтин", "Вікторович",
                "1997-02-12", "Львів", "kost@gmail.com", "333", role.getId());
        User user3 = new User("Левченко", "Михайло", "Олегович",
                "1987-02-12", "Ніжин", "mych@gmail.com", "9876", role.getId());
        userDao.clearAllUsers();
        userDao.add(user);
        userDao.add(user2);
        assertEquals(2, roleDao.getAllUsersByRole(role.getId()).size());
        assertTrue(userDao.getAll().contains(user));
        assertTrue(userDao.getAll().contains(user2));
        assertFalse(roleDao.getAll().contains(user3));
    }
}
