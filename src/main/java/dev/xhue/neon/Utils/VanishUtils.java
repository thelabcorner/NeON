package dev.xhue.neon.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class VanishUtils {
    public static boolean isVanished(Player player) {
        if (!player.hasMetadata("vanished")) return false;
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) {
                return true;
            }
        }
        return false;
    }
}