package model.dao.impl;

import model.dao.RoleDao;
import model.enteties.Role;
import model.builder.RoleBuilder;
import model.enteties.User;
import model.builder.UserBuilder;
import org.apache.log4j.Logger;
import utils.Strings;
import utils.property_loaders.LoadSQLProperties;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class RoleJdbcDao implements RoleDao {

    private static final Logger log = Logger.getLogger(RoleJdbcDao.class);
    private Connection connection;

    public RoleJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Role> getAll() {
        ArrayList<Role> listOfRoles = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                        Strings.FIND_ALL_ROLES))) {

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Role role = new RoleBuilder().setName(name).createRole();
                role.setId(id);
                listOfRoles.add(role);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfRoles;
    }

    @Override
    public void add(Role role) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                        Strings.ADD_ROLE),
                     Statement.RETURN_GENERATED_KEYS)) {


            ps.setString(1, role.getName());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(int id, String value) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                        Strings.UPDATE_NAME_ROLE))) {

            ps.setString(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Role> findById(int id) {
        Role role = null;
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                Strings.FIND_BY_ROLE_ID))) {

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
               int idRole = resultSet.getInt(1);
               String name = resultSet.getString(2);
               role = new RoleBuilder().setName(name).createRole();
               role.setId(idRole);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(role);
    }

    @Override
    public int findIdByRoleName(String name) {
        int id = 0;
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                Strings.FIND_ID_BY_ROLE_NAME))) {

            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return id;
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                Strings.DELETE_BY_ID_ROLE))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void clearAllRoles() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(
                    LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                            Strings.DELETE_ALL_ROLES));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAllUsersByRole(int roleId) {
        ArrayList<User> listOfUsersByRole = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_ROLE_PROPERTIES,
                Strings.ALL_USERS_BY_ROLE))) {

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
                User user = new UserBuilder().setLastName(lastName).setFirstName(firstName)
                        .setPatronymic(patronymic).setBirthday(birthday)
                        .setCity(city).setEmail(email).setPassword(password)
                        .setRole(roleId).createUser();
                user.setId(userId);

                listOfUsersByRole.add(user);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfUsersByRole;
    }
}
