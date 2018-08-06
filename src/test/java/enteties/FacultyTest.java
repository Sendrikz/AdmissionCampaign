package enteties;

import model.enums.Faculties;
import model.enteties.Faculty;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FacultyTest {

    private Faculty faculty;

    @Before
    public void setUp() {
        faculty = new Faculty(Faculties.IT.getName());
    }

    @Test
    public void setIdTest() {
        faculty.setId(1);
        assertEquals(1, faculty.getId());
    }

    @Test
    public void setNameTest() {
        faculty.setName(Faculties.ECONOMIC.getName());
        assertEquals("Факультет економічних наук", faculty.getName());
    }

    @Test
    public void hashCodeTest() {
        Faculty facultyTest = new Faculty(Faculties.IT.getName());
        assertEquals(facultyTest.hashCode(), faculty.hashCode());
    }

    @Test
    public void equalsTest() {
        Faculty facultyTest = new Faculty(Faculties.IT.getName());
        assertEquals(true, faculty.equals(facultyTest));
    }

    @Test
    public void notEqualsTest() {
        Faculty facultyTest = new Faculty(Faculties.ECONOMIC.getName());
        assertEquals(false, faculty.equals(facultyTest));
    }

    @Test
    public void getString() {
        assertEquals("Faculty{" +
                "id=" + faculty.getId() +
                ", name='" + faculty.getName() + '\'' +
                '}', faculty.toString());
    }
}
