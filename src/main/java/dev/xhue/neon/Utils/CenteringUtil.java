package dev.xhue.neon.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class CenteringUtil {
    // Approximate center point in pixels for a single chat line
    private static final int CENTER_PX = 154;
    private static final Map<Character, Integer> CHAR_WIDTHS = new HashMap<>();
    private static final int DEFAULT_WIDTH = 5;

    static {
        // Default width for common chars
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                .chars().forEach(c -> CHAR_WIDTHS.put((char) c, DEFAULT_WIDTH));

        // Explicit widths for special chars (Clearer & Safer)
        CHAR_WIDTHS.put(' ', 4); CHAR_WIDTHS.put('!', 1); CHAR_WIDTHS.put('"', 3);
        CHAR_WIDTHS.put('#', 5); CHAR_WIDTHS.put('$', 5); CHAR_WIDTHS.put('%', 5);
        CHAR_WIDTHS.put('&', 5); CHAR_WIDTHS.put('\'', 1); CHAR_WIDTHS.put('(', 4);
        CHAR_WIDTHS.put(')', 4); CHAR_WIDTHS.put('*', 5); CHAR_WIDTHS.put('+', 5);
        CHAR_WIDTHS.put(',', 1); CHAR_WIDTHS.put('-', 5); CHAR_WIDTHS.put('.', 1);
        CHAR_WIDTHS.put('/', 5); CHAR_WIDTHS.put(':', 1); CHAR_WIDTHS.put(';', 1);
        CHAR_WIDTHS.put('<', 4); CHAR_WIDTHS.put('=', 5); CHAR_WIDTHS.put('>', 4);
        CHAR_WIDTHS.put('?', 5); CHAR_WIDTHS.put('[', 3); CHAR_WIDTHS.put('\\', 5);
        CHAR_WIDTHS.put(']', 3); CHAR_WIDTHS.put('^', 5); CHAR_WIDTHS.put('_', 5);
        CHAR_WIDTHS.put('`', 2); CHAR_WIDTHS.put('{', 3); CHAR_WIDTHS.put('|', 1);
        CHAR_WIDTHS.put('}', 3); CHAR_WIDTHS.put('~', 5);
    }


    private static final Pattern LEGACY_PATTERN = Pattern.compile("([&§])([0-9a-fk-orA-FK-OR])");
    private static final Pattern LEGACY_HEX = Pattern.compile("([&§])x((?:[&§][0-9a-fA-F]){6})");

    public static String getCenteredMessage(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        // 1) Convert any MiniMessage tags into legacy codes
        String legacy = SerializerUtil.miniMessageToLegacy(message);

        // 2) Create a clean version for width calculation by removing formatting
        String plain = stripFormattingForWidthCalculation(legacy);

        // 3) Measure pixel width of the clean text
        int msgPx = 0;
        for (char c : plain.toCharArray()) {
            msgPx += CHAR_WIDTHS.getOrDefault(c, DEFAULT_WIDTH);
        }

        // 4) Compute how many space chars we need to prefix
        int halfMsg = msgPx / 2;
        int spacesPx = CENTER_PX - halfMsg;
        int spaceCount = spacesPx > 0 ? spacesPx / CHAR_WIDTHS.get(' ') : 0;

        // 5) Prepend spaces to the original message (with all formatting intact)
        return " ".repeat(spaceCount) + message;
    }

    private static String stripFormattingForWidthCalculation(String text) {
        StringBuilder result = new StringBuilder(text.length());
        int tagDepth = 0;
        boolean inEscape = false;

        for (int i = 0, len = text.length(); i < len; i++) {
            char c = text.charAt(i);

            // Handle legacy color codes (&a, §b, etc.)
            if ((c == '&' || c == '§') && i + 1 < len) {
                char next = text.charAt(i + 1);
                if ("0123456789abcdefklmnorABCDEFKLMNOR".indexOf(next) >= 0) {
                    i++;
                    continue;
                }
                // Legacy hex color code (&x&F&F&F&F&F&F)
                if (next == 'x' && i + 13 < len) {
                    boolean isHex = true;
                    for (int j = 0; j < 6; j++) {
                        if ((text.charAt(i + 2 + j * 2) != '&' && text.charAt(i + 2 + j * 2) != '§') ||
                            "0123456789abcdefABCDEF".indexOf(text.charAt(i + 3 + j * 2)) < 0) {
                            isHex = false;
                            break;
                        }
                    }
                    if (isHex) {
                        i += 13;
                        continue;
                    }
                }
            }

            // Handle escapes
            if (inEscape) {
                if (tagDepth == 0) result.append(c);
                inEscape = false;
                continue;
            }
            if (c == '\\') {
                inEscape = true;
                continue;
            }

            // Handle MiniMessage-style tags
            if (c == '<') {
                tagDepth++;
                continue;
            }
            if (c == '>' && tagDepth > 0) {
                tagDepth--;
                continue;
            }
            if (tagDepth == 0) {
                result.append(c);
            }
        }
        return result.toString();
    }
}