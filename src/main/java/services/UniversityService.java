package services;

import model.connection.ConnectionManager;
import model.dao.factory.DaoFactory;
import model.dao.UniversityDao;
import model.enteties.Faculty;
import model.enteties.University;
import org.apache.log4j.Logger;
import services.exceptions.NoSuchUniversityException;

import java.io.Closeable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;

public class UniversityService implements Closeable {

    private Connection connection;
    private UniversityDao universityDao;
    private static final Logger log = Logger.getLogger(UniversityService.class);

    public UniversityService() {
        connection = ConnectionManager.getInstance().getConnection();
        universityDao = DaoFactory.getUniversityDao(connection);
    }

    UniversityService(Connection connection) {
        this.connection = connection;
        universityDao = DaoFactory.getUniversityDao(connection);
    }

    public ArrayList<University> getAllUniversities() {
        log.info("Start class UniversityService getAllUniversities()");

        ArrayList<University> listOfUniversities = universityDao.getAll();
        log.debug("List of universities: " + listOfUniversities);

        return listOfUniversities;
    }

    public HashSet<String> getAllCities() {
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

    public ArrayList<Faculty> getAllFacultiesOfUniversity(int uniId) {
        log.info("Start class UniversityService getAllFacultiesOfUniversity()");

        ArrayList<Faculty> facultyArrayList = universityDao.getAllFacultiesOfUniversity(uniId);
        log.debug("List of all faculties by university: " + facultyArrayList);

        return facultyArrayList;
    }

    public University findById(int id) throws NoSuchUniversityException {
        log.info("Start class UniversityService getUniversityById()");

        if (universityDao.findById(id).isPresent()) {
            University result = universityDao.findById(id).get();
            log.debug("Finded university: " + result);

            return result;
        }
        throw new NoSuchUniversityException();
    }

    @Override
    public void close() {
        ConnectionManager.getInstance().close(connection);
    }

}
