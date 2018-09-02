package services;

import model.connection.ConnectionManager;
import model.dao.factory.DaoFactory;
import model.dao.FacultyDao;
import model.enteties.Faculty;
import model.enteties.Specialty;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.sql.Connection;
import java.util.ArrayList;

public class FacultyService implements Closeable {

    private Connection connection;
    private FacultyDao facultyDao;
    private static final Logger log = Logger.getLogger(FacultyService.class);

    public FacultyService() {
        connection = ConnectionManager.getInstance().getConnection();
        facultyDao = DaoFactory.getFacultyDao(connection);
    }

    public ArrayList<Faculty> getAll() {
        log.info("Start class FacultyService getAll()");

        return facultyDao.getAll();
    }

    public ArrayList<Specialty> getAllSpecialtiesOfFaculty(int id) {
        log.info("Start class FacultyService getAllSpecialtiesOfFaculty()");

        return facultyDao.getAllSpecialtiesOfFaculty(id);
    }

    @Override
    public void close() {
        ConnectionManager.getInstance().close(connection);
    }

}
