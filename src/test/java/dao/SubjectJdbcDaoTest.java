package dao;

import model.connection.ConnectionManager;
import model.dao.SubjectDao;
import model.dao.SubjectJdbcDao;
import model.enteties.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SubjectJdbcDaoTest {

    private SubjectDao subjectDao;
    private Connection connection;

    @Before
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnectionToTestBD();
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
        subjectDao.clearAllSubjects();
        assertEquals(0, subjectDao.getAll().size());
    }

    @Test
    public void getAllTest() {
        subjectDao.clearAllSubjects();
        Subject subject = new Subject("Java programming", 120);
        subjectDao.add(subject);
        Subject subjectC = new Subject("C++", 120);
        subjectDao.add(subjectC);
        assertEquals(2, subjectDao.getAll().size());
        assertTrue(subjectDao.getAll().contains(subject));
        assertTrue(subjectDao.getAll().contains(subjectC));
    }

}
