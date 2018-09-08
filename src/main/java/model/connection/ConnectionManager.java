package model.connection;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class ConnectionManager {

    private ConnectionManager() {}

    private static class ConnectionPoolInstance {
        private final static ConnectionManager instance = new ConnectionManager();
    }
    public static ConnectionManager getInstance() {
        return ConnectionPoolInstance.instance;
    }

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
     *
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

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}