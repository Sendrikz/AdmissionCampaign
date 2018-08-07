package enteties;

import model.enteties_enum.Universities;
import model.enteties.University;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UniversityTest {

    private University uni;

    @Before
    public void setUp() {
        uni = new University(Universities.NaUKMA.getName(), Universities.NaUKMA.getAddress());
    }

    @Test
    public void setIdTest() {
        uni.setId(1);
        assertEquals(1, uni.getId());
    }

    @Test
    public void setNameTest() {
        uni.setName(Universities.KPI.getName());
        assertEquals(Universities.KPI.getName(), uni.getName());
    }

    @Test
    public void setAdressTest() {
        uni.setAddress(Universities.KPI.getAddress());
        assertEquals(Universities.KPI.getAddress(), uni.getAddress());
    }

    @Test
    public void hashCodeTest() {
        University uniTest = new University(Universities.NaUKMA.getName(),
                Universities.NaUKMA.getAddress());
        assertEquals(uniTest.hashCode(), uni.hashCode());
    }

    @Test
    public void equalsTest() {
        University uniTest = new University(Universities.NaUKMA.getName(),
                Universities.NaUKMA.getAddress());
        assertEquals(true, uni.equals(uniTest));
    }

    @Test
    public void notEqualsTest() {
        University uniTest = new University(Universities.KPI.getName(),
                Universities.KPI.getAddress());
        assertEquals(false, uni.equals(uniTest));
    }

    @Test
    public void getString() {
        assertEquals("University{" +
                "id=" + uni.getId() +
                ", name='" + uni.getName() + '\'' +
                ", address='" + uni.getAddress() + '\'' +
                '}', uni.toString());
    }
}
