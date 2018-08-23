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

    private static final Logger log = Logger.getLogger(UniversityService.class);

    public static ArrayList<University> getAllUniversities() {
        log.info("Start class UniversityService getAllUniversities()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
        ArrayList<University> listOfUniversities = universityDao.getAll();
        log.debug("List of universities: " + listOfUniversities);
        ConnectionManager.getInstance().close(connection);
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
        Connection connection = ConnectionManager.getInstance().getConnection();
        UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
        ArrayList<Faculty> facultyArrayList = universityDao.getAllFacultiesOfUniversity(uniId);
        log.debug("List of all faculties by university: " + facultyArrayList);
        ConnectionManager.getInstance().close(connection);
        return facultyArrayList;
    }

    public static University findById(int id) {
        log.info("Start class UniversityService getUniversityById()");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
        University result = universityDao.findById(id);
        log.debug("Finded university: " + result);
        ConnectionManager.getInstance().close(connection);
        return result;
    }
}
