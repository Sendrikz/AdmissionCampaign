package enteties;

import model.builder.FacultyBuilder;
import model.enteties.University;
import model.builder.UniversityBuilder;
import model.enteties_enum.Faculties;
import model.enteties.Faculty;
import model.enteties_enum.Universities;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class FacultyTest {

    private Faculty faculty;
    private University university;

    @Before
    public void setUp() {
        university = new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
        faculty = new FacultyBuilder().setName(Faculties.IT.getName()).setUniversityId(university.getId()).createFaculty();
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
        Faculty facultyTest = new FacultyBuilder().setName(Faculties.IT.getName()).setUniversityId(university.getId()).createFaculty();
        assertEquals(facultyTest.hashCode(), faculty.hashCode());
    }

    @Test
    public void equalsTest() {
        Faculty facultyTest = new FacultyBuilder().setName(Faculties.IT.getName()).setUniversityId(university.getId()).createFaculty();
        assertEquals(true, faculty.equals(facultyTest));
    }

    @Test
    public void notEqualsTest() {
        Faculty facultyTest = new FacultyBuilder().setName(Faculties.ECONOMIC.getName()).setUniversityId(university.getId()).createFaculty();
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
