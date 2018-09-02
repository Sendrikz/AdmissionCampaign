package model.dao;

import model.enteties.Role;
import model.enteties.User;

import java.util.ArrayList;
import java.util.Optional;

public interface RoleDao {

    ArrayList<Role> getAll();

    void add(Role role);

    void update(int id, String value);

    Optional<Role> findById(int id);
    int findIdByRoleName(String name);

    void deleteById(int id);
    void clearAllRoles();

    ArrayList<User> getAllUsersByRole(int roleId);
}
