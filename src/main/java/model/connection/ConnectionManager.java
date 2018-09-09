package model.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helps to get connection to databases. Also use singleton
 */

public class ConnectionManager {

    private ConnectionManager() {}

    private static class ConnectionPoolInstance {
        private final static ConnectionManager instance = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return ConnectionPoolInstance.instance;
    }

    /**
     * Get connection to main working database
     * @return Connection
     */
    public Connection getConnection() {
        Context envCtx;
        Connection connection = null;
        try {
            envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
            DataSource ds = (DataSource) envCtx.lookup("jdbc/mydatabase");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Get connection to test database
     * @return Connection
     */
    public Connection getConnectionToTestBD() {
        Connection connection = null;
        try {
            String URL = "jdbc:mysql://localhost:3306/admission_campaign_test" +
                    "?autoReconnect=true&useSSL=false";
            connection = DriverManager.getConnection(URL, "root", "root");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close connection
     * @param connection Connection
     */
    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}