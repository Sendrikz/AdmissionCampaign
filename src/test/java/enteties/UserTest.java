package enteties;

import model.builder.UserBuilder;
import model.enteties_enum.Users;
import model.enteties.User;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new UserBuilder().setLastName(Users.KOSTYA.getLastName()).setFirstName(Users.KOSTYA.getFirstName()).setPatronymic(Users.KOSTYA.getPatronymic()).setBirthday(Users.KOSTYA.getBirthday()).setCity(Users.KOSTYA.getCity()).setEmail(Users.KOSTYA.getEmail()).setPassword(Users.KOSTYA.getPassword()).setRole(2).createUser();
    }

    @Test
    public void setLastNameTest() {
        user.setLastName(Users.MUHAYLO.getLastName());
        assertEquals(Users.MUHAYLO.getLastName(), user.getLastName());
    }

    @Test
    public void setFirstNameTest() {
        user.setFirstName(Users.MUHAYLO.getFirstName());
        assertEquals(Users.MUHAYLO.getFirstName(), user.getFirstName());
    }

    @Test
    public void setPatronymicTest() {
        user.setPatronymic(Users.MUHAYLO.getPatronymic());
        assertEquals(Users.MUHAYLO.getPatronymic(), user.getPatronymic());
    }

    @Test
    public void setBirthdayTest() {
        user.setBirthday(Users.MUHAYLO.getBirthday());
        assertEquals(Users.MUHAYLO.getBirthday(), user.getBirthday());
    }

    @Test
    public void setCityTest() {
        user.setCity(Users.MUHAYLO.getCity());
        assertEquals(Users.MUHAYLO.getCity(), user.getCity());
    }

    @Test
    public void setRoleTest() {
        user.setRole(1);
        assertEquals(1, user.getRole());
    }

    @Test
    public void setIdTest() {
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void setEmailTest() {
        user.setEmail(Users.MUHAYLO.getEmail());
        assertEquals(Users.MUHAYLO.getEmail(), user.getEmail());
    }

    @Test
    public void setPasswordTest() {
        user.setPassword(Users.MUHAYLO.getPassword());
        assertEquals(Users.MUHAYLO.getPassword(), user.getPassword());
    }

    @Test
    public void equalsTest() {
        User userCopy = new UserBuilder().setLastName(Users.KOSTYA.getLastName()).setFirstName(Users.KOSTYA.getFirstName()).setPatronymic(Users.KOSTYA.getPatronymic()).setBirthday(Users.KOSTYA.getBirthday()).setCity(Users.KOSTYA.getCity()).setEmail(Users.KOSTYA.getEmail()).setPassword(Users.KOSTYA.getPassword()).setRole(2).createUser();
        assertEquals(true, user.equals(userCopy));
    }

    @Test
    public void hashCodeTest() {
        User userCopy = new UserBuilder().setLastName(Users.KOSTYA.getLastName()).setFirstName(Users.KOSTYA.getFirstName()).setPatronymic(Users.KOSTYA.getPatronymic()).setBirthday(Users.KOSTYA.getBirthday()).setCity(Users.KOSTYA.getCity()).setEmail(Users.KOSTYA.getEmail()).setPassword(Users.KOSTYA.getPassword()).setRole(2).createUser();
        assertEquals(user.hashCode(), userCopy.hashCode());
    }

    @Test
    public void notEqualsTest() {
        User userCopy = new UserBuilder().setLastName(Users.ANDRIY.getLastName()).setFirstName(Users.ANDRIY.getFirstName()).setPatronymic(Users.ANDRIY.getPatronymic()).setBirthday(Users.ANDRIY.getBirthday()).setCity(Users.ANDRIY.getCity()).setEmail(Users.ANDRIY.getEmail()).setPassword(Users.ANDRIY.getPassword()).setRole(1).createUser();
        assertEquals(false, user.equals(userCopy));
    }

    @Test
    public void getString() {
        assertEquals("User{" +
                "id=" + user.getId() +
                ", lastName='" + user.getLastName() + '\'' +
                ", firstName='" + user.getFirstName() + '\'' +
                ", patronymic='" + user.getPatronymic() + '\'' +
                ", birthday=" + user.getBirthday() +
                ", city='" + user.getCity() + '\'' +
                ", email='" + user.getEmail() + '\'' +
                ", password='" + user.getPassword() + '\'' +
                ", role=" + user.getRole() +
                '}', user.toString());
    }
}
