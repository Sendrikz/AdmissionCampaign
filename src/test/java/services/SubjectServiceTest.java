package services;

import model.builder.SubjectBuilder;
import model.connection.ConnectionManager;
import model.dao.SubjectDao;
import model.dao.factory.DaoFactory;
import model.enteties.Subject;
import model.enteties_enum.Subjects;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.exceptions.NoSuchSubjectException;

import java.sql.Connection;

public class SubjectServiceTest {

    private Connection connection;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
    }

    @After
    public void tearDown() {
        ConnectionManager.getInstance().close(connection);
    }

    private Subject setUpNewMathSubject() {
        return new SubjectBuilder().setName(Subjects.MATH.getName())
                .setDuration(Subjects.MATH.getDuration())
                .createSubject();
    }

    @Test(expected = NoSuchSubjectException.class)
    public void getSubjectIdByNameTest() throws NoSuchSubjectException {
        Subject subject = setUpNewMathSubject();
        SubjectDao subjectDao = DaoFactory.getSubjectDao(connection);
        subjectDao.clearAllSubjects();
        try (SubjectService subjectService = new SubjectService(connection)) {
            subjectService.getSubjectIdByName(subject.getName());
        }
    }
}
