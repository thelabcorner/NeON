package dev.xhue.neon.Utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.Bukkit;

import java.util.*;

import dev.xhue.neon.NeON;
import dev.xhue.neon.Listeners.FireworkDamageListener;

public class FireworkUtil {

    public static final NamespacedKey fireworkKey = new NamespacedKey("neon", "firework_key");

    // Add a record to the explosion map when spawning
    public static void spawnFirework(Location location, List<Color> colors, List<Color> fadeColors, Type type, boolean flicker, boolean trail, int power) {
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .with(type)
                .withColor(colors)
                .withFade(fadeColors)
                .flicker(flicker)
                .trail(trail)
                .build();

        meta.addEffect(effect);
        meta.setPower(power);

        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(fireworkKey, PersistentDataType.STRING, "true");

        firework.setFireworkMeta(meta);

        // Add to explosion record map immediately
        // Use FireworkDamageListener.recentExplosions instead of local map
        UUID recordId = UUID.randomUUID();
        FireworkDamageListener.recentExplosions.put(recordId, new FireworkDamageListener.ExplosionRecord("true", firework.getLocation().clone()));
        Bukkit.getScheduler().runTaskLater(NeON.getPlugin(), () -> FireworkDamageListener.recentExplosions.remove(recordId), 60L);
    }

    // Overload for random firework
    public static void spawnRandomFirework(Location location) {
        Random random = new Random();

        // Determine random number of colors (1 to 20)
        int numColors = random.nextInt(20) + 1;
        List<Color> colors = new ArrayList<>();
        for (int i = 0; i < numColors; i++) {
            colors.add(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        }

        // Determine random number of fade colors (1 to 20)
        int numFadeColors = random.nextInt(20) + 1;
        List<Color> fadeColors = new ArrayList<>();
        for (int i = 0; i < numFadeColors; i++) {
            fadeColors.add(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        }

        Type type = Type.values()[random.nextInt(Type.values().length)];
        boolean flicker = random.nextBoolean();
        boolean trail = random.nextBoolean();
        int power = random.nextInt(1) + 1; // Power 1 to 3

        spawnFirework(location, colors, fadeColors, type, flicker, trail, power);
    }

    public static Color hexToColor(String hex) {
        hex = hex.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return Color.fromRGB(r, g, b);
    }

}
