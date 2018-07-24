import model.enteties.University;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UniversityTest {

    University uni = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");

    @Test
    public void setIdTest() {
        uni.setId(1);
        assertEquals(1, uni.getId());
    }

    @Test
    public void setNameTest() {
        uni.setName("KPI");
        assertEquals("KPI", uni.getName());
    }

    @Test
    public void setAdressTest() {
        uni.setAdress("Peremogu, 37");
        assertEquals("Peremogu, 37", uni.getAdress());
    }

    @Test
    public void hashCodeTest() {
        University uniTest = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        assertEquals(uniTest.hashCode(), uni.hashCode());
    }

    @Test
    public void equalsTest() {
        University uniTest = new University("Kyiv-Mohyla Academy", "Hryhoria Skovorodu, 2");
        assertEquals(true, uni.equals(uniTest));
    }

    @Test
    public void notEqualsTest() {
        University uniTest = new University("KPI", "Peremogu, 37");
        assertEquals(false, uni.equals(uniTest));
    }
}
