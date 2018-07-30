package model.dao;

import model.enteties.Specialty;
import model.enteties.Subject;

import java.io.FileInputStream;
import java.io.IOException;
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
        String pathToFile = "src/main/resources/sql.properties";
        try {
            FileInputStream fileInputStream = new FileInputStream(pathToFile);
            property.load(fileInputStream);
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
                PreparedStatement preparedStatement = connection.prepareStatement(
                            property.getProperty("sql.findByIdSpecialty"));
                preparedStatement.setInt(1, resultSet.getInt(1));
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Specialty specialty = new Specialty(rs.getString(2),
                            rs.getInt(3), rs.getInt(4));
                    specialty.setId(rs.getInt(1));
                    listOfSpecialties.put(specialty, resultSet.getBigDecimal(2));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSpecialties;
    }
}
