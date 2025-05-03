package dev.xhue.neon.Utils;

public class PlaceholderUtils {

    public static String replacePlaceholder(String text, String placeholder, String value) {
        if (text == null || text.isEmpty() || placeholder == null || placeholder.isEmpty()) {
            return text;
        }
        return text.replace(placeholder, value);
    }

}