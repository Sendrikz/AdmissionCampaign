package model.dao;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public interface SpecialtyDao {

    ArrayList<Specialty> getAll();
    ArrayList<Specialty> getAll(int currentPage, int recordsPerPage);

    void add(Specialty specialty);
    void addSpecialtyToSubject(Specialty specialty, Subject subject, BigDecimal coef);
    void addSpecialtyToUser(Specialty specialty, User user, boolean passed);

    void update(int id, String name, int quantityOfStudents, int facultyId);
    void updateSpecialtyToSubject(int specialtyId, int subjectId, BigDecimal coef);
    void updateSpecialtyToUser(int specialtyId, int userId, boolean passed);

    Optional<Specialty> findById(int id);

    void deleteById(int id);
    void clearAllSpecialties();


    HashMap<Subject, BigDecimal> getAllSubjectsOfSpecialty(int id);
    HashMap<User, Boolean> getAllUsersOfSpecialty(int id);

    int getNumberOfRows();
}
