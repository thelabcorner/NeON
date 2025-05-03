package dev.xhue.neon.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SerializerUtil {

    private static final Map<Character, String> LEGACY_TO_MINIMESSAGE_MAP = Map.ofEntries(
            Map.entry('0', "<black>"),       Map.entry('1', "<dark_blue>"),
            Map.entry('2', "<dark_green>"),   Map.entry('3', "<dark_aqua>"),
            Map.entry('4', "<dark_red>"),     Map.entry('5', "<dark_purple>"),
            Map.entry('6', "<gold>"),         Map.entry('7', "<gray>"),
            Map.entry('8', "<dark_gray>"),    Map.entry('9', "<blue>"),
            Map.entry('a', "<green>"),        Map.entry('b', "<aqua>"),
            Map.entry('c', "<red>"),          Map.entry('d', "<light_purple>"),
            Map.entry('e', "<yellow>"),       Map.entry('f', "<white>"),
            Map.entry('k', "<obf>"),          Map.entry('l', "<b>"),
            Map.entry('m', "<st>"),           Map.entry('n', "<u>"),
            Map.entry('o', "<i>"),            Map.entry('r', "<reset>")
    );

    private static final Map<String, Character> MINIMESSAGE_TO_LEGACY_MAP =
            LEGACY_TO_MINIMESSAGE_MAP.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    private static final Pattern LEGACY_SPIGOT_HEX_PATTERN =
            Pattern.compile("[&§]x([&§][0-9a-fA-F]){6}", Pattern.CASE_INSENSITIVE);
    private static final Pattern MINIMESSAGE_HEX_PATTERN =
            Pattern.compile("<(?:color:)?#([0-9a-fA-F]{6})>", Pattern.CASE_INSENSITIVE);
    private static final Pattern MINIMESSAGE_TAG_PATTERN =
            Pattern.compile("<(/?[a-zA-Z_]+)>");
    private static final Pattern LEGACY_PATTERN =
            Pattern.compile("([&§])([0-9a-fk-orA-FK-OR])");

    public static String legacyToMiniMessage(final String input) {
        if (input == null) return null;

        // 1) Process hex codes
        StringBuilder hexProcessed = new StringBuilder();
        Matcher hexMatcher = LEGACY_SPIGOT_HEX_PATTERN.matcher(input);
        while (hexMatcher.find()) {
            String match = hexMatcher.group(0);
            String hex = match.substring(2).replace("&", "").replace("§", "");
            hexMatcher.appendReplacement(hexProcessed, "<color:#" + hex + ">");
        }
        hexMatcher.appendTail(hexProcessed);

        // 2) Process standard legacy codes
        StringBuilder out = new StringBuilder();
        Matcher m = LEGACY_PATTERN.matcher(hexProcessed.toString());
        while (m.find()) {
            char code = Character.toLowerCase(m.group(2).charAt(0));
            String replacement = LEGACY_TO_MINIMESSAGE_MAP.getOrDefault(code, "");
            m.appendReplacement(out, Matcher.quoteReplacement(replacement));
        }
        m.appendTail(out);
        return out.toString();
    }

    public static String miniMessageToLegacy(final String input) {
        if (input == null) return null;

        // 1) Process hex codes
        StringBuilder out = new StringBuilder();
        Matcher hexMatcher = MINIMESSAGE_HEX_PATTERN.matcher(input);
        while (hexMatcher.find()) {
            String hex = hexMatcher.group(1).toLowerCase();
            StringBuilder legacyHex = new StringBuilder("&x");
            for (char c : hex.toCharArray()) {
                legacyHex.append('&').append(c);
            }
            hexMatcher.appendReplacement(out, Matcher.quoteReplacement(legacyHex.toString()));
        }
        hexMatcher.appendTail(out);

        // 2) Process MiniMessage tags
        String processed = out.toString();
        out = new StringBuilder();
        Matcher tagMatcher = MINIMESSAGE_TAG_PATTERN.matcher(processed);
        while (tagMatcher.find()) {
            String fullTag = tagMatcher.group(0).toLowerCase();
            String tagName = tagMatcher.group(1).toLowerCase();
            Character legacyCode = MINIMESSAGE_TO_LEGACY_MAP.get(fullTag);
            String replacement;
            if (legacyCode != null) {
                replacement = "&" + legacyCode;
            } else if (tagName.startsWith("/")) {
                replacement = "&r";
            } else {
                replacement = "";
            }
            tagMatcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
        }
        tagMatcher.appendTail(out);

        return out.toString();
    }


    // legacy → MM tags, then parse
    public static Component legacyToMiniMessageComponent(final String input) {
        return MiniMessage.miniMessage().deserialize(legacyToMiniMessage(input));
    }

}
