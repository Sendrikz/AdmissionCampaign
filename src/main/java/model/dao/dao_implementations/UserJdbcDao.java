package model.dao.dao_implementations;

import model.dao.dao_interfaces.UserDao;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class UserJdbcDao implements UserDao {

    private Properties property;
    private Connection connection;

    public UserJdbcDao(Connection connection) {
        this.connection = connection;
        this.property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> listOfUsers = new ArrayList<>();

        try (Statement statement = connection.createStatement();
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
        try (PreparedStatement ps = connection.prepareStatement(property.getProperty("sql.addUser"),
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
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUserToSubject(User user, Subject subject, boolean checked, BigDecimal grade) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addUserToSubject"))) {

            ps.setInt(1, user.getId());
            ps.setInt(2, subject.getId());
            ps.setBoolean(3, checked);
            ps.setBigDecimal(4, grade);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUserToSpecialty(User user, Specialty specialty, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addUserToSpecialty"))) {

            ps.setInt(1, user.getId());
            ps.setInt(2, specialty.getId());
            ps.setBoolean(3, passed);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.findUserById"))) {

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
    public void update(int id, String lastName, String firstName, String patronymic, String birthday,
                       String city, int role) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateAFullUser"))) {

            ps.setString(1, lastName);
            ps.setString(2, firstName);
            ps.setString(3, patronymic);
            ps.setString(4, birthday);
            ps.setString(5, city);
            ps.setInt(6, role);
            ps.setInt(7, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmail(int id, String value) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUserEmail"))) {

            ps.setString(1, value);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassword(int id, String value) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUserPassword"))) {

            ps.setString(1, value);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserToSubject(int userId, int subjectId, boolean checked, BigDecimal grade) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUserToSubject"))) {

            ps.setBoolean(1, checked);
            ps.setBigDecimal(2, grade);
            ps.setInt(3, userId);
            ps.setInt(4, subjectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserToSpecialty(int userId, int specialtyId, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUserToSpecialty"))) {

            ps.setBoolean(1, passed);
            ps.setInt(2, userId);
            ps.setInt(3, specialtyId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.deleteUserById"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllUsers() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(property.getProperty("sql.deleteAllUsers"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<Subject, BigDecimal> getAllCheckedSubjectsByUser(int id) {

        HashMap<Subject, BigDecimal> listOfSubjects = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllCheckedSubjectsByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject(resultSet.getString(2), resultSet.getInt(3));
                subject.setId(resultSet.getInt(1));
                listOfSubjects.put(subject, resultSet.getBigDecimal(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfSubjects;
    }

    @Override
    public ArrayList<Subject> getAllSubjectsByUser(int id) {
        ArrayList<Subject> listOfSubjects = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllSubjectsByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject(resultSet.getString(2), resultSet.getInt(3));
                subject.setId(resultSet.getInt(1));
                listOfSubjects.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSubjects;
    }

    @Override
    public ArrayList<Subject> getAllUncheckedSubjectsByUser(int id) {
        ArrayList<Subject> listOfSubjects = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllUncheckedSubjectsByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject(resultSet.getString(2), resultSet.getInt(3));
                subject.setId(resultSet.getInt(1));
                listOfSubjects.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSubjects;
    }

    @Override
    public HashMap<Specialty, Boolean> getAllSpecialtiesByUser(int id) {

        HashMap<Specialty, Boolean> listOfSpecialties = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllSpecialtiesByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                int facultyId = resultSet.getInt(4);
                Specialty specialty = new Specialty(name, quantityOfStudents, facultyId);
                specialty.setId(resultSet.getInt(1));
                listOfSpecialties.put(specialty, resultSet.getBoolean(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfSpecialties;
    }
}
