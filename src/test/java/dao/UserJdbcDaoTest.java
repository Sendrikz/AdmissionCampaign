package dao;

import model.connection.ConnectionManager;
import model.dao.*;
import model.enteties.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class UserJdbcDaoTest {

    private UserDao userDao;
    private Role role;
    private SubjectDao subjectDao;
    private FacultyDao facultyDao;
    private SpecialtyDao specialtyDao;
    private Connection con;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        RoleDao roleDao = null;
        con = connectionManager.getConnectionToTestBD();
        userDao = new UserJdbcDao(con);
        roleDao = new RoleJdbcDao(con);
        subjectDao = new SubjectJdbcDao(con);
        facultyDao = new FacultyJdbcDao(con);
        specialtyDao = new SpecialtyJdbcDao(con);
        role = new Role("Student");
        roleDao.add(role);
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
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", role.getId());
        int originUserId = user.getId();
        userDao.add(user);
        assertNotEquals(originUserId, user.getId());
    }

    @Test
    public void findById() {
        User user = new User("Wilson", "Mary", "Johnson",
                "1987-02-12", "London", "mary@gmail.com", "333", role.getId());
        userDao.add(user);
        User foundUser = userDao.findById(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    public void updateEmailTest() {
        // TODO Generate different users
        User user = new User("Wilson", "Mary", "Johnson",
                "1987-02-12", "London", "mary@gmail.com", "333", role.getId());
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
                 "1987-02-12", "London", "mary@gmail.com", "333", role.getId());
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
                 "1987-02-12", "London", "mary@gmail.com", "333", role.getId());
         userDao.add(user);
        userDao.deleteById(user.getId());
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    public void clearAllUsersTest() {
        userDao.clearAllUsers();
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", role.getId());
        User user2 = new User("Wilson", "Mary", "Johnson",
                "1987-02-12", "London", "mary@gmail.com", "333", role.getId());
        userDao.add(user);
        userDao.add(user2);
        assertEquals(2, userDao.getAll().size());
    }

    @Test
    public void updateTest() {
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", role.getId());
        userDao.add(user);
        userDao.update(user.getId(), "Smith", user.getFirstName(), user.getPatronymic(),
                user.getBirthday(), "USA", user.getRole());
        assertNotEquals(user, userDao.findById(user.getId()));
    }

    @Test
    public void addUserToSubjectTest() {
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", role.getId());
        userDao.add(user);
        Subject subjectJava = new Subject("Java programming", 120);
        subjectDao.add(subjectJava);
        Subject subjectC = new Subject("C++", 120);
        subjectDao.add(subjectC);
        userDao.addUserToSubject(user, subjectC, false, new BigDecimal(0));
        userDao.addUserToSubject(user, subjectJava, false, new BigDecimal(0));
        assertEquals(2, userDao.getAllSubjectsByUser(user.getId()).size());
        BigDecimal grade = new BigDecimal(90.8);
        userDao.updateUserToSubject(user.getId(), subjectC.getId(), true, grade);
        assertTrue(userDao.getAllCheckedSubjectsByUser(user.getId()).containsKey(subjectC));
        assertEquals(1, userDao.getAllUncheckedSubjectsByUser(user.getId()).size());
    }

    @Test
    public void addUserToSpecialtyTest() {
        User user = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", role.getId());
        userDao.add(user);
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty("Computer science", 60,
                faculty.getId());
        specialtyDao.add(specialtyComp);
        userDao.addUserToSpecialty(user, specialty, false);
        userDao.addUserToSpecialty(user, specialtyComp, false);
        assertEquals(2, userDao.getAllSpecialtiesByUser(user.getId()).size());
        userDao.updateUserToSpecialty(user.getId(), specialtyComp.getId(), true);
        assertTrue(userDao.getAllSpecialtiesByUser(user.getId()).get(specialtyComp));
    }

}
