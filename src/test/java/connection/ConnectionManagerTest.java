package connection;

import model.connection.ConnectionManager;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class ConnectionManagerTest {

    @Ignore
    public void getConnectionTest() {
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
                assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getConnectionToTestBDTest() {
        try (Connection connection = ConnectionManager.getInstance().getConnectionToTestBD()) {
                assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
