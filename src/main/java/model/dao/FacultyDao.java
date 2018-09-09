package model.dao;

import model.enteties.Faculty;
import model.enteties.Specialty;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public interface FacultyDao {

    ArrayList<Faculty> getAll();

    void add(Faculty faculty);

    void update(int id, String name, int uniId);

    Optional<Faculty> findById(int id);

    void deleteById(int id);
    void clearAllFaculties();

    ArrayList<Specialty> getAllSpecialtiesOfFaculty(int id);
}
