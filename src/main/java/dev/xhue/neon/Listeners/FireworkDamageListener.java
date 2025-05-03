package dev.xhue.neon.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FireworkDamageListener implements Listener {

    // Make this static so FireworkUtil can access it
    public static final Map<UUID, ExplosionRecord> recentExplosions = new ConcurrentHashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (recentExplosions.isEmpty()) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            return;
        }

        Location pLoc = player.getLocation();
        String matchedId = null;

        // find any recent explosion within 4 blocks
        for (Map.Entry<UUID, ExplosionRecord> entry : recentExplosions.entrySet()) {
            ExplosionRecord rec = entry.getValue();

            if (!rec.loc.getWorld().equals(pLoc.getWorld())) {
                continue;
            }

            double distanceSq = rec.loc.distanceSquared(pLoc);
            if (distanceSq <= 16.0) { // 4*4 = 16
                matchedId = rec.id;
                break;
            }
        }

        if (matchedId != null) {
            // this was one of our fireworks
            event.setCancelled(true);
        }
    }

    // ExplosionRecord class for storing in the map
    public static class ExplosionRecord {
        public final String id;
        public final Location loc;

        public ExplosionRecord(String id, Location loc) {
            this.id = id;
            this.loc = loc;
        }
    }

}
