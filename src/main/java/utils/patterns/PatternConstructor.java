package utils.patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternConstructor {

    public Boolean checkWithPattern(String schema, String value) {
        Pattern pattern = Pattern.compile(schema);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
