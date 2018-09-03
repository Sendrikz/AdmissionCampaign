package model.dao.impl;

import model.builder.SpecialtyBuilder;
import model.builder.SubjectBuilder;
import model.dao.UserDao;
import model.enteties.*;
import model.builder.UserBuilder;
import org.apache.log4j.Logger;
import utils.Strings;
import utils.property_loaders.LoadSQLProperties;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

public class UserJdbcDao implements UserDao {

    private static final Logger log = Logger.getLogger(UserJdbcDao.class);
    private Connection connection;

    public UserJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> listOfUsers = new ArrayList<>();

        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.findAllUsers"))) {

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
                User user = new UserBuilder().setLastName(lastName).setFirstName(firstName).setPatronymic(patronymic).setBirthday(birthday).setCity(city).setEmail(email).setPassword(password).setRole(role).createUser();
                user.setId(userId);

                listOfUsers.add(user);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfUsers;
    }

    @Override
    public ArrayList<User> getAllStudents(int id) {
        ArrayList<User> listOfStudents = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.getAllStudents"))) {

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String lastName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String patronymic = resultSet.getString(4);
                String birthday = resultSet.getString(5);
                String city = resultSet.getString(6);
                String email = resultSet.getString(7);
                String password = resultSet.getString(8);
                int role = resultSet.getInt(9);
                User user = new UserBuilder().setLastName(lastName).setFirstName(firstName).setPatronymic(patronymic).setBirthday(birthday).setCity(city).setEmail(email).setPassword(password).setRole(role).createUser();
                int userId = resultSet.getInt(1);
                user.setId(userId);

                listOfStudents.add(user);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfStudents;
    }

    @Override
    public void add(User user) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.addUser"),
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
            log.error(e.getMessage());
        }
    }

    @Override
    public void addUserToSubject(User user, Subject subject, boolean checked, BigDecimal grade) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SUBJECT_PROPERTIES,
                        "sql.addUserToSubject"))) {

            ps.setInt(1, user.getId());
            ps.setInt(2, subject.getId());
            ps.setBoolean(3, checked);
            ps.setBigDecimal(4, grade);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void addUserToSpecialty(User user, Specialty specialty, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SPECIALTY_PROPERTIES,
                        "sql.addUserToSpecialty"))) {

            ps.setInt(1, user.getId());
            ps.setInt(2, specialty.getId());
            ps.setBoolean(3, passed);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(int id) {
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.findUserById"))) {

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
                user = new UserBuilder()
                        .setLastName(lastName)
                        .setFirstName(firstName)
                        .setPatronymic(patronymic)
                        .setBirthday(birthday)
                        .setCity(city)
                        .setEmail(email)
                        .setPassword(password)
                        .setRole(role)
                        .createUser();
                user.setId(userId);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void update(int id, String lastName, String firstName, String patronymic, String birthday,
                       String city, int role) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.updateAFullUser"))) {

            ps.setString(1, lastName);
            ps.setString(2, firstName);
            ps.setString(3, patronymic);
            ps.setString(4, birthday);
            ps.setString(5, city);
            ps.setInt(6, role);
            ps.setInt(7, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateEmail(int id, String value) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.updateUserEmail"))) {

            ps.setString(1, value);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updatePassword(int id, String value) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.updateUserPassword"))) {

            ps.setString(1, value);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateUserToSubject(int userId, int subjectId, boolean checked, BigDecimal grade) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SUBJECT_PROPERTIES,
                        "sql.updateUserToSubject"))) {

            ps.setBoolean(1, checked);
            ps.setBigDecimal(2, grade);
            ps.setInt(3, userId);
            ps.setInt(4, subjectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateUserToSpecialty(int userId, int specialtyId, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SPECIALTY_PROPERTIES,
                        "sql.updateUserToSpecialty"))) {

            ps.setBoolean(1, passed);
            ps.setInt(2, userId);
            ps.setInt(3, specialtyId);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        "sql.deleteUserById"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteUserFromSpecialtiesExcept(int user_id, int specialty_id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SPECIALTY_PROPERTIES,
                        "sql.deleteUserFromSpecialtiesExcept"))) {

            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, specialty_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void clearAllUsers() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                    "sql.deleteAllUsers"));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_PROPERTIES,
                        Strings.FIND_USER_BY_EMAIL_AND_PASSWORD))) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserBuilder().setLastName(resultSet.getString(2))
                        .setFirstName(resultSet.getString(3)).setPatronymic(resultSet.getString(4))
                        .setBirthday(resultSet.getString(5)).setCity(resultSet.getString(6))
                        .setEmail(resultSet.getString(7)).setPassword(resultSet.getString(8))
                        .setRole(resultSet.getInt(9)).createUser();
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public HashMap<Subject, BigDecimal> getAllCheckedSubjectsByUser(int id) {

        HashMap<Subject, BigDecimal> listOfSubjects = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SUBJECT_PROPERTIES,
                        "sql.getAllCheckedSubjectsByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Subject subject = new SubjectBuilder().setName(resultSet.getString(2)).setDuration(resultSet.getInt(3)).createSubject();
                subject.setId(resultSet.getInt(1));
                listOfSubjects.put(subject, resultSet.getBigDecimal(4));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return listOfSubjects;
    }

    @Override
    public ArrayList<Subject> getAllSubjectsByUser(int id) {
        ArrayList<Subject> listOfSubjects = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SUBJECT_PROPERTIES,
                        "sql.getAllSubjectsByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Subject subject = new SubjectBuilder().setName(resultSet.getString(2)).setDuration(resultSet.getInt(3)).createSubject();
                subject.setId(resultSet.getInt(1));
                listOfSubjects.add(subject);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSubjects;
    }

    @Override
    public ArrayList<Subject> getAllUncheckedSubjectsByUser(int id) {
        ArrayList<Subject> listOfSubjects = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SUBJECT_PROPERTIES,
                        "sql.getAllUncheckedSubjectsByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Subject subject = new SubjectBuilder().setName(resultSet.getString(2)).setDuration(resultSet.getInt(3)).createSubject();
                subject.setId(resultSet.getInt(1));
                listOfSubjects.add(subject);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSubjects;
    }

    @Override
    public HashMap<Specialty, Boolean> getAllSpecialtiesByUser(int id) {

        HashMap<Specialty, Boolean> listOfSpecialties = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SPECIALTY_PROPERTIES,
                        "sql.getAllSpecialtiesByUser"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                int facultyId = resultSet.getInt(4);
                Specialty specialty = new SpecialtyBuilder().setName(name).setQuantityOfStudents(quantityOfStudents).setFacultyId(facultyId).createSpecialty();
                specialty.setId(resultSet.getInt(1));
                listOfSpecialties.put(specialty, resultSet.getBoolean(5));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return listOfSpecialties;
    }

    @Override
    public Optional<Specialty> getPassedSpecialtyByUser(int id) {
        Specialty specialty = null;
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SPECIALTY_PROPERTIES,
                        "sql.getPassedSpecialtyByUser"))) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                specialty = new SpecialtyBuilder().setName(resultSet.getString(2)).setQuantityOfStudents(resultSet.getInt(3)).setFacultyId(resultSet.getInt(4)).createSpecialty();
                specialty.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(specialty);
    }
}
