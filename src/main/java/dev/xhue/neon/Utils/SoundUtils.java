package dev.xhue.neon.Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;


public class SoundUtils {
    @NotNull
    public static Sound getSoundOrThrow(@NotNull String key) {
        if (key.startsWith("minecraft:")) {
            key = key.substring("minecraft:".length());
        }
        try {
            // Try new API (1.21.4+)
            NamespacedKey nsk = NamespacedKey.minecraft(key.toLowerCase(Locale.ROOT));
            java.lang.reflect.Field field = Registry.class.getDeclaredField("SOUND_EVENT");
            Object registry = field.get(null);
            return (Sound) registry.getClass().getMethod("getOrThrow", NamespacedKey.class).invoke(registry, nsk);
        } catch (NoSuchFieldException e) {
            // Fallback for older versions: normalize and try Sound.valueOf via reflection
            String normalized = key.toUpperCase(Locale.ROOT).replace('.', '_');
            try {
                return (Sound) Sound.class.getMethod("valueOf", String.class)
                        .invoke(null, normalized);
            } catch (NoSuchMethodException ex) {
                throw new IllegalStateException("Sound.valueOf(String) is not available in this Bukkit version.", ex);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid sound key: " + key, ex);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid sound key: " + key, e);
        }
    }
}
