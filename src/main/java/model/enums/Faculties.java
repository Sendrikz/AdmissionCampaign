package model.enums;

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
