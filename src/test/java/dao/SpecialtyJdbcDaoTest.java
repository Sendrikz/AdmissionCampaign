package dao;

import model.connection.ConnectionManager;
import model.dao.FacultyDao;
import model.dao.FacultyJdbcDao;
import model.dao.SpecialtyDao;
import model.dao.SpecialtyJdbcDao;
import model.enteties.Faculty;
import model.enteties.Specialty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SpecialtyJdbcDaoTest {

    private SpecialtyDao specialtyDao;
    private FacultyDao facultyDao;
    private Connection connection;

    @Before
    public void setUp() {
        connection = new ConnectionManager().getConnectionToTestBD();
        specialtyDao = new SpecialtyJdbcDao(connection);
        facultyDao = new FacultyJdbcDao(connection);
    }

    @After
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTest() {
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 60,
                faculty.getId());
        int originId = specialty.getId();
        specialtyDao.add(specialty);
        assertNotEquals(originId, specialty.getId());
    }

    @Test
    public void updateTest() {
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 60,
                faculty.getId());
        specialtyDao.add(specialty);
        specialtyDao.update(specialty.getId(), specialty.getName(), 80,
                specialty.getFacultyId());
        assertNotEquals(specialty, specialtyDao.findById(specialty.getId()));
    }

    @Test
    public void findByIdTest() {
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 60,
                faculty.getId());
        specialtyDao.add(specialty);
        assertEquals(specialty, specialtyDao.findById(specialty.getId()));
    }

    @Test
    public void deleteByIdTest() {
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 60,
                faculty.getId());
        specialtyDao.add(specialty);
        specialtyDao.deleteById(specialty.getId());
        assertNull(specialtyDao.findById(specialty.getId()));
    }

    @Test
    public void clearAllSubjectsTest() {
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty("Computer science", 60,
                faculty.getId());
        specialtyDao.add(specialtyComp);
        specialtyDao.clearAllSpecialties();
        assertEquals(0, specialtyDao.getAll().size());
    }

    @Test
    public void getAllTest() {
        specialtyDao.clearAllSpecialties();
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty("Computer science", 60,
                faculty.getId());
        specialtyDao.add(specialtyComp);
        assertEquals(2, specialtyDao.getAll().size());
        assertTrue(specialtyDao.getAll().contains(specialty));
        assertTrue(specialtyDao.getAll().contains(specialtyComp));
    }

}
