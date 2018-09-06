package services;

import model.builder.FacultyBuilder;
import model.builder.SpecialtyBuilder;
import model.builder.UniversityBuilder;
import model.connection.ConnectionManager;
import model.dao.SpecialtyDao;
import model.dao.factory.DaoFactory;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.University;
import model.enteties_enum.Faculties;
import model.enteties_enum.Specialties;
import model.enteties_enum.Universities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.exceptions.NoSuchSpecialtyException;

import java.sql.Connection;

public class SpecialtyServiceTest {

    Connection connection;
    private Faculty faculty;
    private University university;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        university = new UniversityBuilder()
                .setName(Universities.NaUKMA.getName())
                .setAddress(Universities.NaUKMA.getAddress())
                .setCity(Universities.NaUKMA.getCity())
                .createUniversity();
        faculty = new FacultyBuilder().setName(Faculties.IT.getName())
                .setUniversityId(university.getId())
                .createFaculty();
    }

    @After
    public void tearDown() {
        ConnectionManager.getInstance().close(connection);
    }


    @Test(expected = NoSuchSpecialtyException.class)
    public void findByIdTest() throws NoSuchSpecialtyException {
        Specialty specialty = setUpNewCompSpecialty();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        specialtyDao.clearAllSpecialties();
        try (SpecialtyService specialtyService = new SpecialtyService(connection)) {
            specialtyService.findById(specialty.getId());
        }
    }

    private Specialty setUpNewCompSpecialty() {
        return new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName())
                .setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents())
                .setFacultyId(faculty.getId()).createSpecialty();
    }
}
