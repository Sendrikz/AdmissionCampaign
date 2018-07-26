package model.enteties;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private int id;
    private String lastName;
    private String firstName;
    private  String patronymic;
    private LocalDate birthday;
    private String city;
    private String email;
    private String password;
    private int role;

    public User(String lastName, String firstName, String patronymic,
                String birthday, String city, String email, String password, int role) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthday = LocalDate.parse(birthday);
        this.city = city;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday.toString();
    }

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) && patronymic.equals(user.patronymic) &&
                birthday.isEqual(user.birthday) && city.equals(user.city) &&
                email.equals(user.email) && password.equals(user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, patronymic,
                birthday, city, email, password, role);
    }

}
