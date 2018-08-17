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

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SubjectJdbcDaoTest {

    private SubjectDao subjectDao;
    private SpecialtyDao specialtyDao;
    private FacultyDao facultyDao;
    private UserDao userDao;
    private RoleDao roleDao;
    private UniversityDao universityDao;
    private Connection connection;

    @Before
    public void setUp() {
        connection = new ConnectionManager().getConnectionToTestBD();
        subjectDao = new SubjectJdbcDao(connection);
        specialtyDao = new SpecialtyJdbcDao(connection);
        userDao = new UserJdbcDao(connection);
        roleDao = new RoleJdbcDao(connection);
        facultyDao = new FacultyJdbcDao(connection);
        universityDao = new UniversityJdbcDao(connection);
    }

    @After
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Subject setUpNewMathSubject() {
        return new Subject(Subjects.MATH.getName(), Subjects.MATH.getDuration());
    }

    private Subject setUpNewLanguageSubject() {
        return new Subject(Subjects.UA_LANGUAGE.getName(), Subjects.UA_LANGUAGE.getDuration());
    }

    @Test
    public void addTest() {
        Subject subject = setUpNewMathSubject();
        int originId = subject.getId();
        subjectDao.add(subject);
        assertNotEquals(originId, subject.getId());
    }

    @Test
    public void updateTest() {
        Subject subject = setUpNewMathSubject();
        subjectDao.add(subject);
        subjectDao.update(subject.getId(), subject.getName(), 240);
        assertNotEquals(subject, subjectDao.findById(subject.getId()));
    }

    @Test
    public void deleteByIdTest() {
        Subject subject = setUpNewMathSubject();
        subjectDao.add(subject);
        subjectDao.deleteById(subject.getId());
        assertNull(subjectDao.findById(subject.getId()));
    }

    @Test
    public void clearAllSubjectsTest() {
        Subject subjectMath = setUpNewMathSubject();
        subjectDao.add(subjectMath);
        Subject subjectLanguage = setUpNewLanguageSubject();
        subjectDao.add(subjectLanguage);
        userDao.clearAllUsers();
        specialtyDao.clearAllSpecialties();
        subjectDao.clearAllSubjects();
        assertEquals(0, subjectDao.getAll().size());
    }

    @Test
    public void addSubjectToSpecialtyTest() {
        Subject math = setUpNewMathSubject();
        subjectDao.add(math);
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
        subjectDao.addSubjectToSpecialty(math, specialty, new BigDecimal(0.5));
        subjectDao.addSubjectToSpecialty(math, specialtyComp, new BigDecimal(0.5));
        assertEquals(2, subjectDao.getAllSpecialtiesBySubject(math.getId()).size());
        subjectDao.updateSubjectToSpecialty(math.getId(), specialty.getId(), new BigDecimal(0.6));
        assertNotEquals(0.5, subjectDao.getAllSpecialtiesBySubject(math.getId()).get(specialty));
    }

    @Test
    public void addSubjectToUserTest() {
        Subject math = setUpNewMathSubject();
        subjectDao.add(math);
        Role role = new Role(Roles.STUDENT.getName());
        roleDao.add(role);
        User user = new User(Users.ANDRIY.getLastName(), Users.ANDRIY.getFirstName(),
                Users.ANDRIY.getPatronymic(), Users.ANDRIY.getBirthday(), Users.ANDRIY.getCity(),
                Users.ANDRIY.getEmail(), Users.ANDRIY.getPassword(), role.getId());
        User user2 = new User(Users.KOSTYA.getLastName(), Users.KOSTYA.getFirstName(),
                Users.KOSTYA.getPatronymic(), Users.KOSTYA.getBirthday(), Users.KOSTYA.getCity(),
                Users.KOSTYA.getEmail(), Users.KOSTYA.getPassword(), role.getId());
        userDao.add(user);
        userDao.add(user2);
        subjectDao.addSubjectToUser(math, user, false, new BigDecimal(0));
        subjectDao.addSubjectToUser(math, user2, false, new BigDecimal(0));
        assertEquals(2, subjectDao.getAllUsersBySubject(math.getId()).size());
        subjectDao.updateSubjectToUser(math.getId(), user.getId(), true, new BigDecimal(87));
        assertEquals(1, subjectDao.getAllUsersWithCheckedSubjects(math.getId()).size());
        assertEquals(1, subjectDao.getAllUsersWithUncheckedSubject(math.getId()).size());
    }

}
