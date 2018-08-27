package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.SpecialtyDao;
import model.enteties.Specialty;
import model.enteties.Subject;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class SpecialtyService {

    private static final Logger log = Logger.getLogger(SpecialtyService.class);

    public static Specialty findById(int id) {
        log.info("Start class SpecialtyService findById()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        Specialty specialty = specialtyDao.findById(id);
        log.debug(specialty);
        ConnectionManager.getInstance().close(connection);
        return specialty;
    }


    public static HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id) {
        log.info("Start class SpecialtyService getAllSubjectsOfSpecialty()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        HashMap<Subject, BigDecimal> subjectBigDecimalHashMap =
                specialtyDao.getAllSubjectsOfSpecialty(id);
        log.debug("HashMap of subjects by specialty: " + subjectBigDecimalHashMap);
        ConnectionManager.getInstance().close(connection);
        return subjectBigDecimalHashMap;
    }

    public static ArrayList<Specialty> getAll() {
        log.info("Start class SpecialtyService getAll()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        ArrayList<Specialty> listOfSpecialties = specialtyDao.getAll();
        log.debug("List of all specialties: " + listOfSpecialties);
        ConnectionManager.getInstance().close(connection);
        return listOfSpecialties;
    }
}
