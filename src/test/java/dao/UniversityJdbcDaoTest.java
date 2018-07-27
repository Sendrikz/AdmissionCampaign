package dao;

import model.connection.ConnectionManager;
import model.dao.UniversityDao;
import model.dao.UniversityJdbcDao;
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
    private Connection con;

    @Before
    public void setUp() {
        con = new ConnectionManager().getConnectionToTestBD();
        universityDao = new UniversityJdbcDao(con);
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



}
