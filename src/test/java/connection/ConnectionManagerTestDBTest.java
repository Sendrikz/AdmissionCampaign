package connection;

import model.connection.ConnectionManagerTestBD;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;

public class ConnectionManagerTestDBTest {

    @Test
    public void getConnectionTest() {
        try (Connection con = new ConnectionManagerTestBD().getConnection()) {
            assertFalse(con.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
