package dao;

import enums.Faculties;
import enums.Specialties;
import model.connection.ConnectionManager;
import model.dao.FacultyDao;
import model.dao.FacultyJdbcDao;
import model.dao.SpecialtyDao;
import model.dao.SpecialtyJdbcDao;
import model.enteties.Faculty;
import model.enteties.Specialty;
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
    private Connection connection;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        facultyDao = new FacultyJdbcDao(connection);
        specialtyDao = new SpecialtyJdbcDao(connection);
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
        return new Faculty(Faculties.IT.getName());
    }

    private Faculty setUpNewEconomicFaculty() {
        return new Faculty(Faculties.ECONOMIC.getName());
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
        specialtyDao.clearAllSpecialties();
        facultyDao.clearAllFaculties();
        assertEquals(0, facultyDao.getAll().size());
    }

    @Test
    public void updateTest() {
        Faculty faculty = setUpNewITFaculty();
        facultyDao.add(faculty);
        facultyDao.update(faculty.getId(), Faculties.BIOLOGY.getName());
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
