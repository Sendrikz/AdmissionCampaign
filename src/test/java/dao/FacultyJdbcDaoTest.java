package dao;

import model.dao.dao_implementations.UniversityJdbcDao;
import model.dao.dao_implementations.UserJdbcDao;
import model.dao.dao_interfaces.UniversityDao;
import model.dao.dao_interfaces.UserDao;
import model.enteties.University;
import model.enteties_enum.Faculties;
import model.enteties_enum.Specialties;
import model.connection.ConnectionManager;
import model.dao.dao_interfaces.FacultyDao;
import model.dao.dao_implementations.FacultyJdbcDao;
import model.dao.dao_interfaces.SpecialtyDao;
import model.dao.dao_implementations.SpecialtyJdbcDao;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties_enum.Universities;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

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
        university = new University(Universities.NaUKMA.getName(),
                Universities.NaUKMA.getAddress(), Universities.NaUKMA.getCity());
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
        return new Faculty(Faculties.IT.getName(), university.getId());
    }

    private Faculty setUpNewEconomicFaculty() {
        return new Faculty(Faculties.ECONOMIC.getName(),
                university.getId());
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
        assertNull(facultyDao.findById(faculty.getId()));
    }

    @Test
    public void getAllSpecialtiesByFacultyTest() {
        specialtyDao.clearAllSpecialties();
        Faculty faculty = setUpNewITFaculty();
        facultyDao.add(faculty);
        Faculty facultyEconomy = setUpNewEconomicFaculty();
        facultyDao.add(facultyEconomy);
        Specialty specialty = new Specialty(Specialties.ENGINEERING.getName(),
                Specialties.ENGINEERING.getQuantityOfStudents(), faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty(Specialties.COMPUTER_SCIENCE.getName(),
                Specialties.COMPUTER_SCIENCE.getQuantityOfStudents(), faculty.getId());
        specialtyDao.add(specialtyComp);
        Specialty market = new Specialty(Specialties.MARKETING.getName(),
                Specialties.MARKETING.getQuantityOfStudents(), facultyEconomy.getId());
        specialtyDao.add(market);
        Assert.assertEquals(2, facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).size());
        assertTrue(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(specialty));
        assertTrue(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(specialtyComp));
        assertFalse(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(market));
    }

}
