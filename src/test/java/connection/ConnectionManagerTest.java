package connection;

import model.connection.ConnectionManager;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;

public class ConnectionManagerTest {

    @Ignore
    public void getConnectionTest() {
        try (Connection connection = new ConnectionManager().getConnection()) {
                assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getConnectionToTestBDTest() {
        try (Connection connection = new ConnectionManager().getConnectionToTestBD()) {
                assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
