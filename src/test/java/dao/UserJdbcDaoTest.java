package dao;

import model.connection.ConnectionManager;
import model.dao.UserDao;
import model.dao.UserJdbcDao;
import model.enteties.User;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class UserJdbcDaoTest {

    private UserDao userDao;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        userDao = new UserJdbcDao(connectionManager);
    }

    @Test
    public void addTest() {
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", 2);
        int originUserId = user.getId();
        userDao.add(user);
        assertNotEquals(originUserId, user.getId());
    }

    @Test
    public void findById() {
        User user = new User("Wilson", "Mary", "Johnson",
                "1987-02-12", "London", "mary@gmail.com", "333", 2);
        userDao.add(user);
        User foundUser = userDao.findById(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    public void updateEmailTest() {
        // TODO Generate different users
        User user = new User("Wilson", "Mary", "Johnson",
                "1987-02-12", "London", "mary@gmail.com", "333", 2);
       // User user = userDao.findById(5);
        userDao.add(user);
        userDao.updateEmail(user.getId(),"ok@gmail.com");
        User foundUser = userDao.findById(user.getId());
        assertNotEquals(user, foundUser);
    }

    @Test
    public void updatePasswordTest() {
        // TODO Generate different users
        User user = new User("Wilson", "Mary", "Johnson",
                 "1987-02-12", "London", "mary@gmail.com", "333", 2);
        //User user = userDao.findById(5);
        userDao.add(user);
        userDao.updatePassword(user.getId(),"5555");
        User foundUser = userDao.findById(user.getId());
        assertNotEquals(user, foundUser);
    }

    @Test
    public void NoSuchUserTest() {
        assertEquals(null, userDao.findById(0));
    }

    @Test
    public void deleteByIdTest() {
        // TODO Generate different users
         User user = new User("Wilson", "Mary", "Johnson",
                 "1987-02-12", "London", "mary@gmail.com", "333", 2);
         userDao.add(user);
        userDao.deleteById(user.getId());
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    public void clearAllUsersTest() {
        userDao.clearAllUsers();
        assertEquals(0, userDao.getAll().size());
    }

    // TODO Think about how realise getAll()
    @Test
    public void getAll() {
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", 2);
        userDao.add(user);
        assertEquals(1, userDao.getAll().size());
    }

}
