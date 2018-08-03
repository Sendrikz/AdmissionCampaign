package dao;

import enums.Faculties;
import enums.Specialties;
import enums.Universities;
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
        return new University(Universities.NaUKMA.getName(), Universities.NaUKMA.getAddress());
    }

    private University setUpNewKPI() {
        return new University(Universities.KPI.getName(), Universities.KPI.getAddress());
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
        universityDao.update(academy.getId(), academy.getName(), Universities.KPI.getAddress());
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
        universityDao.clearAllUniversities();
        assertEquals(0, universityDao.getAll().size());
    }

    @Test
    public void addUniversityToSpecialtyTest() {
        universityDao.clearAllUniversities();
        University NaUKMA = setUpNewNaUKMA();
        universityDao.add(NaUKMA);
        Faculty faculty = new Faculty(Faculties.IT.getName());
        facultyDao.add(faculty);
        Specialty specialty = new Specialty(Specialties.ENGINEERING.getName(),
                Specialties.ENGINEERING.getQuantityOfStudents(), faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty(Specialties.COMPUTER_SCIENCE.getName(),
                Specialties.COMPUTER_SCIENCE.getQuantityOfStudents(), faculty.getId());
        specialtyDao.add(specialtyComp);
        universityDao.addUniversityToSpecialty(NaUKMA, specialty);
        universityDao.addUniversityToSpecialty(NaUKMA, specialtyComp);
        assertEquals(2, universityDao.getAllSpecialtiesOfUniversity(NaUKMA.getId()).size());
    }

}
