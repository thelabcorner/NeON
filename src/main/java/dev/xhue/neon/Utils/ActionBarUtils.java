package dev.xhue.neon.Utils;

import dev.xhue.neon.NeON;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;

public class ActionBarUtils {

    private static NeON plugin = NeON.getPlugin();

    public static void sendActionBar(Player player, Component actionbarComponent, long durationMillis) {
        long startTime = System.currentTimeMillis();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime >= durationMillis) {
                    this.cancel(); // Stop the repeating task
                    return;
                }
                player.sendActionBar(actionbarComponent);
            }
        }.runTaskTimer(plugin, 0L, 20L); // Run every tick (20 ticks = 1 second)
    }
}