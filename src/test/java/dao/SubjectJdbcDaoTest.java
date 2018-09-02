package dao;

import model.builder.*;
import model.dao.*;
import model.enteties_enum.*;
import model.connection.ConnectionManager;
import model.dao.impl.*;
import model.enteties.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

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
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
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
        return new SubjectBuilder().setName(Subjects.MATH.getName()).setDuration(Subjects.MATH.getDuration()).createSubject();
    }

    private Subject setUpNewLanguageSubject() {
        return new SubjectBuilder().setName(Subjects.UA_LANGUAGE.getName()).setDuration(Subjects.UA_LANGUAGE.getDuration()).createSubject();
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
        assertEquals(Optional.empty(), subjectDao.findById(subject.getId()));
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
        University university = new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
        universityDao.add(university);
        Faculty faculty = new FacultyBuilder().setName(Faculties.IT.getName()).setUniversityId(university.getId()).createFaculty();
        facultyDao.add(faculty);
        Specialty specialty = new SpecialtyBuilder().setName(Specialties.ENGINEERING.getName()).setQuantityOfStudents(Specialties.ENGINEERING.getQuantityOfStudents()).setFacultyId(faculty.getId()).createSpecialty();
        specialtyDao.add(specialty);
        Specialty specialtyComp = new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName()).setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents()).setFacultyId(faculty.getId()).createSpecialty();
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
        Role role = new RoleBuilder().setName(Roles.STUDENT.getName()).createRole();
        roleDao.add(role);
        User user = new UserBuilder().setLastName(Users.ANDRIY.getLastName()).setFirstName(Users.ANDRIY.getFirstName()).setPatronymic(Users.ANDRIY.getPatronymic()).setBirthday(Users.ANDRIY.getBirthday()).setCity(Users.ANDRIY.getCity()).setEmail(Users.ANDRIY.getEmail()).setPassword(Users.ANDRIY.getPassword()).setRole(role.getId()).createUser();
        User user2 = new UserBuilder().setLastName(Users.KOSTYA.getLastName()).setFirstName(Users.KOSTYA.getFirstName()).setPatronymic(Users.KOSTYA.getPatronymic()).setBirthday(Users.KOSTYA.getBirthday()).setCity(Users.KOSTYA.getCity()).setEmail(Users.KOSTYA.getEmail()).setPassword(Users.KOSTYA.getPassword()).setRole(role.getId()).createUser();
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
