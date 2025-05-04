package dev.xhue.neon.Listeners;

import dev.xhue.neon.Config.ConfigManager;
import dev.xhue.neon.HologramManager;
import dev.xhue.neon.NeON;
import dev.xhue.neon.Utils.*;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static dev.xhue.neon.Utils.SerializerUtil.legacyToMiniMessage;
import static dev.xhue.neon.Utils.SerializerUtil.legacyToMiniMessageComponent;

public class onPlayerLeave implements Listener {

    private final ConfigManager configManager = NeON.getPlugin().getConfigManager();

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        // Check if the player is vanished
        if (VanishUtils.isVanished(p)) {
            return; // Ignore vanished players
        }

        // Global chat leave message settings
        boolean isGlobalEnabled = configManager.config.getBoolean("leave.global_message.enabled", true);
        List<String> global_raw = configManager.config.getStringList("leave.global_message.value");
        boolean isGlobalCentered = configManager.config.getBoolean("leave.global_message.centered", true);

        // Title settings
        boolean isGlobalTitleEnabled = configManager.config.getBoolean("leave.global_title.enabled", true);
        String global_title_raw = configManager.config.getString("leave.global_title.title", "<red><bold>PLAYER LEFT");
        String global_subtitle_raw = configManager.config.getString("leave.global_title.subtitle", "<gray><italic>dev.xhue.NeON");
        long globalTitleStayMillis = configManager.config.getLong("leave.global_title.time", 3) * 1000L;

        // Hologram settings
        boolean isHologramEnabled = configManager.config.getBoolean("leave.hologram.enabled", false);
        List<String> hologram_raw = configManager.config.getStringList("leave.hologram.value");
        long hologramStayTicks = configManager.config.getLong("leave.hologram.duration", 3) * 20L;


        // Action Bar settings
        boolean isGlobalActionBarEnabled = configManager.config.getBoolean("leave.global_actionbar.enabled", true);
        String globalActionBar_raw = configManager.config.getString("leave.global_actionbar.value", "<red>%left_player% left!");
        long globalActionBarStayMillis = configManager.config.getLong("leave.global_actionbar.time", 3) * 1000L;

        // Boss Bar settings
        boolean isGlobalBossBarEnabled = configManager.config.getBoolean("leave.global_bossbar.enabled", false);
        String globalBossBar_raw = configManager.config.getString("leave.global_bossbar.value", "<red>%left_player% left!");
        String globalBossBarColorStr = configManager.config.getString("leave.global_bossbar.color", "RED");
        String globalBossBarStyleStr = configManager.config.getString("leave.global_bossbar.style", "PROGRESS");
        long globalBossBarDuration = configManager.config.getLong("leave.global_bossbar.duration", 5);
        long globalBossBarDurationTicks = globalBossBarDuration * 20L;
        String globalBossBarDirection = configManager.config.getString("leave.global_bossbar.direction", "RIGHT_TO_LEFT");

        // Sound settings
        boolean isPlayerSoundEnabled = configManager.config.getBoolean("leave.sound.player.enabled", false);
        String playerSoundRaw = configManager.config.getString("leave.sound.player.value", "minecraft:block.note_block.bass");
        List<String> pitchVolStr = configManager.config.getStringList("leave.sound.player.pitch_volume");
        List<Float> playerSoundPitchVol = pitchVolStr.isEmpty() ? List.of(1.0f, 1.0f) : pitchVolStr.stream().map(Float::parseFloat).toList();

        boolean isGlobalSoundEnabled = configManager.config.getBoolean("leave.sound.global.enabled", false);
        String globalSoundRaw = configManager.config.getString("leave.sound.global.value", "minecraft:block.note_block.bass");
        List<String> globalPitchVolStr = configManager.config.getStringList("leave.sound.global.pitch_volume");
        List<Float> globalSoundPitchVol = globalPitchVolStr.isEmpty() ? List.of(1.0f, 1.0f) : globalPitchVolStr.stream().map(Float::parseFloat).toList();


        boolean isLeaveParticleEnabled = configManager.config.getBoolean("leave.particle.enabled", false);
        String leaveParticleRaw = configManager.config.getString("leave.particle.type", "minecraft:campfire_cosy_smoke");
        int leaveParticleCount = configManager.config.getInt("leave.particle.amount", 10);
        long leaveParticleRadius = configManager.config.getLong("leave.particle.radius", 5);
        double leaveParticleSpeed = configManager.config.getDouble("leave.particle.speed", 0.1);




        // global chat leave message
        if (isGlobalEnabled) {
            for (String line : global_raw) {
                String formattedLine = PlaceholderAPI.setPlaceholders(p, line);
                formattedLine = PlaceholderUtils.replacePlaceholder(formattedLine, "%player%", p.getName());
                if (isGlobalCentered) {
                    formattedLine = CenteringUtil.getCenteredMessage(formattedLine);
                }
                Component msg = legacyToMiniMessageComponent(formattedLine);
                event.quitMessage(null);           // suppress vanilla
                Bukkit.broadcast(msg);             // fully parsed component
            }
        } else {
            event.quitMessage(null);
        }

        // Global Title
        if (isGlobalTitleEnabled) {
            String finalGlobalTitle_raw = PlaceholderAPI.setPlaceholders(p, global_title_raw);
            String finalGlobalSubtitle_raw = PlaceholderAPI.setPlaceholders(p, global_subtitle_raw);

            finalGlobalTitle_raw = PlaceholderUtils.replacePlaceholder(finalGlobalTitle_raw, "%left_player%", p.getName());
            finalGlobalSubtitle_raw = PlaceholderUtils.replacePlaceholder(finalGlobalSubtitle_raw, "%left_player%", p.getName());

            Title.Times times = Title.Times.times(Duration.ofMillis(1000), Duration.ofMillis(globalTitleStayMillis), Duration.ofMillis(1000));

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String processedTitle = PlaceholderUtils.replacePlaceholder(finalGlobalTitle_raw, "%global_player%", onlinePlayer.getName());
                String processedSubtitle = PlaceholderUtils.replacePlaceholder(finalGlobalSubtitle_raw, "%global_player%", onlinePlayer.getName());

                Component titleComponent = legacyToMiniMessageComponent(processedTitle);
                Component subtitleComponent = legacyToMiniMessageComponent(processedSubtitle);

                Title title = Title.title(titleComponent, subtitleComponent, times);

                if (!onlinePlayer.equals(p)) {
                    onlinePlayer.showTitle(title);
                }
            }
        }


        // Hologram
        if (isHologramEnabled) {
            List<String> finalHologram_raw = hologram_raw.stream()
                    .map(line -> PlaceholderAPI.setPlaceholders(p, line))
                    .map(line -> PlaceholderUtils.replacePlaceholder(line, "%player%", p.getName()))
                    .collect(Collectors.toList());

            HologramManager.spawnHologram(p, finalHologram_raw, -90f, 90f, hologramStayTicks, NeON.getPlugin(), 0.0);
        }

        // Action Bar
        if (isGlobalActionBarEnabled) {
            String finalGlobalActionBar_raw = PlaceholderAPI.setPlaceholders(p, globalActionBar_raw);
            finalGlobalActionBar_raw = PlaceholderUtils.replacePlaceholder(finalGlobalActionBar_raw, "%left_player%", p.getName());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String processedGlobalActionBar = PlaceholderUtils.replacePlaceholder(finalGlobalActionBar_raw, "%global_player%", onlinePlayer.getName());
                Component finalActionbarComponent = legacyToMiniMessageComponent(processedGlobalActionBar);
                if (!onlinePlayer.equals(p)) {
                    ActionBarUtils.sendActionBar(onlinePlayer, finalActionbarComponent, globalActionBarStayMillis);
                }
            }
        }

        // Boss Bar
        if (isGlobalBossBarEnabled) {
            String finalGlobalBossBar_raw = PlaceholderAPI.setPlaceholders(p, globalBossBar_raw);
            finalGlobalBossBar_raw = PlaceholderUtils.replacePlaceholder(finalGlobalBossBar_raw, "%left_player%", p.getName());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String processedGlobalBossBar = PlaceholderUtils.replacePlaceholder(finalGlobalBossBar_raw, "%global_player%", onlinePlayer.getName());
                Component bossbarComponent = legacyToMiniMessageComponent(processedGlobalBossBar);
                if (!onlinePlayer.equals(p)) {
                    BossBarUtils.handleBossBar(onlinePlayer, bossbarComponent, globalBossBarColorStr, globalBossBarStyleStr, globalBossBarDurationTicks, globalBossBarDirection);
                }
            }
        }

        // Sound
        if (isPlayerSoundEnabled) {
            Sound sound = SoundUtils.getSoundOrThrow(playerSoundRaw);
            p.playSound(
                    p.getEyeLocation(),
                    sound,
                    playerSoundPitchVol.get(1),
                    playerSoundPitchVol.get(0)
            );
        }

        if (isGlobalSoundEnabled) {
            Sound sound = SoundUtils.getSoundOrThrow(globalSoundRaw);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!isPlayerSoundEnabled || !onlinePlayer.equals(p)) {
                    onlinePlayer.playSound(
                            onlinePlayer.getEyeLocation(),
                            sound,
                            globalSoundPitchVol.get(1),
                            globalSoundPitchVol.get(0)
                    );
                }
            }
        }

        if (isLeaveParticleEnabled) {
            // Particle effect handling
            Particle particleEffect = Particle.valueOf(leaveParticleRaw);
            Vector direction = p.getEyeLocation().getDirection();
            Location spawnLocation = p.getLocation().add(direction).add(0, 1.5, 0);

            double offsetX = (Math.random() - 0.5) * leaveParticleRadius;
            double offsetY = (Math.random() - 0.5) * leaveParticleRadius;
            double offsetZ = (Math.random() - 0.5) * leaveParticleRadius;
            spawnLocation.getWorld().spawnParticle(particleEffect, spawnLocation, leaveParticleCount, offsetX, offsetY, offsetZ, leaveParticleSpeed);
        }

        // @todo: add support for leave firework, particles, etc if desired
        // @todo: add vanish support
    }
}
