package model.enteties;

import java.util.Objects;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class Specialty {

    private int id;
    private String name;
    private int quantityOfStudents;
    private int facultyId;

    public Specialty(String name, int quantityOfStudents, int facultyId) {
        this.name = name;
        this.quantityOfStudents = quantityOfStudents;
        this.facultyId = facultyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityOfStudents() {
        return quantityOfStudents;
    }

    public void setQuantityOfStudents(int quantityOfStudents) {
        this.quantityOfStudents = quantityOfStudents;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Specialty specialty = (Specialty) obj;

        return this.id == specialty.id && this.name.equals(specialty.name) &&
                this.quantityOfStudents == specialty.quantityOfStudents &&
                this.facultyId == specialty.facultyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantityOfStudents, facultyId);
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityOfStudents=" + quantityOfStudents +
                ", facultyId=" + facultyId +
                '}';
    }
}
