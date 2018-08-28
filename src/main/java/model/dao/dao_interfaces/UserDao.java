package model.dao.dao_interfaces;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface UserDao {

    ArrayList<User> getAll();
    ArrayList<User> getAllStudents(int i);

    void add(User user);
    void addUserToSubject(User user, Subject subject, boolean checked, BigDecimal grade);
    void addUserToSpecialty(User user, Specialty specialty, boolean passed);

    User findById(int id);

    void update(int id, String lastName, String firstName, String patronymic,
                String birthday, String city, int role);
    void updateEmail(int id, String value);
    void updatePassword(int id, String value);
    void updateUserToSubject(int userId, int subjectId, boolean checked, BigDecimal grade);
    void updateUserToSpecialty(int userId, int specialtyId, boolean passed);

    void deleteById(int id);
    void deleteUserFromSpecialtiesExcept(int user_id, int specialty_id);
    void clearAllUsers();

    HashMap<Subject, BigDecimal> getAllCheckedSubjectsByUser(int id);
    ArrayList<Subject> getAllSubjectsByUser(int id);
    ArrayList<Subject> getAllUncheckedSubjectsByUser(int id);

    HashMap<Specialty, Boolean> getAllSpecialtiesByUser(int id);
}
