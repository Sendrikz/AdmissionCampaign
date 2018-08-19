package services;

import model.connection.ConnectionManager;
import model.dao.dao_implementations.DaoFactory;
import model.dao.dao_interfaces.UniversityDao;
import model.enteties.Faculty;
import model.enteties.University;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class UniversityService {

    private static final Logger log = Logger.getLogger(String.valueOf(UniversityService.class));

    public static ArrayList<University> getAllUniversities() {
        log.info("Start class UniversityService getAllUniversities()");
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        ArrayList<University> listOfUniversities = null;
        try {
            connection.setAutoCommit(false);
            log.info("Start transaction");
            UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
            listOfUniversities = universityDao.getAll();
            log.debug("List of universities: " + listOfUniversities);
            connection.commit();
            log.info("End transaction");
        } catch (SQLException e) {
            log.info("Transaction fail");
            log.error(e.getMessage());
        }
        connectionManager.close(connection);
        return listOfUniversities;
    }

    public static HashSet<String> getAllCities() {
        log.info("Start class UniversityService getAllCities()");
        HashSet<String> cityHashMap = new HashSet<>();
        ArrayList<University> listOfUniversities = getAllUniversities();
        log.debug(listOfUniversities);
        for (University uni : listOfUniversities) {
            cityHashMap.add(uni.getCity());
        }
        log.debug(cityHashMap);
        return cityHashMap;
    }

    public static ArrayList<Faculty> getAllFacultiesOfUniversity(int uniId) {
        log.info("Start class UniversityService getAllFacultiesOfUniversity()");
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
        UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
        ArrayList<Faculty> facultyArrayList = universityDao.getAllFacultiesOfUniversity(uniId);
        log.debug("List of all faculties by university: " + facultyArrayList);
        return facultyArrayList;
    }

    public static University findById(int id) {
        log.info("Start class UniversityService getUniversityById()");
        Connection connection = new ConnectionManager().getConnection();
        UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
        University result = universityDao.findById(id);
        log.debug("Finded university: " + result);
        return result;
    }
}
