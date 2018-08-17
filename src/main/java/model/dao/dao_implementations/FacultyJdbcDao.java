package model.dao.dao_implementations;

import model.dao.dao_interfaces.FacultyDao;
import model.enteties.Faculty;
import model.enteties.Specialty;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class FacultyJdbcDao implements FacultyDao {

    private Connection connection;
    private Properties property;

    public FacultyJdbcDao(Connection connection) {
        this.connection = connection;
        this.property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Faculty> getAll() {
        ArrayList<Faculty> listOfFaculties = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(property.getProperty("sql.getAllFaculties"));

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int uniId = resultSet.getInt(3);
                Faculty faculty = new Faculty(name, uniId);
                faculty.setId(id);
                listOfFaculties.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfFaculties;
    }

    @Override
    public void add(Faculty faculty) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.addFaculty"), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, faculty.getName());
            ps.setInt(2, faculty.getUniversityId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatesKeys = ps.getGeneratedKeys()) {
                if (generatesKeys.next()) {
                    faculty.setId(generatesKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, String name, int uniId) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.updateFaculty"))) {
            ps.setString(1, name);
            ps.setInt(2, uniId);
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Faculty findById(int id) {
        Faculty faculty = null;
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.findByIdFaculty"))) {
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                faculty = new Faculty(resultSet.getString(2), resultSet.getInt(3));
                faculty.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculty;
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.deleteByIdFaculty"))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllFaculties() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(property.getProperty("sql.clearAllFaculties"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Specialty> getAllSpecialtiesOfFaculty(int id) {
        ArrayList<Specialty> listOfSpecialties = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                property.getProperty("sql.getAllSpecialtiesByFaculty"))) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int specialtyId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                Specialty specialty = new Specialty(name, quantityOfStudents, id);
                specialty.setId(specialtyId);
                listOfSpecialties.add(specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSpecialties;
    }
}
