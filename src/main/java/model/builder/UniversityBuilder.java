package model.builder;

import model.enteties.University;

public class UniversityBuilder {
    private String name;
    private String address;
    private String city;

    public UniversityBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UniversityBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public UniversityBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public University createUniversity() {
        return new University(name, address, city);
    }
}