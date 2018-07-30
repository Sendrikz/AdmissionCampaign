package dao;

import model.connection.ConnectionManager;
import model.dao.*;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.University;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.NavigableMap;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotEquals;
import static junit.framework.TestCase.assertEquals;

public class UniversityJdbcDaoTest {

    private UniversityDao universityDao;
    private FacultyDao facultyDao;
    private SpecialtyDao specialtyDao;
    private Connection con;

    @Before
    public void setUp() {
        con = new ConnectionManager().getConnectionToTestBD();
        universityDao = new UniversityJdbcDao(con);
        facultyDao = new FacultyJdbcDao(con);
        specialtyDao = new SpecialtyJdbcDao(con);
    }

    @After
    public void tearDown() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTest() {
        University university = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        int originId = university.getId();
        universityDao.add(university);
        assertNotEquals(originId, university.getId());
    }

    @Test
    public void findByIdTest() {
        University academy = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        universityDao.add(academy);
        University uniTest = universityDao.findById(academy.getId());
        assertEquals(uniTest, academy);
    }

    @Test
    public void updateTest() {
        University academy = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        universityDao.add(academy);
        universityDao.update(academy.getId(), academy.getName(), "Hryhoria Skovorodu, 4");
        assertNotEquals(academy, universityDao.findById(academy.getId()));
    }

    @Test
    public void deleteByIdTest() {
        University academy = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        universityDao.add(academy);
        universityDao.deleteById(academy.getId());
        assertNull(universityDao.findById(academy.getId()));
    }

    @Test
    public void clearAllUniversities() {
        University academy = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        University university = new University("KPI", "Peremogu, 37");
        universityDao.clearAllUniversities();
        assertEquals(0, universityDao.getAll().size());
    }

    @Test
    public void getAllTest() {
        universityDao.clearAllUniversities();
        University academy = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        University university = new University("KPI", "Peremogu, 37");
        universityDao.add(academy);
        universityDao.add(university);
        assertEquals(2, universityDao.getAll().size());
    }

    @Test
    public void addUniversityToSpecialtyTest() {
        universityDao.clearAllUniversities();
        University NAUKMA = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        universityDao.add(NAUKMA);
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty("Computer science", 60,
                faculty.getId());
        specialtyDao.add(specialtyComp);
        universityDao.addUniversityToSpecialty(NAUKMA, specialty);
        universityDao.addUniversityToSpecialty(NAUKMA, specialtyComp);
        assertEquals(2, universityDao.getAllSpecialtiesOfUniversity(NAUKMA.getId()).size());
    }

}
