package dao;

import model.enteties_enum.Universities;
import model.connection.ConnectionManager;
import model.dao.dao_implementations.FacultyJdbcDao;
import model.dao.dao_implementations.SpecialtyJdbcDao;
import model.dao.dao_implementations.UniversityJdbcDao;
import model.dao.dao_interfaces.FacultyDao;
import model.dao.dao_interfaces.SpecialtyDao;
import model.dao.dao_interfaces.UniversityDao;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.University;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

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

    private University setUpNewNaUKMA() {
        return new University(Universities.NaUKMA.getName(), Universities.NaUKMA.getAddress(),
                Universities.NaUKMA.getCity());
    }

    private University setUpNewKPI() {
        return new University(Universities.KPI.getName(), Universities.KPI.getAddress(),
                Universities.KPI.getCity());
    }

    @Test
    public void addTest() {
        University university = setUpNewNaUKMA();
        int originId = university.getId();
        universityDao.add(university);
        assertNotEquals(originId, university.getId());
    }

    @Test
    public void findByIdTest() {
        University academy = setUpNewNaUKMA();
        universityDao.add(academy);
        University uniTest = universityDao.findById(academy.getId());
        assertEquals(uniTest, academy);
    }

    @Test
    public void updateTest() {
        University academy = setUpNewNaUKMA();
        universityDao.add(academy);
        universityDao.update(academy.getId(), academy.getName(), Universities.KPI.getAddress(),
                Universities.KPI.getCity());
        assertNotEquals(academy, universityDao.findById(academy.getId()));
    }

    @Test
    public void deleteByIdTest() {
        University academy = setUpNewNaUKMA();
        universityDao.add(academy);
        universityDao.deleteById(academy.getId());
        assertNull(universityDao.findById(academy.getId()));
    }

    @Test
    public void clearAllUniversities() {
        University academy = setUpNewNaUKMA();
        University university = setUpNewKPI();
        specialtyDao.clearAllSpecialties();
        facultyDao.clearAllFaculties();
        universityDao.clearAllUniversities();
        universityDao.add(academy);
        universityDao.add(university);
        assertEquals(2, universityDao.getAll().size());
    }

}
