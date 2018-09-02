package model.dao.impl;

import model.builder.SpecialtyBuilder;
import model.builder.SubjectBuilder;
import model.dao.SubjectDao;
import model.enteties.*;
import model.builder.UserBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

public class SubjectJdbcDao implements SubjectDao {

    private static final Logger log = Logger.getLogger(SubjectJdbcDao.class);
    private Connection connection;
    private Properties property;

    public SubjectJdbcDao(Connection connection) {
        this.connection = connection;
        property = new Properties();
        this.property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql.properties")){
            property.load(is);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public ArrayList<Subject> getAll() {
        ArrayList<Subject> listOfSubjects = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(property.getProperty("sql.getAllSubjects"));
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int duration = resultSet.getInt(3);
                Subject subject = new SubjectBuilder().setName(name).setDuration(duration).createSubject();
                subject.setId(id);
                listOfSubjects.add(subject);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSubjects;
    }

    @Override
    public void add(Subject subject) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addSubject"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, subject.getName());
            ps.setInt(2, subject.getDuration());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subject.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void addSubjectToSpecialty(Subject subject, Specialty specialty, BigDecimal coef) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addSpecialtyToSubject"))) {

            ps.setInt(2, subject.getId());
            ps.setInt(1, specialty.getId());
            ps.setBigDecimal(3, coef);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void addSubjectToUser(Subject subject, User user, boolean checked, BigDecimal grade) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addUserToSubject"))) {

            ps.setInt(2, subject.getId());
            ps.setInt(1, user.getId());
            ps.setBoolean(3, checked);
            ps.setBigDecimal(4, grade);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(int id, String name, int duration) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateSubject"))) {

            ps.setString(1, name);
            ps.setInt(2, duration);
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateSubjectToSpecialty(int subjectId, int specialtyId, BigDecimal coef) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateSpecialtyToSubject"))) {

            ps.setBigDecimal(1, coef);
            ps.setInt(3, subjectId);
            ps.setInt(2, specialtyId);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateSubjectToUser(int subjectId, int userId, boolean checked, BigDecimal grade) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUserToSubject"))) {

            ps.setBoolean(1, checked);
            ps.setBigDecimal(2, grade);
            ps.setInt(4, subjectId);
            ps.setInt(3, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Subject> findById(int id) {
        Subject subject = null;
        try (PreparedStatement ps =connection.prepareStatement(
                property.getProperty("sql.findByIdSubject"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int duration = resultSet.getInt(3);
                subject = new SubjectBuilder().setName(name).setDuration(duration).createSubject();
                subject.setId(id);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(subject);
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.deleteByIdSubject"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void clearAllSubjects() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(property.getProperty("sql.clearAllSubjects"));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public HashMap<Specialty, BigDecimal> getAllSpecialtiesBySubject(int subjectId) {
        HashMap<Specialty, BigDecimal> listOfSpecialties = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllSpecialtiesBySubject"))) {

            ps.setInt(1, subjectId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                    Specialty specialty = new SpecialtyBuilder().setName(resultSet.getString(2)).setQuantityOfStudents(resultSet.getInt(3)).setFacultyId(resultSet.getInt(4)).createSpecialty();
                    specialty.setId(resultSet.getInt(1));
                    listOfSpecialties.put(specialty, resultSet.getBigDecimal(5));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSpecialties;
    }

    @Override
    public HashMap<User, BigDecimal> getAllUsersWithCheckedSubjects(int id) {
        HashMap<User, BigDecimal> listOfUsers = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllUsersWithCheckedSubjects"))) {

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
                User user = new UserBuilder().setLastName(lastName).setFirstName(firstName).setPatronymic(patronymic).setBirthday(birthday).setCity(city).setEmail(email).setPassword(password).setRole(role).createUser();
                user.setId(userId);
                listOfUsers.put(user, resultSet.getBigDecimal(10));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfUsers;
    }

    @Override
    public ArrayList<User> getAllUsersBySubject(int id) {
        ArrayList<User> listOfUsers = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllUsersBySubject"))) {

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
                user.setId(resultSet.getInt(1));
                listOfUsers.add(user);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfUsers;
    }

    @Override
    public ArrayList<User> getAllUsersWithUncheckedSubject(int id) {
        ArrayList<User> listOfUsers = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllUsersWithUncheckedSubject"))) {

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
                User user = new UserBuilder().setLastName(lastName).setFirstName(firstName).setPatronymic(patronymic).setBirthday(birthday).setCity(city).setEmail(email).setPassword(password).setRole(resultSet.getInt(9)).createUser();
                user.setId(resultSet.getInt(1));
                listOfUsers.add(user);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfUsers;
    }

}