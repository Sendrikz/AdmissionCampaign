package model.builder;

import model.enteties.Role;

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