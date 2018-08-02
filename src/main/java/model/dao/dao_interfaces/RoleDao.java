package model.dao.dao_interfaces;

import model.enteties.Role;
import model.enteties.User;

import java.util.ArrayList;

public interface RoleDao {

    ArrayList<Role> getAll();

    void add(Role role);

    void update(int id, String value);

    Role findById(int id);

    void deleteById(int id);
    void clearAllRoles();

    ArrayList<User> getAllUsersByRole(int roleId);
}
