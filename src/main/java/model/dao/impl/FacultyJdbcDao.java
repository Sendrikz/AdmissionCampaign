package model.dao.impl;

import utils.property_loaders.LoadSQLProperties;
import utils.Strings;
import model.dao.FacultyDao;
import model.enteties.Faculty;
import model.builder.FacultyBuilder;
import model.enteties.Specialty;
import model.builder.SpecialtyBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class FacultyJdbcDao implements FacultyDao {

    private static final Logger log = Logger.getLogger(FacultyJdbcDao.class);
    private Connection connection;

    public FacultyJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Faculty> getAll() {
        ArrayList<Faculty> listOfFaculties = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_FACULTY_PROPERTIES,
                            Strings.GET_ALL_FACULTIES));

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int uniId = resultSet.getInt(3);
                Faculty faculty = new FacultyBuilder()
                        .setName(name)
                        .setUniversityId(uniId)
                        .createFaculty();
                faculty.setId(id);
                listOfFaculties.add(faculty);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfFaculties;
    }

    @Override
    public void add(Faculty faculty) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_FACULTY_PROPERTIES,
                        Strings.ADD_FACULTY), Statement.RETURN_GENERATED_KEYS)) {
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
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(int id, String name, int uniId) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_FACULTY_PROPERTIES,
                        Strings.UPDATE_FACULTY))) {
            ps.setString(1, name);
            ps.setInt(2, uniId);
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Faculty> findById(int id) {
        Faculty faculty = null;
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_FACULTY_PROPERTIES,
                        Strings.FIND_BY_ID_FACULTY))) {
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                faculty = new FacultyBuilder()
                        .setName(resultSet.getString(2))
                        .setUniversityId(resultSet.getInt(3))
                        .createFaculty();
                faculty.setId(id);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(faculty);
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_FACULTY_PROPERTIES,
                        Strings.DELETE_BY_ID_FACULTY))) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void clearAllFaculties() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(LoadSQLProperties.getInstance()
                    .getConfigProperty(Strings.SQL_FACULTY_PROPERTIES, Strings.CLEAR_ALL_FACULTIES));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public ArrayList<Specialty> getAllSpecialtiesOfFaculty(int id) {
        ArrayList<Specialty> listOfSpecialties = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                LoadSQLProperties.getInstance().getConfigProperty(Strings.SQL_FACULTY_PROPERTIES,
                        Strings.GET_ALL_SPECIALTIES_BY_FACULTY))) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int specialtyId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int quantityOfStudents = resultSet.getInt(3);
                Specialty specialty = new SpecialtyBuilder()
                        .setName(name)
                        .setQuantityOfStudents(quantityOfStudents)
                        .setFacultyId(id)
                        .createSpecialty();
                specialty.setId(specialtyId);
                listOfSpecialties.add(specialty);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return listOfSpecialties;
    }
}
