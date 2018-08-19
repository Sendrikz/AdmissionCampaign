package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.SpecialtyDao;
import model.enteties.Specialty;
import org.apache.log4j.Logger;

import java.sql.Connection;

public class SpecialtyService {

    private static final Logger log = Logger.getLogger(String.valueOf(SpecialtyService.class));

    public static Specialty findById(int id) {
        log.info("Start class SpecialtyService findById()");
        Connection connection = new ConnectionManager().getConnection();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        return specialtyDao.findById(id);
    }
}
