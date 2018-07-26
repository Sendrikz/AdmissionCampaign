package enteties;

import model.enteties.Faculty;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FacultyTest {

    Faculty faculty = new Faculty("Information Technologies");

    @Test
    public void setIdTest() {
        faculty.setId(1);
        assertEquals(1, faculty.getId());
    }

    @Test
    public void setNameTest() {
        faculty.setName("Economic");
        assertEquals("Economic", faculty.getName());
    }

    @Test
    public void hashCodeTest() {
        Faculty facultyTest = new Faculty("Information Technologies");
        assertEquals(facultyTest.hashCode(), faculty.hashCode());
    }

    @Test
    public void equalsTest() {
        Faculty facultyTest = new Faculty("Information Technologies");
        assertEquals(true, faculty.equals(facultyTest));
    }

    @Test
    public void notEqualsTest() {
        Faculty facultyTest = new Faculty("Economic");
        assertEquals(false, faculty.equals(facultyTest));
    }
}
