package model.dao;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.HashMap;

public interface SubjectDao {

    ArrayList<Subject> getAll();

    void add(Subject subject);
    void addSubjectToSpecialty(Subject subject, Specialty specialty, BigDecimal coef);
    void addSubjectToUser(Subject subject, User user, boolean checked, BigDecimal grade);

    void update(int id, String name, int duration);
    void updateSubjectToSpecialty(int subjectId, int specialtyId, BigDecimal coef);
    void updateSubjectToUser(int subjectId, int userId, boolean checked, BigDecimal grade);

    Subject findById(int id);

    void deleteById(int id);
    void clearAllSubjects();

    HashMap<Specialty, BigDecimal> getAllSpecialtiesBySubject(int subjectId);

    HashMap<User, BigDecimal> getAllUsersWithCheckedSubjects(int id);
    ArrayList<User> getAllUsersBySubject(int id);
    ArrayList<User> getAllUsersWithUncheckedSubject(int id);
}
