package model.enteties_enum;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public enum Users {
    ANDRIY("Бойко", "Андрій", "Петрович", "1998-02-12", "Київ", "andriy@gmail.com", "123"),
    KOSTYA("Гринчук", "Костянтин", "Вікторович", "1997-02-12", "Львів", "kost@gmail.com", "333"),
    MUHAYLO("Левченко", "Михайло", "Олегович", "1987-02-12", "Ніжин", "mych@gmail.com", "9876");

    private String lastName;
    private String firstName;
    private  String patronymic;
    private String birthday;
    private String city;
    private String email;
    private String password;

    Users(String lastName, String firstName, String patronymic,
          String birthday, String city, String email, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.city = city;
        this.email = email;
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
