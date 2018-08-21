package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.SpecialtyDao;
import model.enteties.Specialty;
import model.enteties.Subject;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;

public class SpecialtyService {

    private static final Logger log = Logger.getLogger(String.valueOf(SpecialtyService.class));

    public static Specialty findById(int id) {
        log.info("Start class SpecialtyService findById()");
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        Specialty specialty = specialtyDao.findById(id);
        log.debug(specialty);
        connectionManager.close(connection);
        return specialty;
    }


    public static HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id) {
        log.info("Start class SpecialtyService getAllSubjectsOfSpecialty()");
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        HashMap<Subject, BigDecimal> subjectBigDecimalHashMap =
                specialtyDao.getAllSubjectsOfSpecialty(id);
        log.debug("HashMap of subjects by specialty: " + subjectBigDecimalHashMap);
        connectionManager.close(connection);
        return subjectBigDecimalHashMap;
    }
}
