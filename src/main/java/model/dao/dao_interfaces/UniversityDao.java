package model.dao.dao_interfaces;

import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.University;

import java.io.InputStream;
import java.util.ArrayList;

public interface UniversityDao {

    ArrayList<University> getAll();

    void add(University university);

    void update(int id, String name, String address, String city);

    University findById(int id);

    void deleteById(int id);
    void clearAllUniversities();

    ArrayList<Faculty> getAllFacultiesOfUniversity(int uniId);
}
