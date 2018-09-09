package enteties;

import model.builder.UniversityBuilder;
import model.enteties_enum.Universities;
import model.enteties.University;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class UniversityTest {

    private University uni;

    @Before
    public void setUp() {
        uni = new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
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
        University uniTest = new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
        assertEquals(uniTest.hashCode(), uni.hashCode());
    }

    @Test
    public void equalsTest() {
        University uniTest = new UniversityBuilder().setName(Universities.NaUKMA.getName()).setAddress(Universities.NaUKMA.getAddress()).setCity(Universities.NaUKMA.getCity()).createUniversity();
        assertEquals(true, uni.equals(uniTest));
    }

    @Test
    public void notEqualsTest() {
        University uniTest = new UniversityBuilder().setName(Universities.KPI.getName()).setAddress(Universities.KPI.getAddress()).setCity(Universities.KPI.getCity()).createUniversity();
        assertEquals(false, uni.equals(uniTest));
    }

    @Test
    public void getString() {
        assertEquals("University{" +
                "id=" + uni.getId() +
                ", name='" + uni.getName() + '\'' +
                ", address='" + uni.getAddress() + '\'' +
                ", city='" + uni.getCity() + '\'' +
                '}', uni.toString());
    }
}
