package model.dao;

import model.connection.ConnectionManager;
import model.enteties.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class UserJdbcDao implements UserDao {

    private Properties property;
    private ConnectionManager connectionManager;

    public UserJdbcDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        property = new Properties();
        try {
            String pathToFile = "src/main/resources/sql.properties";
            FileInputStream fis = new FileInputStream(pathToFile);
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> listOfUsers = new ArrayList<>();

        try (Statement statement = connectionManager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(property.getProperty("sql.findAllUsers"))) {

            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String lastName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String patronymic = resultSet.getString(4);
                String birthday = resultSet.getString(5);
                String city = resultSet.getString(6);
                String email = resultSet.getString(7);
                String password = resultSet.getString(8);
                int role = resultSet.getInt(9);
                User user = new User(lastName, firstName, patronymic, birthday, city, email,
                        password, role);
                user.setId(userId);

                listOfUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    @Override
    public void add(User user) {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(property.getProperty("sql.addUser"),
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getLastName());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getPatronymic());
            ps.setString(4, user.getBirthday());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPassword());
            ps.setInt(8, user.getRole());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(property.getProperty("sql.findUserById"))) {

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String lastName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String patronymic = resultSet.getString(4);
                String birthday = resultSet.getString(5);
                String city = resultSet.getString(6);
                String email = resultSet.getString(7);
                String password = resultSet.getString(8);
                int role = resultSet.getInt(9);
                user = new User(lastName, firstName, patronymic, birthday, city, email,
                        password, role);
                user.setId(userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateEmail(int id, String value) {
        try (Connection con = connectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(property.getProperty("sql.updateUserEmail"))) {

            ps.setString(1, value);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassword(int id, String value) {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(property.getProperty("sql.updateUserPassword"))) {

            ps.setString(1, value);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection con = connectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(property.getProperty("sql.deleteUserById"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllUsers() {
        try (Statement statement = connectionManager.getConnection().createStatement()) {

            statement.executeUpdate(property.getProperty("sql.deleteAllUsers"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
