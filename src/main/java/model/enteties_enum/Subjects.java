package model.enteties_enum;

public enum Subjects {

    MATH("Математика", 120),
    UA_LANGUAGE("Українська мова", 80),
    ENG_LANGUAGE("Англійська мова", 80);

    private String name;
    private int duration;

    Subjects(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
