package model.dao.dao_implementations;

import model.dao.dao_interfaces.SpecialtyDao;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.University;
import model.enteties.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class SpecialtyJdbcDao implements SpecialtyDao {

    private Connection connection;
    private Properties property;

    public SpecialtyJdbcDao(Connection connection) {
        this.connection = connection;
        this.property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Specialty> getAll() {
        ArrayList<Specialty> listOfSpecialties = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    property.getProperty("sql.getAllSpecialties"));
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                int facultyId = resultSet.getInt(4);
                Specialty specialty = new Specialty(name, quantityOfStudents, facultyId);
                specialty.setId(id);
                listOfSpecialties.add(specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSpecialties;
    }

    @Override
    public void add(Specialty specialty) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addSpecialty"), Statement.RETURN_GENERATED_KEYS)) {
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
            e.printStackTrace();
        }
    }

    @Override
    public void addSpecialtyToSubject(Specialty specialty, Subject subject, BigDecimal coef) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addSpecialtyToSubject"))) {

            ps.setInt(1, specialty.getId());
            ps.setInt(2, subject.getId());
            ps.setBigDecimal(3, coef);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSpecialtyToUser(Specialty specialty, User user, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addUserToSpecialty"))) {

            ps.setInt(2, specialty.getId());
            ps.setInt(1, user.getId());
            ps.setBoolean(3, passed);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(int id, String name, int quantityOfStudents, int facultyId) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateSpecialty"))) {

            ps.setString(1, name);
            ps.setInt(2, quantityOfStudents);
            ps.setInt(3, facultyId);
            ps.setInt(4, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSpecialtyToSubject(int specialtyId, int subjectId, BigDecimal coef) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateSpecialtyToSubject"))) {

            ps.setBigDecimal(1, coef);
            ps.setInt(2, specialtyId);
            ps.setInt(3, subjectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSpecialtyToUser(int specialtyId, int userId, boolean passed) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUserToSpecialty"))) {

            ps.setBoolean(1, passed);
            ps.setInt(3, specialtyId);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Specialty findById(int id) {
        Specialty specialty = null;
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.findByIdSpecialty"))) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                int facultyId = resultSet.getInt(4);
                specialty = new Specialty(name, quantityOfStudents, facultyId);
                specialty.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.deleteByIdSpecialty"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllSpecialties() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(property.getProperty("sql.clearAllSpecialties"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id) {
        HashMap<Subject, BigDecimal> listOfSubject = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllSubjectsOfSpecialty"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                    String name = resultSet.getString(2);
                    int duration = resultSet.getInt(3);
                    Subject subject = new Subject(name, duration);
                    subject.setId(resultSet.getInt(1));
                    listOfSubject.put(subject, resultSet.getBigDecimal(4));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSubject;
    }

    @Override
    public HashMap<User, Boolean> getAllUsersOfSpecialty(int id) {
        HashMap<User, Boolean> listOfUsers = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllUsersOfSpecialty"))) {

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
                User user = new User(lastName, firstName, patronymic, birthday, city, email,
                        password, role);
                user.setId(userId);
                listOfUsers.put(user, resultSet.getBoolean(10));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfUsers;
    }
}
