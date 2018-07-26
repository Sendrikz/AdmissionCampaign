package model.dao;

import model.enteties.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    void add(User user);

    User findById(int id);

    // UPDATE
    // void update(int id, String content);

    // DELETE
    // void deleteById(int id);
    // void clearAllTasks();

}
