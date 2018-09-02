package dao;

import model.builder.UniversityBuilder;
import model.enteties_enum.Universities;
import model.connection.ConnectionManager;
import model.dao.impl.FacultyJdbcDao;
import model.dao.impl.SpecialtyJdbcDao;
import model.dao.impl.UniversityJdbcDao;
import model.dao.FacultyDao;
import model.dao.SpecialtyDao;
import model.dao.UniversityDao;
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
        con = ConnectionManager.getInstance().getConnectionToTestBD();
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
        return new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
    }

    private University setUpNewKPI() {
        return new UniversityBuilder().setName(Universities.KPI.getName()).setAddress(Universities.KPI.getAddress()).setCity(Universities.KPI.getCity()).createUniversity();
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
        if (universityDao.findById(academy.getId()).isPresent()) {
            University uniTest = universityDao.findById(academy.getId()).get();
            assertEquals(uniTest, academy);
        }
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
