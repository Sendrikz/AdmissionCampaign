package dao;

import enums.*;
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

public class SpecialtyJdbcDaoTest {

    private SpecialtyDao specialtyDao;
    private FacultyDao facultyDao;
    private SubjectDao subjectDao;
    private UserDao userDao;
    private RoleDao roleDao;
    private Connection connection;
    private UniversityDao universityDao;
    private Faculty faculty;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        specialtyDao = new SpecialtyJdbcDao(connection);
        facultyDao = new FacultyJdbcDao(connection);
        subjectDao = new SubjectJdbcDao(connection);
        userDao = new UserJdbcDao(connection);
        roleDao = new RoleJdbcDao(connection);
        universityDao = new UniversityJdbcDao(connection);
        faculty = new Faculty(Faculties.IT.getName());
        facultyDao.add(faculty);
    }

    @After
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Specialty setUpNewEngineeringSpecialty() {
        return new Specialty(Specialties.ENGINEERING.getName(),
                Specialties.ENGINEERING.getQuantityOfStudents(), faculty.getId());
    }

    private Specialty setUpNewCompSpecialty() {
        return new Specialty(Specialties.COMPUTER_SCIENCE.getName(),
                Specialties.COMPUTER_SCIENCE.getQuantityOfStudents(), faculty.getId());
    }

    @Test
    public void addTest() {
        Specialty specialty = setUpNewEngineeringSpecialty();
        int originId = specialty.getId();
        specialtyDao.add(specialty);
        assertNotEquals(originId, specialty.getId());
    }

    @Test
    public void updateTest() {
        Specialty specialty = setUpNewEngineeringSpecialty();
        specialtyDao.add(specialty);
        specialtyDao.update(specialty.getId(), specialty.getName(), 90,
                specialty.getFacultyId());
        assertNotEquals(specialty, specialtyDao.findById(specialty.getId()));
    }

    @Test
    public void deleteByIdTest() {
        Specialty specialty = setUpNewEngineeringSpecialty();
        specialtyDao.add(specialty);
        specialtyDao.deleteById(specialty.getId());
        assertNull(specialtyDao.findById(specialty.getId()));
    }

    @Test
    public void clearAllSpecialtiesTest() {
        Specialty specialty = setUpNewEngineeringSpecialty();
        specialtyDao.add(specialty);
        Specialty specialtyComp = setUpNewCompSpecialty();
        specialtyDao.add(specialtyComp);
        userDao.clearAllUsers();
        specialtyDao.clearAllSpecialties();
        assertEquals(0, specialtyDao.getAll().size());
    }

    @Test
    public void addSpecialtyToUniversityTest() {
        University NaUKMA = new University(Universities.NaUKMA.getName(),
                Universities.NaUKMA.getAddress());
        University university = new University(Universities.KPI.getName(),
                Universities.KPI.getAddress());
        universityDao.add(NaUKMA);
        universityDao.add(university);
        Specialty specialty = setUpNewEngineeringSpecialty();
        specialtyDao.add(specialty);
        specialtyDao.addSpecialtyToUniversity(specialty, NaUKMA);
        specialtyDao.addSpecialtyToUniversity(specialty, university);
        assertEquals(2, specialtyDao.getAllUniversitiesBySpecialty(specialty.getId()).size());
    }

    @Test
    public void addSpecialtyToSubjectTest() {
        Specialty specialty = setUpNewEngineeringSpecialty();
        specialtyDao.add(specialty);
        Subject math = new Subject(Subjects.MATH.getName(), Subjects.MATH.getDuration());
        Subject language = new Subject(Subjects.UA_LANGUAGE.getName(),
                Subjects.UA_LANGUAGE.getDuration());
        subjectDao.add(math);
        subjectDao.add(language);
        specialtyDao.addSpecialtyToSubject(specialty, math, new BigDecimal(0.5));
        specialtyDao.addSpecialtyToSubject(specialty, language, new BigDecimal(0.2));
        assertEquals(2, specialtyDao.getAllSubjectsOfSpecialty(specialty.getId()).size());
        specialtyDao.updateSpecialtyToSubject(specialty.getId(), language.getId(),
                new BigDecimal(0.3));
        assertNotEquals(new BigDecimal(0.2),
                specialtyDao.getAllSubjectsOfSpecialty(specialty.getId()).get(language));
    }

    @Test
    public void addSpecialtyToUser() {
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
        Specialty specialty = setUpNewEngineeringSpecialty();
        specialtyDao.add(specialty);

        specialtyDao.addSpecialtyToUser(specialty, user, false);
        specialtyDao.addSpecialtyToUser(specialty, user2, false);
        assertEquals(2, specialtyDao.getAllUsersOfSpecialty(specialty.getId()).size());
        specialtyDao.updateSpecialtyToUser(specialty.getId(), user.getId(), true);
        assertEquals(true, specialtyDao.getAllUsersOfSpecialty(specialty.getId()).get(user));
    }

}
