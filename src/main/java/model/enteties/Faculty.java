package model.enteties;

import java.util.Objects;

public class Faculty {

    private int id;
    private String name;
    private int universityId;

    public Faculty (String name, int universityId) {
        this.name = name;
        this.universityId = universityId;
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

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id &&
                universityId == faculty.universityId &&
                Objects.equals(name, faculty.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, universityId);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", university=" + universityId +
                '}';
    }
}
