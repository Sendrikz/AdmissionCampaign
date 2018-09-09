package model.enteties_enum;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public enum Specialties {

    ENGINEERING("Інженерія програмного забезпечення", 80),
    COMPUTER_SCIENCE("Комп'ютерні науки", 60),
    MARKETING("Маркетинг", 40);

    private String name;
    private int quantityOfStudents;

    Specialties(String name, int quantityOfStudents) {
        this.name = name;
        this.quantityOfStudents = quantityOfStudents;
    }

    public String getName() {
        return name;
    }

    public int getQuantityOfStudents() {
        return quantityOfStudents;
    }
}
