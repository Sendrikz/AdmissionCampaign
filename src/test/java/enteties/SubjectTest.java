package enteties;

import enums.Subjects;
import model.enteties.Subject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SubjectTest {

    private Subject subject;

    @Before
    public void setUp() {
        subject = new Subject(Subjects.MATH.getName(), Subjects.MATH.getDuration());
    }

    @Test
    public void setNameTest() {
        subject.setName(Subjects.UA_LANGUAGE.getName());
        assertEquals(Subjects.UA_LANGUAGE.getName(), subject.getName());
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
        Subject subjectToTest = new Subject(Subjects.MATH.getName(), Subjects.MATH.getDuration());
        assertEquals(true, subject.equals(subjectToTest));
    }

    @Test
    public void equalsTest() {
        Subject subjectToTest = new Subject(Subjects.UA_LANGUAGE.getName(),
                Subjects.UA_LANGUAGE.getDuration());
        assertEquals(false, subject.equals(subjectToTest));
    }

    @Test
    public void hashCodeTest() {
        Subject subjectToTest = new Subject(Subjects.MATH.getName(), Subjects.MATH.getDuration());
        assertEquals(subjectToTest.hashCode(), subject.hashCode());
    }

    @Test
    public void getString() {
        assertEquals("Subject{" +
                "id=" + subject.getId() +
                ", name='" + subject.getName() + '\'' +
                ", duration=" + subject.getDuration() +
                '}', subject.toString());
    }
}
