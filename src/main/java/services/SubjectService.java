package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.SubjectDao;
import model.enteties.Subject;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectService {

    private static final Logger log = Logger.getLogger(String.valueOf(SubjectService.class));

    public static Subject getSubjectIdByName(String name) {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        ArrayList<Subject> listOfSubjects = null;
        try {
            connection.setAutoCommit(false);
            log.info("Start transaction");
            SubjectDao subject = DaoFactory.getSubjectDao(connection);
            listOfSubjects = subject.getAll();
            log.debug("List of subjects: " + listOfSubjects);
            connection.commit();
            log.info("End transaction");
        } catch (SQLException e) {
            log.error("Transaction fail");
            e.printStackTrace();
        }
        connectionManager.close(connection);
        for (Subject sub : listOfSubjects) {
            if (sub.getName().equals(name)) {
                log.debug("Find subject: " + sub);
                return sub;
            }
        }
        log.debug("Subject didnt found: " + null);
        return null;
    }
}
