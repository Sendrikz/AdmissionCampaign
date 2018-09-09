package services;

import model.builder.UniversityBuilder;
import model.connection.ConnectionManager;
import model.dao.UniversityDao;
import model.dao.factory.DaoFactory;
import model.enteties.University;
import model.enteties_enum.Universities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.exceptions.NoSuchUniversityException;

import java.sql.Connection;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class UniversityServiceTest {

    private Connection connection;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
    }

    @After
    public void tearDown() {
        ConnectionManager.getInstance().close(connection);
    }

    private University setUpNewNaUKMA() {
        return new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
    }

    @Test(expected = NoSuchUniversityException.class)
    public void findByIdTest() throws NoSuchUniversityException {
        University uni = setUpNewNaUKMA();
        UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
        universityDao.clearAllUniversities();
        try (UniversityService universityService = new UniversityService(true)) {
            universityService.findById(uni.getId());
        }
    }

}
