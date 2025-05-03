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
        NamespacedKey nsk = NamespacedKey.minecraft(key.toLowerCase(Locale.ROOT));
        // Throws NoSuchElementException if absent
        return Registry.SOUND_EVENT.getOrThrow(nsk);
    }
}
