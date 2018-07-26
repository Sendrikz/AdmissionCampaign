package model.dao;

import model.enteties.User;

import java.util.ArrayList;

public interface UserDao {

    ArrayList<User> getAll();

    void add(User user);

    User findById(int id);

    void update(int id, String content);

    void deleteById(int id);
    void clearAllTasks();

}
