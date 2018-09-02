package model.dao;

import model.enteties.Faculty;
import model.enteties.University;

import java.util.ArrayList;
import java.util.Optional;

public interface UniversityDao {

    ArrayList<University> getAll();

    void add(University university);

    void update(int id, String name, String address, String city);

    Optional<University> findById(int id);

    void deleteById(int id);
    void clearAllUniversities();

    ArrayList<Faculty> getAllFacultiesOfUniversity(int uniId);
}
