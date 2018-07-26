package model.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    FileInputStream fis;
    Properties property;
    Connection connection;
    String pathToFile = "src/main/resources/config.properties";
    String URL = "jdbc:mysql://localhost:3306/admission_сampaign" +
            "?autoReconnect=true&useSSL=false";

    public ConnectionManager() {
        property = new Properties();
        try {
            fis = new FileInputStream(pathToFile);
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, property.getProperty("db.login"),
                    property.getProperty("db.password"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}