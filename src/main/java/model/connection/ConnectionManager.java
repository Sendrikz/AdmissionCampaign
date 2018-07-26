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

    public ConnectionManager() {
        property = new Properties();
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getConnection() {
        try {
            connection = DriverManager.getConnection(property.getProperty("db.URL"),
                    property.getProperty("db.login"), property.getProperty("db.password"));

            if (!connection.isClosed()) {
                System.out.println("Підключено до бази");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}