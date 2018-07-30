package model.dao;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.University;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface SpecialtyDao {

    ArrayList<Specialty> getAll();

    void add(Specialty specialty);
    void addSpecialtyToUniversity(Specialty specialty, University university);
    void addSpecialtyToSubject(Specialty specialty, Subject subject, BigDecimal coef);

    void update(int id, String name, int quantityOfStudents, int facultyId);
    void updateSpecialtyToSubject(int specialtyId, int subjectId, BigDecimal coef);

    Specialty findById(int id);

    void deleteById(int id);
    void clearAllSpecialties();

    ArrayList<University> getAllUniversitiesBySpecialty(int id);
    HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id);
}
