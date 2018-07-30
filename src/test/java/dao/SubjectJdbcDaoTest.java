package dao;

import model.connection.ConnectionManager;
import model.dao.*;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SubjectJdbcDaoTest {

    private SubjectDao subjectDao;
    private SpecialtyDao specialtyDao;
    private FacultyDao facultyDao;
    private Connection connection;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnectionToTestBD();
        subjectDao = new SubjectJdbcDao(connection);
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
        Subject subject = new Subject("Java programming", 120);
        int originId = subject.getId();
        subjectDao.add(subject);
        assertNotEquals(originId, subject.getId());
    }

    @Test
    public void updateTest() {
        Subject subject = new Subject("Java programming", 120);
        subjectDao.add(subject);
        subjectDao.update(subject.getId(), subject.getName(), 240);
        assertNotEquals(subject, subjectDao.findById(subject.getId()));
    }

    @Test
    public void findByIdTest() {
        Subject subject = new Subject("Java programming", 120);
        subjectDao.add(subject);
        assertEquals(subject, subjectDao.findById(subject.getId()));
    }

    @Test
    public void deleteByIdTest() {
        Subject subject = new Subject("Java programming", 120);
        subjectDao.add(subject);
        subjectDao.deleteById(subject.getId());
        assertNull(subjectDao.findById(subject.getId()));
    }

    @Test
    public void clearAllSubjectsTest() {
        Subject subjectJava = new Subject("Java programming", 120);
        subjectDao.add(subjectJava);
        Subject subjectC = new Subject("C++", 120);
        subjectDao.add(subjectC);
        specialtyDao.clearAllSpecialties();
        subjectDao.clearAllSubjects();
        assertEquals(0, subjectDao.getAll().size());
    }

    @Test
    public void addSubjectToSpecialtyTest() {
        Subject math = new Subject("Math", 120);
        subjectDao.add(math);
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty("Computer science", 60,
                faculty.getId());
        specialtyDao.add(specialtyComp);
        subjectDao.addSubjectToSpecialty(math, specialty, new BigDecimal(0.5));
        subjectDao.addSubjectToSpecialty(math, specialtyComp, new BigDecimal(0.5));
        assertEquals(2, subjectDao.getAllSpecialtiesBySubject(math.getId()).size());
        subjectDao.updateSubjectToSpecialty(math.getId(), specialty.getId(), new BigDecimal(0.6));
        assertNotEquals(0.5, subjectDao.getAllSpecialtiesBySubject(math.getId()).get(specialty));
    }

//    @Test
//    public void getAllTest() {
//        subjectDao.clearAllSubjects();
//        Subject subject = new Subject("Java programming", 120);
//        subjectDao.add(subject);
//        Subject subjectC = new Subject("C++", 120);
//        subjectDao.add(subjectC);
//        assertEquals(2, subjectDao.getAll().size());
//        assertTrue(subjectDao.getAll().contains(subject));
//        assertTrue(subjectDao.getAll().contains(subjectC));
//    }

}
