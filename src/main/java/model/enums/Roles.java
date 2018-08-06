package model.enums;

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
