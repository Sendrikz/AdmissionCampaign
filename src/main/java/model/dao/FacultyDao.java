package model.dao;

import model.enteties.Faculty;
import model.enteties.Specialty;

import java.util.ArrayList;
import java.util.Optional;

public interface FacultyDao {

    ArrayList<Faculty> getAll();

    void add(Faculty faculty);

    void update(int id, String name, int uniId);

    Optional<Faculty> findById(int id);

    void deleteById(int id);
    void clearAllFaculties();

    ArrayList<Specialty> getAllSpecialtiesOfFaculty(int id);
}
