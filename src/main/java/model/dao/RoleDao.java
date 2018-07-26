package model.dao;

import model.enteties.Role;

import java.util.ArrayList;

public interface RoleDao {

    ArrayList<Role> getAll();

    void add(Role role);

    Role findById(int id);

    void deleteById(int id);
    void clearAllRoles();

}
