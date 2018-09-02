package model.builder;

import model.enteties.User;

public class UserBuilder {
    private String lastName;
    private String firstName;
    private String patronymic;
    private String birthday;
    private String city;
    private String email;
    private String password;
    private int role;

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public UserBuilder setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public UserBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRole(int role) {
        this.role = role;
        return this;
    }

    public User createUser() {
        return new User(lastName, firstName, patronymic, birthday, city, email, password, role);
    }
}