
import model.enteties.Specialty;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SpecialtyTest {

    Specialty specialty = new Specialty("Computer science", 60, 1);

    @Test
    public void setIdTest() {
        specialty.setId(1);
        assertEquals(1, specialty.getId());
    }

    @Test
    public void setNameTest() {
        specialty.setName("Computer engineering");
        assertEquals("Computer engineering", specialty.getName());
    }

    @Test
    public void setQuantityOfStudentsTest() {
        specialty.setQuantityOfStudents(50);
        assertEquals(50, specialty.getQuantityOfStudents());
    }

    @Test
    public void setFacultyIdTest() {
        specialty.setFacultyId(2);
        assertEquals(2, specialty.getFacultyId());
    }

    @Test
    public void hashCodeTest() {
        Specialty specialtyTest = new Specialty("Computer science", 60, 1);
        assertEquals(specialtyTest.hashCode(), specialty.hashCode());
    }

    @Test
    public void equalsTest() {
        Specialty specialtyTest = new Specialty("Computer science", 60, 1);
        assertEquals(true, specialty.equals(specialtyTest));
    }

    @Test
    public void notEqualsTest() {
        Specialty specialtyTest = new Specialty("Computer engineering", 60, 1);
        assertEquals(false, specialty.equals(specialtyTest));
    }

}
