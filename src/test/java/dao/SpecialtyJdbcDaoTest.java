package dao;

import model.builder.*;
import model.dao.*;
import model.enteties_enum.*;
import model.connection.ConnectionManager;
import model.dao.impl.*;
import model.enteties.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SpecialtyJdbcDaoTest {

    private SpecialtyDao specialtyDao;
    private SubjectDao subjectDao;
    private UserDao userDao;
    private RoleDao roleDao;
    private Connection connection;
    private UniversityDao universityDao;
    private Faculty faculty;
    private University university;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        specialtyDao = new SpecialtyJdbcDao(connection);
        FacultyDao facultyDao = new FacultyJdbcDao(connection);
        subjectDao = new SubjectJdbcDao(connection);
        userDao = new UserJdbcDao(connection);
        roleDao = new RoleJdbcDao(connection);
        universityDao = new UniversityJdbcDao(connection);
        university = new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
        universityDao.add(university);
        faculty = new FacultyBuilder().setName(Faculties.IT.getName()).setUniversityId(university.getId()).createFaculty();
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
        return new SpecialtyBuilder().setName(Specialties.ENGINEERING.getName()).setQuantityOfStudents(Specialties.ENGINEERING.getQuantityOfStudents()).setFacultyId(faculty.getId()).createSpecialty();
    }

    private Specialty setUpNewCompSpecialty() {
        return new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName()).setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents()).setFacultyId(faculty.getId()).createSpecialty();
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
        if (specialtyDao.findById(specialty.getId()).isPresent()) {
            assertNull(specialtyDao.findById(specialty.getId()));
        }
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
    public void addSpecialtyToSubjectTest() {
        Specialty specialty = setUpNewEngineeringSpecialty();
        specialtyDao.add(specialty);
        Subject math = new SubjectBuilder().setName(Subjects.MATH.getName()).setDuration(Subjects.MATH.getDuration()).createSubject();
        Subject language = new SubjectBuilder().setName(Subjects.UA_LANGUAGE.getName()).setDuration(Subjects.UA_LANGUAGE.getDuration()).createSubject();
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

    @Ignore
    public void addSpecialtyToUser() {
        Role role = new RoleBuilder().setName(Roles.STUDENT.getName()).createRole();
        roleDao.add(role);
        User user = new UserBuilder().setLastName(Users.ANDRIY.getLastName()).setFirstName(Users.ANDRIY.getFirstName()).setPatronymic(Users.ANDRIY.getPatronymic()).setBirthday(Users.ANDRIY.getBirthday()).setCity(Users.ANDRIY.getCity()).setEmail(Users.ANDRIY.getEmail()).setPassword(Users.ANDRIY.getPassword()).setRole(role.getId()).createUser();
        User user2 = new UserBuilder().setLastName(Users.KOSTYA.getLastName()).setFirstName(Users.KOSTYA.getFirstName()).setPatronymic(Users.KOSTYA.getPatronymic()).setBirthday(Users.KOSTYA.getBirthday()).setCity(Users.KOSTYA.getCity()).setEmail(Users.KOSTYA.getEmail()).setPassword(Users.KOSTYA.getPassword()).setRole(role.getId()).createUser();
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
