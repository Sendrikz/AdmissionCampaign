package enteties;

import model.builder.SubjectBuilder;
import model.enteties_enum.Subjects;
import model.enteties.Subject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class SubjectTest {

    private Subject subject;

    @Before
    public void setUp() {
        subject = new SubjectBuilder().setName(Subjects.MATH.getName()).setDuration(Subjects.MATH.getDuration()).createSubject();
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
        Subject subjectToTest = new SubjectBuilder().setName(Subjects.MATH.getName()).setDuration(Subjects.MATH.getDuration()).createSubject();
        assertEquals(true, subject.equals(subjectToTest));
    }

    @Test
    public void equalsTest() {
        Subject subjectToTest = new SubjectBuilder().setName(Subjects.UA_LANGUAGE.getName()).setDuration(Subjects.UA_LANGUAGE.getDuration()).createSubject();
        assertEquals(false, subject.equals(subjectToTest));
    }

    @Test
    public void hashCodeTest() {
        Subject subjectToTest = new SubjectBuilder().setName(Subjects.MATH.getName()).setDuration(Subjects.MATH.getDuration()).createSubject();
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
