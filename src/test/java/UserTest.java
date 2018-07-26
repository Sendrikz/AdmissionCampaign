import model.enteties.User;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserTest {

  User user = new User("Petrenko", "Petro", "Petrovych",
          "1998-02-12", "Kyiv", "petr@gmail.com", "123", 2);

    @Test
    public void getLastNameTest() {
        assertEquals("Petrenko", user.getLastName());
    }

    @Test
    public void getFirstNameTest() {
        assertEquals("Petro", user.getFirstName());
    }

    @Test
    public void getPatronymicTest() {
        assertEquals("Petrovych", user.getPatronymic());
    }

    @Test
    public void getBirthdayTest() {
        assertEquals("1998-02-12", user.getBirthday());
    }

    @Test
    public void getCityTest() {
        assertEquals("Kyiv", user.getCity());
    }

    @Test
    public void getRoleTest() {
        assertEquals(2, user.getRole());
    }

    @Test
    public void setLastNameTest() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void setFirstNameTest() {
        user.setFirstName("Eric");
        assertEquals("Eric", user.getFirstName());
    }

    @Test
    public void setPatronymicTest() {
        user.setPatronymic("Rouse");
        assertEquals("Rouse", user.getPatronymic());
    }

    @Test
    public void setBirthdayTest() {
        user.setBirthday("1993-03-16");
        assertEquals("1993-03-16", user.getBirthday());
    }

    @Test
    public void setCityTest() {
        user.setCity("Lviv");
        assertEquals("Lviv", user.getCity());
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
        user.setEmail("petrovych@gmail.com");
        assertEquals("petrovych@gmail.com", user.getEmail());
    }

    @Test
    public void setPasswordTest() {
        user.setPassword("321");
        assertEquals("321", user.getPassword());
    }

    @Test
    public void equalsTest() {
        User userCopy = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", 2);
        assertEquals(true, user.equals(userCopy));
    }

    @Test
    public void hashCodeTest() {
        User userCopy = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", 2);
        assertEquals(user.hashCode(), userCopy.hashCode());
    }

    @Test
    public void notEqualsTest() {
        User userCopy = new User("Petrenko", "Petro", "Petrovych",
                "1998-02-12", "Kyiv", "petr@gmail.com", "123", 1);
        assertEquals(false, user.equals(userCopy));
    }

}
