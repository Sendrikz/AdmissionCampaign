package model.dao;

import model.connection.ConnectionManager;
import model.enteties.Role;
import model.enteties.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class RoleJdbcDao implements RoleDao {

    private ConnectionManager connectionManager;
    private Properties property;

    public RoleJdbcDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        property = new Properties();
        String filePath = "src/main/resources/sql.properties";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Role> getAll() {
        ArrayList<Role> listOfRoles = new ArrayList<>();

        try (Statement statement = connectionManager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(property.getProperty("sql.findAllRoles"))) {

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Role role = new Role(name);
                role.setId(id);
                listOfRoles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfRoles;
    }

    @Override
    public void add(Role role) {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(property.getProperty("sql.addRole"),
                     Statement.RETURN_GENERATED_KEYS)) {


            ps.setString(1, role.getName());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getInt(1));
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
    public void update(int id, String value) {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(property.getProperty("sql.updateNameRole"))) {

            ps.setString(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Role findById(int id) {
        Role role = null;
        try (Connection con = connectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(property.getProperty("sql.findByIdRole"))) {

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
               int idRole = resultSet.getInt(1);
               String name = resultSet.getString(2);
               role = new Role(name);
               role.setId(idRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public void deleteById(int id) {
        try (Connection con = connectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(property.getProperty("sql.deleteByIdRole"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clearAllRoles() {
        try (Statement statement = connectionManager.getConnection().createStatement()) {

            statement.executeUpdate(property.getProperty("sql.deleteAllRoles"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getAllUsersByRole(int roleId) {
        ArrayList<User> listOfUsersByRole = new ArrayList<>();
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(property.getProperty("sql.allUsersByRole"))) {

            ps.setInt(1, roleId);

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
                User user = new User(lastName, firstName, patronymic, birthday, city, email,
                        password, roleId);
                user.setId(userId);

                listOfUsersByRole.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUsersByRole;
    }
}
