package controller.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

public class ComparatorDescend implements Comparator<BigDecimal> {

    @Override
    public int compare(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        return bigDecimal2.compareTo(bigDecimal1);
    }
}