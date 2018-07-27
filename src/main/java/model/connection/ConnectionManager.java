package model.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private Properties property;
    private Connection connection;

    public ConnectionManager() {
        property = new Properties();
        String pathToFile = "src/main/resources/config.properties";
        try {
            FileInputStream fis = new FileInputStream(pathToFile);
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            String URL = "jdbc:mysql://localhost:3306/admission_сampaign" +
                    "?autoReconnect=true&useSSL=false";
            connection = DriverManager.getConnection(URL, property.getProperty("db.login"),
                    property.getProperty("db.password"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnectionToTestBD() {
        String URL = "jdbc:mysql://localhost:3306/admission_campaign_test" +
                "?autoReconnect=true&useSSL=false";
        try {
            connection = DriverManager.getConnection(URL, property.getProperty("db.login"),
                    property.getProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}