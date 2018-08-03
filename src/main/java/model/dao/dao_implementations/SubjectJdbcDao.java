package model.dao.dao_implementations;

import model.dao.dao_interfaces.SubjectDao;
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

public class SubjectJdbcDao implements SubjectDao {

    private Connection connection;
    private Properties property;

    public SubjectJdbcDao(Connection connection) {
        this.connection = connection;
        property = new Properties();
        this.property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
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
                Subject subject = new Subject(name, duration);
                subject.setId(id);
                listOfSubjects.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSubjects;
    }

    @Override
    public void add(Subject subject) {
        try (PreparedStatement ps =connection.prepareStatement(
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
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Override
    public Subject findById(int id) {
        Subject subject = null;
        try (PreparedStatement ps =connection.prepareStatement(
                property.getProperty("sql.findByIdSubject"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int duration = resultSet.getInt(3);
                subject = new Subject(name, duration);
                subject.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.deleteByIdSubject"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllSubjects() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(property.getProperty("sql.clearAllSubjects"));

        } catch (SQLException e) {
            e.printStackTrace();
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
                    Specialty specialty = new Specialty(resultSet.getString(2),
                            resultSet.getInt(3), resultSet.getInt(4));
                    specialty.setId(resultSet.getInt(1));
                    listOfSpecialties.put(specialty, resultSet.getBigDecimal(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                User user = new User(lastName, firstName, patronymic, birthday, city, email,
                        password, role);
                user.setId(userId);
                listOfUsers.put(user, resultSet.getBigDecimal(10));
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
                User user = new User(lastName, firstName, patronymic, birthday, city, email,
                        password, role);
                user.setId(resultSet.getInt(1));
                listOfUsers.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
                User user = new User(lastName, firstName, patronymic, birthday, city, email,
                        password, resultSet.getInt(9));
                user.setId(resultSet.getInt(1));
                listOfUsers.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }
}
