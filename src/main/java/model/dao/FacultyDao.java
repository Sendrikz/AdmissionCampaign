package model.dao;

import model.enteties.Faculty;
import model.enteties.Specialty;

import java.util.ArrayList;

public interface FacultyDao {

    ArrayList<Faculty> getAll();

    void add(Faculty faculty);

    void update(int id, String name);

    Faculty findById(int id);

    void deleteById(int id);
    void clearAllFaculties();

    ArrayList<Specialty> getAllSpecialtiesOfFaculty(int id);
}
