package model.builder;

import model.enteties.Role;

/**
 * Role builder
 * @author Olha Yuryeva
 * @version 1.0
 */

public class RoleBuilder {
    private String name;

    public RoleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Role createRole() {
        return new Role(name);
    }
}