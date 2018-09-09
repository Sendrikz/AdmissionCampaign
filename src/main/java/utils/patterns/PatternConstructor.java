package utils.patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class PatternConstructor {

    /**
     * General construction for checking if data is validate
     * @param schema String
     * @param value String
     * @return Boolean
     */
    public Boolean checkWithPattern(String schema, String value) {
        Pattern pattern = Pattern.compile(schema);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
