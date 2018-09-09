package enteties;

import model.builder.RoleBuilder;
import model.enteties_enum.Roles;
import model.enteties.Role;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class RoleTest {

    private Role role;

    @Before
    public void setUp() {
        role = new RoleBuilder().setName(Roles.ADMINISTRATOR.getName()).createRole();
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
        Role roleTest = new RoleBuilder().setName(Roles.ADMINISTRATOR.getName()).createRole();
        assertEquals(roleTest.hashCode(), role.hashCode());
    }

    @Test
    public void equalsTest() {
        Role roleTest = new RoleBuilder().setName(Roles.ADMINISTRATOR.getName()).createRole();
        assertEquals(true, role.equals(roleTest));
    }

    @Test
    public void notEqualsTest() {
        Role roleTest = new RoleBuilder().setName(Roles.STUDENT.getName()).createRole();
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
