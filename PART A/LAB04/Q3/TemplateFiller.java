import java.util.regex.*;
public class TemplateFiller {
    static String fill(String template, String[] names, String[] values) {
        Matcher m = Pattern.compile("\\{(\\w+)\\}").matcher(template);
        StringBuilder result = new StringBuilder();
        while (m.find()) {
            String value = "[?]";
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(m.group(1))) { value = values[i]; break; }
            }
            m.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        m.appendTail(result);
        return result.toString();
    }
}
