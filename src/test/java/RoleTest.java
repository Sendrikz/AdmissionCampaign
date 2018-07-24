import model.enteties.Role;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoleTest {

    Role role = new Role("Адміністратор");

    @Test
    public void setIdTest() {
        role.setId(1);
        assertEquals(1, role.getId());
    }

    @Test
    public void setNameTest() {
        role.setName("Student");
        assertEquals("Student", role.getName());
    }

    @Test
    public void hashCodeTest() {
        Role roleTest = new Role("Адміністратор");
        assertEquals(roleTest.hashCode(), role.hashCode());
    }

    @Test
    public void equalsTest() {
        Role roleTest = new Role("Адміністратор");
        assertEquals(true, role.equals(roleTest));
    }

    @Test
    public void notEqualsTest() {
        Role roleTest = new Role("Student");
        assertEquals(false, role.equals(roleTest));
    }
}
