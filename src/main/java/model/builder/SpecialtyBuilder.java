package model.builder;

import model.enteties.Specialty;

public class SpecialtyBuilder {
    private String name;
    private int quantityOfStudents;
    private int facultyId;

    public SpecialtyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SpecialtyBuilder setQuantityOfStudents(int quantityOfStudents) {
        this.quantityOfStudents = quantityOfStudents;
        return this;
    }

    public SpecialtyBuilder setFacultyId(int facultyId) {
        this.facultyId = facultyId;
        return this;
    }

    public Specialty createSpecialty() {
        return new Specialty(name, quantityOfStudents, facultyId);
    }
}