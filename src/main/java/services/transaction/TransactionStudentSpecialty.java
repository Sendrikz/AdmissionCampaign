package services.transaction;

import model.connection.ConnectionManager;
import model.dao.factory.DaoFactory;
import model.dao.UserDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionStudentSpecialty {

    private static final Logger log = Logger.getLogger(TransactionStudentSpecialty.class);

    public static void updateUserSpecialty(int user_id, int specialty_id) {
        log.info("Start class TransactionStudentSpecialty updateUserSpecialty()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        try {
            connection.setAutoCommit(false);
            userDao.updateUserToSpecialty(user_id, specialty_id, true);
            userDao.deleteUserFromSpecialtiesExcept(user_id, specialty_id);
            connection.commit();
            log.debug("Transaction successful");
        } catch (SQLException e) {
            log.debug("Transaction fail");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error(e1.getMessage());
            }
            log.error(e.getMessage());
        }
        ConnectionManager.getInstance().close(connection);
    }
}
