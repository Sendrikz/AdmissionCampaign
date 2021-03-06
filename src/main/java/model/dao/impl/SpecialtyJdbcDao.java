package model.dao.impl;

import model.builder.SpecialtyBuilder;
import model.builder.SubjectBuilder;
import model.builder.UserBuilder;
import model.dao.SpecialtyDao;
import model.enteties.*;
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

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class SpecialtyJdbcDao implements SpecialtyDao {

    private static final Logger log = Logger.getLogger(SpecialtyJdbcDao.class);
    private Connection connection;

    public SpecialtyJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Specialty> getAll() {
        ArrayList<Specialty> listOfSpecialties = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                    Strings.GET_ALL_SPECIALTIES));
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                int facultyId = resultSet.getInt(4);
                Specialty specialty = new SpecialtyBuilder().setName(name).setQuantityOfStudents(quantityOfStudents).setFacultyId(facultyId).createSpecialty();
                specialty.setId(id);
                listOfSpecialties.add(specialty);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSpecialties;
    }

    @Override
    public ArrayList<Specialty> getAll(int currentPage, int recordsPerPage) {
        ArrayList<Specialty> listOfSpecialties = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                Strings.GET_ALL_SPECIALTIES_PAGINATION))) {

            int start = currentPage * recordsPerPage - recordsPerPage;
            ps.setInt(1, start);
            ps.setInt(2, recordsPerPage);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                int facultyId = resultSet.getInt(4);
                Specialty specialty = new SpecialtyBuilder().setName(name).setQuantityOfStudents(quantityOfStudents).setFacultyId(facultyId).createSpecialty();
                specialty.setId(resultSet.getInt(1));
                listOfSpecialties.add(specialty);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSpecialties;
    }

    @Override
    public void add(Specialty specialty) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                Strings.ADD_SPECIALTY), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, specialty.getName());
            ps.setInt(2, specialty.getQuantityOfStudents());
            ps.setInt(3, specialty.getFacultyId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("Creating user failed, no ID obtained.");
                } else {
                    specialty.setId(keys.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void addSpecialtyToSubject(Specialty specialty, Subject subject, BigDecimal coef) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_SUBJECT_PROPERTIES,
                Strings.ADD_SPECIALTY_TO_SUBJECT))) {

            ps.setInt(1, specialty.getId());
            ps.setInt(2, subject.getId());
            ps.setBigDecimal(3, coef);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void addSpecialtyToUser(Specialty specialty, User user, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SUBJECT_PROPERTIES,
                        Strings.ADD_USER_TO_SPECIALTY))) {

            ps.setInt(2, specialty.getId());
            ps.setInt(1, user.getId());
            ps.setBoolean(3, passed);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void update(int id, String name, int quantityOfStudents, int facultyId) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                Strings.UPDATE_SPECIALTY))) {

            ps.setString(1, name);
            ps.setInt(2, quantityOfStudents);
            ps.setInt(3, facultyId);
            ps.setInt(4, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateSpecialtyToSubject(int specialtyId, int subjectId, BigDecimal coef) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_SUBJECT_PROPERTIES,
                Strings.UPDATE_SPECIALTY_TO_SUBJECT))) {

            ps.setBigDecimal(1, coef);
            ps.setInt(2, specialtyId);
            ps.setInt(3, subjectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateSpecialtyToUser(int specialtyId, int userId, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SPECIALTY_PROPERTIES,
                        Strings.UPDATE_USER_TO_SPECIALTY))) {

            ps.setBoolean(1, passed);
            ps.setInt(3, specialtyId);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Specialty> findById(int id) {
        Specialty specialty = null;
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                Strings.FIND_BY_ID_SPECIALTY))) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                int facultyId = resultSet.getInt(4);
                specialty = new SpecialtyBuilder().setName(name)
                        .setQuantityOfStudents(quantityOfStudents).setFacultyId(facultyId)
                        .createSpecialty();
                specialty.setId(id);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(specialty);
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                        Strings.DELETE_BY_ID_SPECIALTY))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void clearAllSpecialties() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(
                    LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                            Strings.CLEAR_ALL_SPECIALTIES));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id) {
        HashMap<Subject, BigDecimal> listOfSubject = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_SUBJECT_PROPERTIES,
                        Strings.GET_ALL_SUBJECTS_OF_SPECILATY))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                    String name = resultSet.getString(2);
                    int duration = resultSet.getInt(3);
                    Subject subject = new SubjectBuilder().setName(name).setDuration(duration).createSubject();
                    subject.setId(resultSet.getInt(1));
                    listOfSubject.put(subject, resultSet.getBigDecimal(4));
                }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSubject;
    }

    @Override
    public HashMap<User, Boolean> getAllUsersOfSpecialty(int id) {
        HashMap<User, Boolean> listOfUsers = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_USER_SPECIALTY_PROPERTIES,
                        Strings.GET_ALL_USERS_OF_SPECIALTY))) {

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
                listOfUsers.put(user, resultSet.getBoolean(10));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return listOfUsers;
    }

    @Override
    public int getNumberOfRows() {
        int rows = 0;
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_SPECIALTY_PROPERTIES,
                            Strings.COUNT_SPECIALTIES_ROWS));
            if (resultSet.next()) {
                rows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return rows;
    }
}
