package model.builder;

import model.enteties.Subject;

/**
 * Subject builder
 * @author Olha Yuryeva
 * @version 1.0
 */

public class SubjectBuilder {

    private String name;
    private int duration;

    public SubjectBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SubjectBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public Subject createSubject() {
        return new Subject(name, duration);
    }
}