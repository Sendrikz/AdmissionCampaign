package model.builder;

import model.enteties.Faculty;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class FacultyBuilder {
    private String name;
    private int universityId;

    public FacultyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FacultyBuilder setUniversityId(int universityId) {
        this.universityId = universityId;
        return this;
    }

    public Faculty createFaculty() {
        return new Faculty(name, universityId);
    }
}