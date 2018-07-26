package model.dao;

import model.enteties.User;

import java.util.ArrayList;

public interface UserDao {

    ArrayList<User> getAll();

    void add(User user);

    User findById(int id);

    void update(int id, String lastName, String firstName, String patronymic,
                String birthday, String city, int role);
    void updateEmail(int id, String value);
    void updatePassword(int id, String value);

    void deleteById(int id);
    void clearAllUsers();

}
