package dao;

import model.builder.FacultyBuilder;
import model.builder.SpecialtyBuilder;
import model.dao.impl.UniversityJdbcDao;
import model.dao.impl.UserJdbcDao;
import model.dao.UniversityDao;
import model.dao.UserDao;
import model.enteties.*;
import model.builder.UniversityBuilder;
import model.enteties_enum.Faculties;
import model.enteties_enum.Specialties;
import model.connection.ConnectionManager;
import model.dao.FacultyDao;
import model.dao.impl.FacultyJdbcDao;
import model.dao.SpecialtyDao;
import model.dao.impl.SpecialtyJdbcDao;
import model.enteties_enum.Universities;
import org.junit.*;

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

public class FacultyJdbcDaoTest {

    private FacultyDao facultyDao;
    private SpecialtyDao specialtyDao;
    private UserDao userDao;
    private Connection connection;
    private University university;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        facultyDao = new FacultyJdbcDao(connection);
        specialtyDao = new SpecialtyJdbcDao(connection);
        university = new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
        UniversityDao universityDao = new UniversityJdbcDao(connection);
        universityDao.add(university);
        userDao = new UserJdbcDao(connection);
    }

    @After
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Faculty setUpNewITFaculty() {
        return new FacultyBuilder().setName(Faculties.IT.getName()).setUniversityId(university.getId()).createFaculty();
    }

    private Faculty setUpNewEconomicFaculty() {
        return new FacultyBuilder().setName(Faculties.ECONOMIC.getName()).setUniversityId(university.getId()).createFaculty();
    }

    @Test
    public void addTest() {
        Faculty faculty = setUpNewITFaculty();
        int originId = faculty.getId();
        facultyDao.add(faculty);
        assertNotEquals(originId, faculty.getId());
    }

    @Test
    public void clearAllFacultiesTest() {
        Faculty facultyEconomy = setUpNewEconomicFaculty();
        Faculty facultyIT = setUpNewITFaculty();
        facultyDao.add(facultyEconomy);
        facultyDao.add(facultyIT);
        userDao.clearAllUsers();
        specialtyDao.clearAllSpecialties();
        facultyDao.clearAllFaculties();
        assertEquals(0, facultyDao.getAll().size());
    }

    @Test
    public void updateTest() {
        Faculty faculty = setUpNewITFaculty();
        facultyDao.add(faculty);
        facultyDao.update(faculty.getId(), Faculties.BIOLOGY.getName(), university.getId());
        assertNotEquals(faculty, facultyDao.findById(faculty.getId()));
    }

    @Test
    public void deleteById() {
        Faculty faculty = setUpNewEconomicFaculty();
        facultyDao.add(faculty);
        facultyDao.deleteById(faculty.getId());
        if (facultyDao.findById(faculty.getId()).isPresent()) {
            assertNull(facultyDao.findById(faculty.getId()));
        }
    }

    @Test
    public void getAllSpecialtiesByFacultyTest() {
        specialtyDao.clearAllSpecialties();
        Faculty faculty = setUpNewITFaculty();
        facultyDao.add(faculty);
        Faculty facultyEconomy = setUpNewEconomicFaculty();
        facultyDao.add(facultyEconomy);
        Specialty specialty = new SpecialtyBuilder().setName(Specialties.ENGINEERING.getName()).setQuantityOfStudents(Specialties.ENGINEERING.getQuantityOfStudents()).setFacultyId(faculty.getId()).createSpecialty();
        specialtyDao.add(specialty);
        Specialty specialtyComp = new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName()).setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents()).setFacultyId(faculty.getId()).createSpecialty();
        specialtyDao.add(specialtyComp);
        Specialty market = new SpecialtyBuilder().setName(Specialties.MARKETING.getName()).setQuantityOfStudents(Specialties.MARKETING.getQuantityOfStudents()).setFacultyId(facultyEconomy.getId()).createSpecialty();
        specialtyDao.add(market);
        Assert.assertEquals(2, facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).size());
        assertTrue(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(specialty));
        assertTrue(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(specialtyComp));
        assertFalse(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(market));
    }

}
