package model.dao;

import model.enteties.Subject;
import model.enteties.User;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface UserDao {

    ArrayList<User> getAll();

    void add(User user);
    //void addUserToSubject(User user, Subject subject, boolean checked, BigDecimal grade);

    User findById(int id);

    void update(int id, String lastName, String firstName, String patronymic,
                String birthday, String city, int role);
    void updateEmail(int id, String value);
    void updatePassword(int id, String value);
   // void updateUserToSubject(int userId, int subjectId, boolean checked, BigDecimal grade);

    void deleteById(int id);
    void clearAllUsers();

    //ArrayList<Subject> getAllSubjectsOfUser(int id);

}
