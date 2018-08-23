package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.FacultyDao;
import model.enteties.Faculty;
import model.enteties.Specialty;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;

public class FacultyService {

    private static final Logger log = Logger.getLogger(FacultyService.class);

    public static ArrayList<Faculty> getAll() {
        log.info("Start class FacultyService getAll()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        FacultyDao facultyDao = DaoFactory.getFacultyDao(connection);
        ArrayList<Faculty> listOfFaculties = facultyDao.getAll();
        ConnectionManager.getInstance().close(connection);
        return listOfFaculties;
    }

    public static ArrayList<Specialty> getAllSpecialtiesOfFaculty(int id) {
        log.info("Start class FacultyService getAllSpecialtiesOfFaculty()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        FacultyDao facultyDao = DaoFactory.getFacultyDao(connection);
        ArrayList<Specialty> listOfSpecialties = facultyDao.getAllSpecialtiesOfFaculty(id);
        ConnectionManager.getInstance().close(connection);
        return listOfSpecialties;
    }
}
