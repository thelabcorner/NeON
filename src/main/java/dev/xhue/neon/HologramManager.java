package dev.xhue.neon;

import dev.xhue.neon.Utils.SerializerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HologramManager {

    private static final double LINE_SPACING = 0.25;
    private static final Map<UUID, HologramEntry> activeHolograms = new ConcurrentHashMap<>();
    private static final Map<UUID, HologramEntry> trackingHolograms = new ConcurrentHashMap<>();
    private static boolean taskStarted = false;
    private static boolean trackingTaskStarted = false;

    private static final NamespacedKey HOLOGRAM_KEY = new NamespacedKey(NeON.getPlugin(), "neon_hologram");

    public static List<TextDisplay> spawnHologram(Player player, List<String> lines, float minPitch, float maxPitch, long durationTicks, JavaPlugin plugin, double distance) {
        return spawnStandardHologram(player, lines, durationTicks, plugin, minPitch, maxPitch, distance, false, 0, 0);
    }

    public static List<TextDisplay> spawnBouncingHologram(Player player, List<String> lines, float minPitch, float maxPitch, long durationTicks, JavaPlugin plugin, double distance, double amplitude, int frequencyTicks) {
        return spawnStandardHologram(player, lines, durationTicks, plugin, minPitch, maxPitch, distance, true, amplitude, frequencyTicks);
    }

    private static List<TextDisplay> spawnStandardHologram(Player player, List<String> lines, long durationTicks, JavaPlugin plugin, float minPitch, float maxPitch,  double distance, boolean bouncing, double amplitude, int frequencyTicks) {
        Location spawnLocation = getHologramBaseLocation(player, distance, lines.size(), minPitch, maxPitch);
        List<TextDisplay> displays = createTextDisplays(lines, spawnLocation);
        UUID id = UUID.randomUUID();
        long spawnTime = System.currentTimeMillis();
        activeHolograms.put(id, new HologramEntry(displays, spawnTime, durationTicks));
        startAutoRemovalTask(plugin);

        if (bouncing) {
            startBouncingTask(player, displays, id, spawnTime, durationTicks, spawnLocation, amplitude, frequencyTicks, plugin);
        }

        return displays;
    }

    public static List<TextDisplay> spawnTrackingHologram(Player player, List<String> lines, float minPitch, float maxPitch, long durationTicks, JavaPlugin plugin, double distance, boolean shouldThrob) {
        removeTrackingHologram(player);
        List<TextDisplay> displays = createTextDisplays(lines, player.getLocation());
        trackingHolograms.put(player.getUniqueId(), new HologramEntry(displays, System.currentTimeMillis(), durationTicks));
        startTrackingTask(plugin, distance, lines.size(), minPitch, maxPitch, shouldThrob);
        return displays;
    }

    public static void removeTrackingHologram(Player player) {
        removeHologramByEntry(trackingHolograms.remove(player.getUniqueId()));
    }

    public static void removeAllTrackingHolograms() {
        trackingHolograms.values().forEach(HologramManager::removeHologramByEntry);
        trackingHolograms.clear();
    }

    public static void removeHologram(List<TextDisplay> displays) {
        displays.forEach(Entity::remove);
        activeHolograms.values().removeIf(entry -> entry.displays.equals(displays));
    }

    public static void removeAllHolograms() {
        activeHolograms.values().forEach(HologramManager::removeHologramByEntry);
        activeHolograms.clear();
    }

    public static void hologramCleanup() {
        for (World world : Bukkit.getWorlds()) {
            for (TextDisplay display : world.getEntitiesByClass(TextDisplay.class)) {
                if (display.getPersistentDataContainer().has(HOLOGRAM_KEY, PersistentDataType.BOOLEAN)) {
                    display.remove();
                }
            }
        }
        activeHolograms.clear();
        trackingHolograms.clear();
    }

    private static void startAutoRemovalTask(JavaPlugin plugin) {
        if (taskStarted) return;
        taskStarted = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                activeHolograms.entrySet().removeIf(entry -> {
                    HologramEntry hologram = entry.getValue();
                    if (hologram.durationTicks > 0 && now - hologram.spawnTime >= hologram.durationTicks * 50) {
                        removeHologramByEntry(hologram);
                        return true;
                    }
                    return false;
                });

                if (activeHolograms.isEmpty()) {
                    taskStarted = false;
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }



    private static void startTrackingTask(JavaPlugin plugin, double distance, int linesCount, float minPitch, float maxPitch, boolean throbbing) {
        if (trackingTaskStarted) return;
        trackingTaskStarted = true;
        final double lerpFactor = 0.6;
        final double amplitude = 0.15; // How much the distance changes
        final int periodTicks = 20;   // How fast the throbbing is (2 seconds)

        new BukkitRunnable() {
            int tick = 0;
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                double modulatedDistance = distance;
                if (throbbing) {
                    modulatedDistance += Math.sin((tick / (double) periodTicks) * 1.25 * Math.PI) * amplitude;
                }
                double finalModulatedDistance = modulatedDistance;
                trackingHolograms.entrySet().removeIf(entry -> {
                    Player player = Bukkit.getPlayer(entry.getKey());
                    HologramEntry hologram = entry.getValue();
                    if (player == null || !player.isOnline() ||
                            (hologram.durationTicks > 0 && now - hologram.spawnTime >= hologram.durationTicks * 50)) {
                        removeHologramByEntry(hologram);
                        return true;
                    }

                    Location baseLocation = getHologramBaseLocation(player, finalModulatedDistance, hologram.displays.size(), minPitch, maxPitch);
                    double y = baseLocation.getY();

                    for (TextDisplay display : hologram.displays) {
                        Location target = baseLocation.clone();
                        target.setY(y);
                        float clampedPitch = Math.min(target.getPitch(), 0f);
                        target.setPitch(clampedPitch);

                        Location current = display.getLocation();
                        Location lerped = current.clone().add(target.clone().subtract(current).multiply(lerpFactor));
                        lerped.setYaw(target.getYaw());
                        lerped.setPitch(clampedPitch);

                        display.teleport(lerped);
                        y -= LINE_SPACING;
                    }
                    return false;
                });

                if (trackingHolograms.isEmpty()) {
                    trackingTaskStarted = false;
                    cancel();
                }
                tick++;
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    private static void startBouncingTask(Player player, List<TextDisplay> displays, UUID id, long spawnTime, long durationTicks, Location baseLocation, double amplitude, int frequencyTicks, JavaPlugin plugin) {
        new BukkitRunnable() {
            int tick = 0;
            final double baseY = baseLocation.getY();

            @Override
            public void run() {
                if (!player.isOnline() || !activeHolograms.containsKey(id)) {
                    cancel();
                    return;
                }

                long now = System.currentTimeMillis();
                if (durationTicks > 0 && now - spawnTime >= durationTicks * 50) {
                    removeHologramByEntry(activeHolograms.remove(id));
                    cancel();
                    return;
                }

                double offsetY = Math.sin((tick / (double) frequencyTicks) * 2 * Math.PI) * amplitude;
                double y = baseY + offsetY;

                for (TextDisplay display : displays) {
                    Location target = baseLocation.clone();
                    target.setY(y);
                    display.teleport(target);
                    y -= LINE_SPACING;
                }

                tick++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private static List<TextDisplay> createTextDisplays(List<String> lines, Location baseLocation) {
        List<TextDisplay> displays = new ArrayList<>();
        World world = baseLocation.getWorld();
        if (world == null) return displays;

        double y = baseLocation.getY() + (LINE_SPACING * (lines.size() - 1) / 2.0);
        for (String line : lines) {
            Location lineLoc = baseLocation.clone();
            lineLoc.setY(y);

            TextDisplay display = world.spawn(lineLoc, TextDisplay.class, td -> configureTextDisplay(td, line));
            displays.add(display);
            y -= LINE_SPACING;
        }
        return displays;
    }

    private static void configureTextDisplay(TextDisplay td, String line) {
        Component component = SerializerUtil.legacyToMiniMessageComponent(line);
        td.text(component);
        td.setBillboard(TextDisplay.Billboard.CENTER);
        td.setSeeThrough(true);
        td.setShadowed(true);
        td.setPersistent(true);
        td.setGravity(false);
        td.setLineWidth(1000);
        td.getPersistentDataContainer().set(HOLOGRAM_KEY, PersistentDataType.BOOLEAN, true);
    }

    private static Location getHologramBaseLocation(Player player, double distance, int linesCount, float minPitch, float maxPitch) {
        Location eyeLoc = player.getEyeLocation().clone();
        // Clamp pitch to 0 if above 0
        float pitch = Math.min(eyeLoc.getPitch(), minPitch);
        pitch = Math.max(pitch, maxPitch);
        float yaw = eyeLoc.getYaw();
        // Create a direction vector with clamped pitch
        Location directionLoc = new Location(eyeLoc.getWorld(), 0, 0, 0, yaw, pitch);
        Vector direction = directionLoc.getDirection().normalize();
        Location target = eyeLoc.add(direction.multiply(distance));
        target.setY(target.getY() + (LINE_SPACING * (linesCount - 1) / 2.0));
        return target;
    }

    private static void removeHologramByEntry(HologramEntry entry) {
        if (entry != null) {
            entry.displays.forEach(Entity::remove);
        }
    }

    private static class HologramEntry {
        final List<TextDisplay> displays;
        final long spawnTime;
        final long durationTicks;

        HologramEntry(List<TextDisplay> displays, long spawnTime, long durationTicks) {
            this.displays = displays;
            this.spawnTime = spawnTime;
            this.durationTicks = durationTicks;
        }
    }
}
