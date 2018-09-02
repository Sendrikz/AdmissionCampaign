package enteties;

import model.builder.SpecialtyBuilder;
import model.enteties_enum.Specialties;
import model.enteties.Specialty;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SpecialtyTest {

    private Specialty specialty;

    @Before
    public void setUp() {
        specialty = new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName()).setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents()).setFacultyId(1).createSpecialty();
    }

    @Test
    public void setIdTest() {
        specialty.setId(1);
        assertEquals(1, specialty.getId());
    }

    @Test
    public void setNameTest() {
        specialty.setName(Specialties.ENGINEERING.getName());
        assertEquals(Specialties.ENGINEERING.getName(), specialty.getName());
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
        Specialty specialtyTest = new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName()).setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents()).setFacultyId(1).createSpecialty();
        assertEquals(specialtyTest.hashCode(), specialty.hashCode());
    }

    @Test
    public void equalsTest() {
        Specialty specialtyTest = new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName()).setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents()).setFacultyId(1).createSpecialty();
        assertEquals(true, specialty.equals(specialtyTest));
    }

    @Test
    public void notEqualsTest() {
        Specialty specialtyTest = new SpecialtyBuilder().setName(Specialties.ENGINEERING.getName()).setQuantityOfStudents(Specialties.ENGINEERING.getQuantityOfStudents()).setFacultyId(1).createSpecialty();
        assertEquals(false, specialty.equals(specialtyTest));
    }

    @Test
    public void getString() {
        assertEquals("Specialty{" +
                "id=" + specialty.getId() +
                ", name='" + specialty.getName() + '\'' +
                ", quantityOfStudents=" + specialty.getQuantityOfStudents() +
                ", facultyId=" + specialty.getFacultyId() +
                '}', specialty.toString());
    }

}
