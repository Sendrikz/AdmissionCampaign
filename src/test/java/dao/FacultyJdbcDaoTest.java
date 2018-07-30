package dao;

import model.connection.ConnectionManager;
import model.dao.FacultyDao;
import model.dao.FacultyJdbcDao;
import model.dao.SpecialtyDao;
import model.dao.SpecialtyJdbcDao;
import model.enteties.Faculty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotEquals;

public class FacultyJdbcDaoTest {

    private FacultyDao facultyDao;
    private SpecialtyDao specialtyDao;
    private Connection connection;

    @Before
    public void setUp() {
        connection = new ConnectionManager().getConnectionToTestBD();
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

    @Test
    public void addTest() {
        Faculty faculty = new Faculty("Information technologies");
        int originId = faculty.getId();
        facultyDao.add(faculty);
        assertNotEquals(originId, faculty.getId());
    }

    @Test
    public void clearAllFacultiesTest() {
        Faculty facultyEconomy = new Faculty("Economy");
        Faculty facultyIT = new Faculty("Information technologies");
        facultyDao.add(facultyEconomy);
        facultyDao.add(facultyIT);
        specialtyDao.clearAllSpecialties();
        facultyDao.clearAllFaculties();
        assertEquals(0, facultyDao.getAll().size());
    }

    @Test
    public void getAllTest() {
        Faculty facultyEconomy = new Faculty("Economy");
        Faculty facultyIT = new Faculty("Information technologies");
        facultyDao.clearAllFaculties();
        facultyDao.add(facultyEconomy);
        facultyDao.add(facultyIT);
        assertEquals(2, facultyDao.getAll().size());
    }

    @Test
    public void findByIdTest() {
        Faculty faculty = new Faculty("Biology");
        facultyDao.add(faculty);
        assertEquals(faculty, facultyDao.findById(faculty.getId()));
    }

    @Test
    public void updateTest() {
        Faculty faculty = new Faculty("Biology");
        facultyDao.add(faculty);
        facultyDao.update(faculty.getId(), "Economy");
        assertNotEquals(faculty, facultyDao.findById(faculty.getId()));
    }

    @Test
    public void deleteById() {
        Faculty faculty = new Faculty("Economy");
        facultyDao.add(faculty);
        facultyDao.deleteById(faculty.getId());
        assertNull(facultyDao.findById(faculty.getId()));
    }

}
