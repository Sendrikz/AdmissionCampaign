package controller.transaction;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.UserDao;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionStudentSpecialty {

    public static void updateUserSpecialty(int user_id, int specialty_id) {
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserDao userDao = DaoFactory.getUserDao(connection);
        try {
            connection.setAutoCommit(false);
            userDao.updateUserToSpecialty(user_id, specialty_id, true);
            userDao.deleteUserFromSpecialtiesExcept(user_id, specialty_id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        ConnectionManager.getInstance().close(connection);
    }
}
