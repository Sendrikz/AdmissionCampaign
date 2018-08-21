package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.SubjectDao;
import model.enteties.Subject;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SubjectService {

    private static final Logger log = Logger.getLogger(String.valueOf(SubjectService.class));

    public static Subject getSubjectIdByName(String name) {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        SubjectDao subject = DaoFactory.getSubjectDao(connection);
        ArrayList<Subject> listOfSubjects = subject.getAll();
        log.debug("List of subjects: " + listOfSubjects);
        connectionManager.close(connection);
        for (Subject sub : listOfSubjects) {
            if (sub.getName().equals(name)) {
                log.debug("Find subject: " + sub);
                return sub;
            }
        }
        log.debug("Subject didn`t found: " + null);
        return null;
    }
}
