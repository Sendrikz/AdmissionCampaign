package model.dao.dao_implementations;

import java.io.InputStream;
import java.sql.*;

import model.dao.dao_interfaces.UniversityDao;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.University;

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
        this.property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                String city = resultSet.getString(4);
                University uni = new University(name, address, city);
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
            ps.setString(3, university.getCity());

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
    public void update(int id, String name, String address, String city) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateUniversity"))) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, city);
            ps.setInt(4, id);
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
                String city = resultSet.getString(4);
                uni = new University(name, address, city);
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
    public ArrayList<Faculty> getAllFacultiesOfUniversity(int uniId) {
        ArrayList<Faculty> listOfFaculties = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllFacultiesOfUniversity"))) {

            ps.setInt(1, uniId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                    String name = resultSet.getString(2);
                    int universityId = resultSet.getInt(3);
                    Faculty faculty = new Faculty(name, universityId);
                    faculty.setId(resultSet.getInt(1));
                listOfFaculties.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfFaculties;
    }
}
