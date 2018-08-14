package dao;

import model.enteties_enum.Roles;
import model.enteties_enum.Users;
import model.connection.ConnectionManager;
import model.dao.dao_interfaces.RoleDao;
import model.dao.dao_implementations.RoleJdbcDao;
import model.dao.dao_interfaces.UserDao;
import model.dao.dao_implementations.UserJdbcDao;
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
        con = new ConnectionManager().getConnectionToTestBD();
        roleDao = new RoleJdbcDao(con);
        userDao = new UserJdbcDao(con);
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
        return new Role(Roles.ADMINISTRATOR.getName());
    }

    private Role setUpNewStudentRole() {
        return new Role(Roles.STUDENT.getName());
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
        roleDao.update(role.getId(), Roles.ADMINISTRATOR.getName());
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
        User user = new User(Users.ANDRIY.getLastName(), Users.ANDRIY.getFirstName(),
                Users.ANDRIY.getPatronymic(), Users.ANDRIY.getBirthday(), Users.ANDRIY.getCity(),
                Users.ANDRIY.getEmail(), Users.ANDRIY.getPassword(), role.getId());
        User user2 = new User(Users.KOSTYA.getLastName(), Users.KOSTYA.getFirstName(),
                Users.KOSTYA.getPatronymic(), Users.KOSTYA.getBirthday(), Users.KOSTYA.getCity(),
                Users.KOSTYA.getEmail(), Users.KOSTYA.getPassword(), role.getId());
        User user3 = new User(Users.MUHAYLO.getLastName(), Users.MUHAYLO.getFirstName(),
                Users.MUHAYLO.getPatronymic(), Users.MUHAYLO.getBirthday(), Users.MUHAYLO.getCity(),
                Users.MUHAYLO.getEmail(), Users.MUHAYLO.getPassword(), role.getId());
        userDao.clearAllUsers();
        userDao.add(user);
        userDao.add(user2);
        assertEquals(2, roleDao.getAllUsersByRole(role.getId()).size());
        assertTrue(userDao.getAll().contains(user));
        assertTrue(userDao.getAll().contains(user2));
        assertFalse(roleDao.getAll().contains(user3));
    }
}
