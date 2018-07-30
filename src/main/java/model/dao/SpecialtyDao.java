package model.dao;

import model.enteties.Specialty;
import model.enteties.University;

import java.util.ArrayList;

public interface SpecialtyDao {

    ArrayList<Specialty> getAll();

    void add(Specialty specialty);
    void addSpecialtyToUniversity(Specialty specialty, University university);

    void update(int id, String name, int quantityOfStudents, int facultyId);

    Specialty findById(int id);

    void deleteById(int id);
    void clearAllSpecialties();

    ArrayList<University> getAllUniversitiesBySpecialty(int id);
}
