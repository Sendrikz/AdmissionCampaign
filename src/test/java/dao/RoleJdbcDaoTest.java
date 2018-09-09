package dao;

import model.builder.UserBuilder;
import model.builder.RoleBuilder;
import model.enteties_enum.Roles;
import model.enteties_enum.Users;
import model.connection.ConnectionManager;
import model.dao.RoleDao;
import model.dao.impl.RoleJdbcDao;
import model.dao.UserDao;
import model.dao.impl.UserJdbcDao;
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

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class RoleJdbcDaoTest {

    private RoleDao roleDao;
    private UserDao userDao;
    private Connection con;

    @Before
    public void setUp() {
        con = ConnectionManager.getInstance().getConnectionToTestBD();
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
        return new RoleBuilder().setName(Roles.ADMINISTRATOR.getName()).createRole();
    }

    private Role setUpNewStudentRole() {
        return new RoleBuilder().setName(Roles.STUDENT.getName()).createRole();
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
        if (roleDao.findById(role.getId()).isPresent()) {
            assertNull(roleDao.findById(role.getId()));
        }
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
        if (roleDao.findById(role.getId()).isPresent()) {
            assertNotEquals(originalId, roleDao.findById(role.getId()).get().getId());
        }
    }

    @Test
    public void getAllUsersByRoleTest() {
        Role role = setUpNewStudentRole();
        roleDao.add(role);
        User user = new UserBuilder().setLastName(Users.ANDRIY.getLastName()).setFirstName(Users.ANDRIY.getFirstName()).setPatronymic(Users.ANDRIY.getPatronymic()).setBirthday(Users.ANDRIY.getBirthday()).setCity(Users.ANDRIY.getCity()).setEmail(Users.ANDRIY.getEmail()).setPassword(Users.ANDRIY.getPassword()).setRole(role.getId()).createUser();
        User user2 = new UserBuilder().setLastName(Users.KOSTYA.getLastName()).setFirstName(Users.KOSTYA.getFirstName()).setPatronymic(Users.KOSTYA.getPatronymic()).setBirthday(Users.KOSTYA.getBirthday()).setCity(Users.KOSTYA.getCity()).setEmail(Users.KOSTYA.getEmail()).setPassword(Users.KOSTYA.getPassword()).setRole(role.getId()).createUser();
        User user3 = new UserBuilder().setLastName(Users.MUHAYLO.getLastName()).setFirstName(Users.MUHAYLO.getFirstName()).setPatronymic(Users.MUHAYLO.getPatronymic()).setBirthday(Users.MUHAYLO.getBirthday()).setCity(Users.MUHAYLO.getCity()).setEmail(Users.MUHAYLO.getEmail()).setPassword(Users.MUHAYLO.getPassword()).setRole(role.getId()).createUser();
        userDao.clearAllUsers();
        userDao.add(user);
        userDao.add(user2);
        assertEquals(2, roleDao.getAllUsersByRole(role.getId()).size());
        assertTrue(userDao.getAll().contains(user));
        assertTrue(userDao.getAll().contains(user2));
        assertFalse(roleDao.getAll().contains(user3));
    }
}
