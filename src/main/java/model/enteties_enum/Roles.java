package model.enteties_enum;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public enum Roles {

    ADMINISTRATOR("Адміністратор"),
    STUDENT("Студент");

    private String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
