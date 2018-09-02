package services;

import model.connection.ConnectionManager;
import model.dao.factory.DaoFactory;
import model.dao.SpecialtyDao;
import model.enteties.Specialty;
import model.enteties.Subject;
import org.apache.log4j.Logger;
import services.exceptions.NoSuchSpecialtyException;

import java.io.Closeable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class SpecialtyService implements Closeable {

    private Connection connection;
    private SpecialtyDao specialtyDao;
    private static final Logger log = Logger.getLogger(SpecialtyService.class);

    public SpecialtyService() {
        connection = ConnectionManager.getInstance().getConnection();
        specialtyDao = DaoFactory.getSpecialtyDao(connection);
    }

    public Specialty findById(int id) throws NoSuchSpecialtyException {
        log.info("Start class SpecialtyService findById()");

        if (specialtyDao.findById(id).isPresent()) {
            Specialty specialty = specialtyDao.findById(id).get();
            log.debug(specialty);
            return specialty;
        }
        throw new NoSuchSpecialtyException();
    }


    public HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id) {
        log.info("Start class SpecialtyService getAllSubjectsOfSpecialty()");

        HashMap<Subject, BigDecimal> subjectBigDecimalHashMap =
                specialtyDao.getAllSubjectsOfSpecialty(id);
        log.debug("HashMap of subjects by specialty: " + subjectBigDecimalHashMap);

        return subjectBigDecimalHashMap;
    }

    public ArrayList<Specialty> getAll() {
        log.info("Start class SpecialtyService getAll()");

        ArrayList<Specialty> listOfSpecialties = specialtyDao.getAll();
        log.debug("List of all specialties: " + listOfSpecialties);

        return listOfSpecialties;
    }

    public ArrayList<Specialty> getAll(int currentPage, int recordsPerPage) {
        log.info("Start class SpecialtyService getAll(int currentPage, int recordsPerPage)");

        ArrayList<Specialty> listOfSpecialties = specialtyDao.getAll(currentPage, recordsPerPage);
        log.debug("List of specialties pagination: " + listOfSpecialties);

        return listOfSpecialties;
    }

    public int getRows() {
        log.info("Start class SpecialtyService  getRows()");

        int rows = specialtyDao.getNumberOfRows();
        log.debug("Rows in table Specialty: " + rows);

        return rows;
    }

    @Override
    public void close() {
        ConnectionManager.getInstance().close(connection);
    }
}
