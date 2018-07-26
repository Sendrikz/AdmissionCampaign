package dao;

import model.connection.ConnectionManager;
import model.dao.UserDao;
import model.dao.UserJdbcDao;
import model.enteties.User;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

//    @Test
//    public void getAll() {
//        User user = new User("Petrenko", "Petro", "Petrovych",
//                "1998-02-12", "Kyiv", "petr@gmail.com", "123", 2);
//        userDao.add(user);
//        assertEquals(1, userDao.getAll().size());
//    }

}
