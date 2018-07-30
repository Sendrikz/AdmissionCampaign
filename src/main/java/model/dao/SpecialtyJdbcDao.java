package model.dao;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.University;

import java.io.FileInputStream;
import java.io.IOException;
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
        property = new Properties();
        String pathToFile = "src/main/resources/sql.properties";
        try {
            FileInputStream fis = new FileInputStream(pathToFile);
            property.load(fis);
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
    public void addSpecialtyToUniversity(Specialty specialty, University university) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addSpecialtyToUniversity"))) {

            ps.setInt(1, specialty.getId());
            ps.setInt(2, university.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
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
    public ArrayList<University> getAllUniversitiesBySpecialty(int id) {
        ArrayList<University> listOfUni = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllUniversitiesBySpecialty"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        property.getProperty("sql.findByIdUniversity"));
                preparedStatement.setInt(1, resultSet.getInt(1));
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int uniId = rs.getInt(1);
                    University uni = new University(rs.getString(2), rs.getString(3));
                    uni.setId(uniId);
                    listOfUni.add(uni);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUni;
    }

    @Override
    public HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id) {
        HashMap<Subject, BigDecimal> listOfSubject = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllSubjectsOfSpecialty"))) {

            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        property.getProperty("sql.findByIdSubject"));
                preparedStatement.setInt(1, resultSet.getInt(1));
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String name = rs.getString(2);
                    int duration = rs.getInt(3);
                    Subject subject = new Subject(name, duration);
                    subject.setId(rs.getInt(1));
                    listOfSubject.put(subject, resultSet.getBigDecimal(2));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSubject;
    }
}
