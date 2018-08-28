package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.SubjectDao;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SubjectService {

    private static final Logger log = Logger.getLogger(SubjectService.class);

    public static Subject getSubjectIdByName(String name) {
        Connection connection = ConnectionManager.getInstance().getConnection();
        SubjectDao subject = DaoFactory.getSubjectDao(connection);
        ArrayList<Subject> listOfSubjects = subject.getAll();
        log.debug("List of subjects: " + listOfSubjects);
        ConnectionManager.getInstance().close(connection);
        for (Subject sub : listOfSubjects) {
            if (sub.getName().equals(name)) {
                log.debug("Find subject: " + sub);
                return sub;
            }
        }
        log.debug("There is no subject: " + null);
        return null;
    }

    public static ArrayList<User> getAllUsersWithUncheckedSubject(int id) {
        Connection connection = ConnectionManager.getInstance().getConnection();
        SubjectDao subjectDao = DaoFactory.getSubjectDao(connection);
        ArrayList<User> listOfUsers = subjectDao.getAllUsersWithUncheckedSubject(id);
        log.debug("List of users with unchecked subjects: " + listOfUsers);
        ConnectionManager.getInstance().close(connection);
        return listOfUsers;
    }

    public static void updateSubjectToUser(int subjectId, int userId, BigDecimal grade) {
        Connection connection = ConnectionManager.getInstance().getConnection();
        SubjectDao subjectDao = DaoFactory.getSubjectDao(connection);
        subjectDao.updateSubjectToUser(subjectId, userId, true, grade);
        ConnectionManager.getInstance().close(connection);
    }
}
