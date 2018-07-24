import model.enteties.Subject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SubjectTest {

    Subject subject = new Subject("Math", 120);

    @Test
    public void setNameTest() {
        subject.setName("Physic");
        assertEquals("Physic", subject.getName());
    }

    @Test
    public void setDurationTest() {
        subject.setDuration(140);
        assertEquals(140, subject.getDuration());
    }

    @Test
    public void setIdTest() {
        subject.setId(1);
        assertEquals(1, subject.getId());
    }

    @Test
    public void notEqualsTest() {
        Subject subjectToTest = new Subject("Math", 120);
        assertEquals(true, subject.equals(subjectToTest));
    }

    @Test
    public void equalsTest() {
        Subject subjectToTest = new Subject("Physic", 140);
        assertEquals(false, subject.equals(subjectToTest));
    }

    @Test
    public void hashCodeTest() {
        Subject subjectToTest = new Subject("Math", 120);
        assertEquals(subjectToTest.hashCode(), subject.hashCode());
    }
}
