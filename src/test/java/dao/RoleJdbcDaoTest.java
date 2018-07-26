package dao;

import model.connection.ConnectionManager;
import model.dao.RoleDao;
import model.dao.RoleJdbcDao;
import model.enteties.Role;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotEquals;

public class RoleJdbcDaoTest {

    private RoleDao roleDao;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        roleDao = new RoleJdbcDao(connectionManager);
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

}
