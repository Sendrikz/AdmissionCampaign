package enteties;

import enums.Roles;
import model.enteties.Role;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoleTest {

    private Role role;

    @Before
    public void setUp() {
        role = new Role(Roles.ADMINISTRATOR.getName());
    }

    @Test
    public void setIdTest() {
        role.setId(1);
        assertEquals(1, role.getId());
    }

    @Test
    public void setNameTest() {
        role.setName(Roles.STUDENT.getName());
        assertEquals(Roles.STUDENT.getName(), role.getName());
    }

    @Test
    public void hashCodeTest() {
        Role roleTest = new Role(Roles.ADMINISTRATOR.getName());
        assertEquals(roleTest.hashCode(), role.hashCode());
    }

    @Test
    public void equalsTest() {
        Role roleTest = new Role(Roles.ADMINISTRATOR.getName());
        assertEquals(true, role.equals(roleTest));
    }

    @Test
    public void notEqualsTest() {
        Role roleTest = new Role(Roles.STUDENT.getName());
        assertEquals(false, role.equals(roleTest));
    }

    @Test
    public void getString() {
        assertEquals("Role{" +
                "id=" + role.getId() +
                ", name='" + role.getName() + '\'' +
                '}', role.toString());
    }
}
