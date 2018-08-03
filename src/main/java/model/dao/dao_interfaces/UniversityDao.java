package model.dao.dao_interfaces;

import model.enteties.Specialty;
import model.enteties.University;

import java.io.InputStream;
import java.util.ArrayList;

public interface UniversityDao {

    ArrayList<University> getAll();

    void add(University university);
    void addUniversityToSpecialty(University university, Specialty specialty);

    void update(int id, String name, String address);

    University findById(int id);

    void deleteById(int id);
    void clearAllUniversities();

    ArrayList<Specialty> getAllSpecialtiesOfUniversity(int universityId);
}
