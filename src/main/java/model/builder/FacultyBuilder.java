package model.builder;

import model.enteties.Faculty;

/**
 * Factory builder
 * @author Olha Yuryeva
 * @version 1.0
 */
public class FacultyBuilder {
    private String name;
    private int universityId;

    /**
     * Name setter
     * @param name String
     * @return FacultyBuilder
     */
    public FacultyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * University id setter
     * @param universityId int
     * @return FacultyBuilder
     */
    public FacultyBuilder setUniversityId(int universityId) {
        this.universityId = universityId;
        return this;
    }

    /**
     * Build factory instance
     * @return Faculty
     * @see Faculty
     */
    public Faculty createFaculty() {
        return new Faculty(name, universityId);
    }
}