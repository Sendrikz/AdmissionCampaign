package dao;

import model.enteties_enum.*;
import model.connection.ConnectionManager;
import model.dao.dao_implementations.*;
import model.dao.dao_interfaces.*;
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
    private UniversityDao universityDao;
    private Connection con;

    @Before
    public void setUp() {
        RoleDao roleDao;
        con = ConnectionManager.getInstance().getConnectionToTestBD();
        userDao = new UserJdbcDao(con);
        roleDao = new RoleJdbcDao(con);
        subjectDao = new SubjectJdbcDao(con);
        facultyDao = new FacultyJdbcDao(con);
        specialtyDao = new SpecialtyJdbcDao(con);
        universityDao = new UniversityJdbcDao(con);
        role = new Role(Roles.STUDENT.getName());
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

    private User setUpNewAndriy() {
        return new User(Users.ANDRIY.getLastName(), Users.ANDRIY.getFirstName(),
                Users.ANDRIY.getPatronymic(), Users.ANDRIY.getBirthday(), Users.ANDRIY.getCity(),
                Users.ANDRIY.getEmail(), Users.ANDRIY.getPassword(), role.getId());
    }

    private User setUpNewKostya() {
        return new User(Users.KOSTYA.getLastName(), Users.KOSTYA.getFirstName(),
                Users.KOSTYA.getPatronymic(), Users.KOSTYA.getBirthday(), Users.KOSTYA.getCity(),
                Users.KOSTYA.getEmail(), Users.KOSTYA.getPassword(), role.getId());
    }

    @Test
    public void addTest() {
        User user = setUpNewAndriy();
        int originUserId = user.getId();
        userDao.add(user);
        assertNotEquals(originUserId, user.getId());
    }

    @Test
    public void findById() {
        User user = setUpNewKostya();
        userDao.add(user);
        User foundUser = userDao.findById(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    public void updateEmailTest() {
        // TODO Generate different users
        User user = setUpNewKostya();
       // User user = userDao.findById(5);
        userDao.add(user);
        userDao.updateEmail(user.getId(),"ok@gmail.com");
        User foundUser = userDao.findById(user.getId());
        assertNotEquals(user, foundUser);
    }

    @Test
    public void updatePasswordTest() {
        // TODO Generate different users
        User user = setUpNewKostya();
        //User user = userDao.findById(5);
        userDao.add(user);
        userDao.updatePassword(user.getId(),"5555");
        User foundUser = userDao.findById(user.getId());
        assertNotEquals(user, foundUser);
    }

    @Test
    public void NoSuchUserTest() {
        assertNull(userDao.findById(0));
    }

    @Test
    public void deleteByIdTest() {
        // TODO Generate different users
         User user = setUpNewAndriy();
         userDao.add(user);
        userDao.deleteById(user.getId());
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    public void clearAllUsersTest() {
        userDao.clearAllUsers();
        User user = setUpNewAndriy();
        User user2 = setUpNewKostya();
        userDao.add(user);
        userDao.add(user2);
        assertEquals(2, userDao.getAll().size());
    }

    @Test
    public void updateTest() {
        User user = setUpNewKostya();
        userDao.add(user);
        userDao.update(user.getId(), Users.MUHAYLO.getFirstName(), user.getFirstName(),
                user.getPatronymic(), user.getBirthday(), Users.MUHAYLO.getCity(), user.getRole());
        assertNotEquals(user, userDao.findById(user.getId()));
    }

    @Test
    public void addUserToSubjectTest() {
        User user = setUpNewAndriy();;
        userDao.add(user);
        Subject subjectMath = new Subject(Subjects.MATH.getName(), Subjects.MATH.getDuration());
        subjectDao.add(subjectMath);
        Subject subjectUA = new Subject(Subjects.UA_LANGUAGE.getName(),
                Subjects.UA_LANGUAGE.getDuration());
        subjectDao.add(subjectUA);
        userDao.addUserToSubject(user, subjectUA, false, new BigDecimal(0));
        userDao.addUserToSubject(user, subjectMath, false, new BigDecimal(0));
        assertEquals(2, userDao.getAllSubjectsByUser(user.getId()).size());
        BigDecimal grade = new BigDecimal(90.8);
        userDao.updateUserToSubject(user.getId(), subjectUA.getId(), true, grade);
        assertTrue(userDao.getAllCheckedSubjectsByUser(user.getId()).containsKey(subjectUA));
        assertEquals(1, userDao.getAllUncheckedSubjectsByUser(user.getId()).size());
    }

    @Test
    public void addUserToSpecialtyTest() {
        User user = setUpNewKostya();;
        userDao.add(user);
        University university = new University(Universities.NaUKMA.getName(), Universities.NaUKMA.getAddress(),
                Universities.NaUKMA.getCity());
        universityDao.add(university);
        Faculty faculty = new Faculty(Faculties.IT.getName(), university.getId());
        facultyDao.add(faculty);
        Specialty specialty = new Specialty(Specialties.ENGINEERING.getName(),
                Specialties.ENGINEERING.getQuantityOfStudents(), faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty(Specialties.COMPUTER_SCIENCE.getName(),
                Specialties.COMPUTER_SCIENCE.getQuantityOfStudents(), faculty.getId());
        specialtyDao.add(specialtyComp);
        userDao.addUserToSpecialty(user, specialty, false);
        userDao.addUserToSpecialty(user, specialtyComp, false);
        assertEquals(2, userDao.getAllSpecialtiesByUser(user.getId()).size());
        userDao.updateUserToSpecialty(user.getId(), specialtyComp.getId(), true);
        assertTrue(userDao.getAllSpecialtiesByUser(user.getId()).get(specialtyComp));
    }

}
