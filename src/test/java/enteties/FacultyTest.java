package enteties;

import model.enteties.University;
import model.enteties_enum.Faculties;
import model.enteties.Faculty;
import model.enteties_enum.Universities;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FacultyTest {

    private Faculty faculty;
    private University university;

    @Before
    public void setUp() {
        university = new University(Universities.NaUKMA.getName(),
                Universities.NaUKMA.getAddress(), Universities.NaUKMA.getCity());
        faculty = new Faculty(Faculties.IT.getName(), university.getId());
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
        Faculty facultyTest = new Faculty(Faculties.IT.getName(), university.getId());
        assertEquals(facultyTest.hashCode(), faculty.hashCode());
    }

    @Test
    public void equalsTest() {
        Faculty facultyTest = new Faculty(Faculties.IT.getName(), university.getId());
        assertEquals(true, faculty.equals(facultyTest));
    }

    @Test
    public void notEqualsTest() {
        Faculty facultyTest = new Faculty(Faculties.ECONOMIC.getName(), university.getId());
        assertEquals(false, faculty.equals(facultyTest));
    }

    @Test
    public void getString() {
        assertEquals("Faculty{" +
                "id=" + faculty.getId() +
                ", name='" + faculty.getName() + '\'' +
                ", university=" + faculty.getUniversityId() +
                '}', faculty.toString());
    }
}
