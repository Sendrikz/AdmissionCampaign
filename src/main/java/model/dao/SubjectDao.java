package model.dao;

import model.enteties.Specialty;
import model.enteties.Subject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface SubjectDao {

    ArrayList<Subject> getAll();

    void add(Subject subject);
    void addSubjectToSpecialty(Subject subject, Specialty specialty, BigDecimal coef);

    void update(int id, String name, int duration);
    void updateSubjectToSpecialty(int subjectId, int specialtyId, BigDecimal coef);

    Subject findById(int id);

    void deleteById(int id);
    void clearAllSubjects();

    ArrayList<Specialty> getAllSpecialtiesBySubject(int subjectId);
}
