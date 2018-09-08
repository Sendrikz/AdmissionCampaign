package services;

import model.connection.ConnectionManager;
import model.dao.factory.DaoFactory;
import model.dao.SubjectDao;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.exceptions.NoSuchSubjectException;

import java.io.Closeable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;

public class SubjectService implements Closeable {

    private Connection connection;
    private SubjectDao subjectDao;
    private static final Logger log = Logger.getLogger(SubjectService.class);

    public SubjectService() {
        connection = ConnectionManager.getInstance().getConnection();
        subjectDao = DaoFactory.getSubjectDao(connection);
    }

    public SubjectService(Boolean isTest) {
        if (isTest) {
            this.connection = ConnectionManager.getInstance().getConnectionToTestBD();
            subjectDao = DaoFactory.getSubjectDao(connection);
        } else {
            connection = ConnectionManager.getInstance().getConnection();
            subjectDao = DaoFactory.getSubjectDao(connection);
        }
    }

    public Subject getSubjectIdByName(String name) throws NoSuchSubjectException {
        log.info("Start class SubjectService getSubjectIdByName()");
        ArrayList<Subject> listOfSubjects = subjectDao.getAll();
        log.debug("List of subjects: " + listOfSubjects);
        for (Subject sub : listOfSubjects) {
            if (sub.getName().equals(name)) {
                log.debug("Find subject: " + sub);
                return sub;
            }
        }
        log.debug("There is no subject: " + null);
        throw new NoSuchSubjectException();
    }

    public ArrayList<User> getAllUsersWithUncheckedSubject(int id) {
        log.info("Start class SubjectService getAllUsersWithUncheckedSubject()");
        ArrayList<User> listOfUsers = subjectDao.getAllUsersWithUncheckedSubject(id);
        log.debug("List of users with unchecked subjects: " + listOfUsers);
        return listOfUsers;
    }

    public void updateSubjectToUser(int subjectId, int userId, BigDecimal grade) {
        log.info("Start class SubjectService updateSubjectToUser()");
        subjectDao.updateSubjectToUser(subjectId, userId, true, grade);
    }

    @Override
    public void close() {
        ConnectionManager.getInstance().close(connection);
    }
}
