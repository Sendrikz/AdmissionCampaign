package dao;

import model.connection.ConnectionManager;
import model.dao.*;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.University;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class SpecialtyJdbcDaoTest {

    private SpecialtyDao specialtyDao;
    private FacultyDao facultyDao;
    private SubjectDao subjectDao;
    private Connection connection;
    private UniversityDao universityDao;

    @Before
    public void setUp() {
        connection = new ConnectionManager().getConnectionToTestBD();
        specialtyDao = new SpecialtyJdbcDao(connection);
        facultyDao = new FacultyJdbcDao(connection);
        universityDao = new UniversityJdbcDao(connection);
        subjectDao = new SubjectJdbcDao(connection);
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

    @Test
    public void getAllSpecialtiesByFacultyTest() {
        specialtyDao.clearAllSpecialties();
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Faculty facultyBio = new Faculty("Biology");
        facultyDao.add(facultyBio);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        Specialty specialtyComp = new Specialty("Computer science", 60,
                faculty.getId());
        specialtyDao.add(specialtyComp);
        Specialty biology = new Specialty("Biology", 40,
                facultyBio.getId());
        specialtyDao.add(biology);
        assertEquals(2, facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).size());
        assertTrue(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(specialty));
        assertTrue(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(specialtyComp));
        assertFalse(facultyDao.getAllSpecialtiesOfFaculty(faculty.getId()).contains(biology));
    }

    @Test
    public void addSpecialtyToUniversityTest() {
        University NAUKMA = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        University university = new University("KPI", "Peremogu, 37");
        universityDao.add(NAUKMA);
        universityDao.add(university);
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        specialtyDao.addSpecialtyToUniversity(specialty, NAUKMA);
        specialtyDao.addSpecialtyToUniversity(specialty, university);
        assertEquals(2, specialtyDao.getAllUniversitiesBySpecialty(specialty.getId()).size());
    }

    @Test
    public void addSpecialtyToSubjectTest() {
        Faculty faculty = new Faculty("Information technologies");
        facultyDao.add(faculty);
        Specialty specialty = new Specialty("Program engineering", 80,
                faculty.getId());
        specialtyDao.add(specialty);
        Subject math = new Subject("Math", 120);
        Subject language = new Subject("Ukrainian language", 80);
        subjectDao.add(math);
        subjectDao.add(language);
        specialtyDao.addSpecialtyToSubject(specialty, math, new BigDecimal(0.5));
        specialtyDao.addSpecialtyToSubject(specialty, language, new BigDecimal(0.2));
        assertEquals(2, specialtyDao.getAllSubjectsOfSpecialty(specialty.getId()).size());
        specialtyDao.updateSpecialtyToSubject(specialty.getId(), language.getId(), new BigDecimal(0.3));
        assertNotEquals(new BigDecimal(0.2), specialtyDao.getAllSubjectsOfSpecialty(specialty.getId()).get(language));
    }

}
