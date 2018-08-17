package model.dao.dao_interfaces;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.University;
import model.enteties.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface SpecialtyDao {

    ArrayList<Specialty> getAll();

    void add(Specialty specialty);
    void addSpecialtyToSubject(Specialty specialty, Subject subject, BigDecimal coef);
    void addSpecialtyToUser(Specialty specialty, User user, boolean passed);

    void update(int id, String name, int quantityOfStudents, int facultyId);
    void updateSpecialtyToSubject(int specialtyId, int subjectId, BigDecimal coef);
    void updateSpecialtyToUser(int specialtyId, int userId, boolean passed);

    Specialty findById(int id);

    void deleteById(int id);
    void clearAllSpecialties();


    HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id);
    HashMap<User, Boolean> getAllUsersOfSpecialty(int id);
}
