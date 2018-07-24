package model.enteties;

import java.util.Objects;

public class Faculty {

    private int id;
    private String name;

    public Faculty (String name) {
        this.name = name;
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

        Faculty faculty = (Faculty) obj;

        return this.id == faculty.id && this.name.equals(faculty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
