package model.enteties_enum;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public enum Faculties {
    IT("Факультет інформатики"),
    ECONOMIC("Факультет економічних наук"),
    BIOLOGY("Факультет природничих наук");

    private String name;

    Faculties(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
