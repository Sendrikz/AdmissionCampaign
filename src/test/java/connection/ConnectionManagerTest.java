package connection;

import model.connection.ConnectionManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;

public class ConnectionManagerTest {

    @Test
    public void getConnectionTest() {
        try (Connection connection = new ConnectionManager().getConnection()) {
            try {
                assertFalse(connection.isClosed());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
