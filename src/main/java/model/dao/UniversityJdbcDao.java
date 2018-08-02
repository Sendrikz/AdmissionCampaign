package model.dao;

import java.io.File;
import java.io.InputStream;
import java.sql.*;

import model.enteties.Specialty;
import model.enteties.University;

import javax.print.attribute.standard.PresentationDirection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class UniversityJdbcDao implements UniversityDao {

    private Connection connection;
    private Properties property;

    public UniversityJdbcDao(Connection connection) {
        this.connection = connection;
    }

    public void setPath(InputStream path) {
        property = new Properties();
        try {
            property.load(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTestPath() {
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
    public ArrayList<University> getAll() {
        ArrayList<University> listOfUniversities = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(property.getProperty("sql.getAllUniversities"));

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                University uni = new University(name, address);
                uni.setId(id);
                listOfUniversities.add(uni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUniversities;
    }

    @Override
    public void add(University university) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addUniversity"), Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, university.getName());
            ps.setString(2, university.getAddress());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    university.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUniversityToSpecialty(University university, Specialty specialty) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addSpecialtyToUniversity"))) {

            ps.setInt(2, university.getId());
            ps.setInt(1, specialty.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, String name, String address) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUniversity"))) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public University findById(int id) {
        University uni = null;
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.findByIdUniversity"))) {
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                uni = new University(name, address);
                uni.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uni;
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.deleteByIdUniversity"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllUniversities() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(property.getProperty("sql.clearAllUniversities"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Specialty> getAllSpecialtiesOfUniversity(int universityId) {
        ArrayList<Specialty> listOfSpecialties = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllSpecialtiesOfUniversity"))) {

            ps.setInt(1, universityId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                    String name = resultSet.getString(2);
                    int quantityOfStudents = resultSet.getInt(3);
                    int facultyId = resultSet.getInt(4);
                    Specialty specialty = new Specialty(name, quantityOfStudents, facultyId);
                    specialty.setId(resultSet.getInt(1));
                    listOfSpecialties.add(specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSpecialties;
    }
}
