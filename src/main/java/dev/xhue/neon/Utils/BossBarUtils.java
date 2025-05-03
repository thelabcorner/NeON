package dev.xhue.neon.Utils;

import dev.xhue.neon.NeON;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;

import static dev.xhue.neon.Utils.SerializerUtil.legacyToMiniMessageComponent;

public class BossBarUtils {

    public static void handleBossBar(Player player, Component bossbarComponent, String colorStr, String styleStr, long durationTicks, String direction) {
        BossBar.Color color;
        try {
            color = BossBar.Color.valueOf(colorStr.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            NeON.getPlugin().getLogger().warning("Invalid BossBar color specified: " + colorStr + ". Defaulting to PURPLE.");
            color = BossBar.Color.PURPLE;
        }

        BossBar.Overlay style;
        try {
            style = BossBar.Overlay.valueOf(styleStr.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            NeON.getPlugin().getLogger().warning("Invalid BossBar style specified: " + styleStr + ". Defaulting to PROGRESS.");
            style = BossBar.Overlay.PROGRESS;
        }

        BossBar bossBar = BossBar.bossBar(bossbarComponent, 1.0f, color, style);
        player.showBossBar(bossBar);

        if (durationTicks > 0) {
            new BukkitRunnable() {
                private long ticksElapsed = 0;
                private final long totalTicks = durationTicks;

                @Override
                public void run() {
                    if (ticksElapsed >= totalTicks) {
                        player.hideBossBar(bossBar);
                        this.cancel();
                        return;
                    }

                    float progress;
                    float t = (float) (ticksElapsed + 1) / totalTicks;
                    switch (direction.toUpperCase(Locale.ROOT)) {
                        case "RIGHT_TO_LEFT":
                            progress = (float) (totalTicks - ticksElapsed) / totalTicks;
                            break;
                        case "LEFT_TO_RIGHT":
                            progress = t;
                            break;
                        case "RIGHT_TO_LEFT_CUBIC":
                            progress = 1.0f - cubicBezier(t, 0.4f, 0.0f, 0.2f, 1.0f);
                            break;
                        case "LEFT_TO_RIGHT_CUBIC":
                            progress = cubicBezier(t, 0.4f, 0.0f, 0.2f, 1.0f);
                            break;
                        case "SOLID":
                            progress = 1.0f;
                            break;
                        default:
                            progress = (float) (totalTicks - ticksElapsed) / totalTicks;
                            break;
                    }
                    progress = Math.max(0.0f, Math.min(1.0f, progress));
                    bossBar.progress(progress);

                    ticksElapsed++;
                }
            }.runTaskTimer(NeON.getPlugin(), 0L, 1L);
        }
    }

    // Cubic Bezier helper: p0=0, p1, p2, p3=1
    private static float cubicBezier(float t, float p1, float p2, float p3, float p4) {
        float u = 1 - t;
        return (float) (
            u * u * u * 0 +
            3 * u * u * t * p1 +
            3 * u * t * t * p2 +
            t * t * t * 1
        );
    }

}
