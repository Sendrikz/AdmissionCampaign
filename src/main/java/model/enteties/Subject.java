package model.enteties;

import java.util.Objects;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class Subject {

    private int id;
    private String name;
    private int duration;

    public Subject(String name, int duration) {
        this.name = name;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

        Subject subject = (Subject) obj;

        return this.id == subject.id && this.name.equals(subject.name) &&
                this.duration == subject.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
