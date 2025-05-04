package dev.xhue.neon.Listeners;

import dev.xhue.neon.Config.ConfigManager;
import dev.xhue.neon.HologramManager;
import dev.xhue.neon.NeON;
import dev.xhue.neon.Utils.*;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static dev.xhue.neon.Utils.SerializerUtil.legacyToMiniMessageComponent;

public class onPlayerJoin implements Listener {

    // Consider making these final if they are initialized once and not reassigned
    private final ConfigManager configManager = NeON.getPlugin().getConfigManager();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        // check if player is in vanish
        if (VanishUtils.isVanished(p)) {
            return; // do nothing if player is vanished
        }

        // Global chat join message settings
        boolean isGlobalEnabled = configManager.config.getBoolean("join.global_message.enabled");
        List<String> global_raw = configManager.config.getStringList("join.global_message.value");
        boolean isGlobalCentered = configManager.config.getBoolean("join.global_message.centered");

        boolean isFirstJoinEnabled = configManager.config.getBoolean("join.global_message.first_join.enabled");
        List<String> firstJoinMessage = configManager.config.getStringList("join.global_message.first_join.value");

        // Player join MOTD settings
        boolean isMOTDEnabled = configManager.config.getBoolean("join.player_motd.enabled");
        List<String> motdList = configManager.config.getStringList("join.player_motd.value");
        boolean isMOTDCentered = configManager.config.getBoolean("join.player_motd.centered");

        // Title settings
        boolean isPlayerTitleEnabled = configManager.config.getBoolean("join.player_title.enabled");
        String player_title_raw = configManager.config.getString("join.player_title.title");
        String player_subtitle_raw = configManager.config.getString("join.player_title.subtitle");
        long playerTitleStayMillis = configManager.config.getLong("join.player_title.time", 3) * 1000L; // default to 3 seconds

        boolean isGlobalTitleEnabled = configManager.config.getBoolean("join.global_title.enabled");
        String global_title_raw = configManager.config.getString("join.global_title.title");
        String global_subtitle_raw = configManager.config.getString("join.global_title.subtitle");
        long globalTitleStayMillis = configManager.config.getLong("join.global_title.time", 3) * 1000L; // default to 3 seconds

        // Action Bar settings
        boolean isPlayerActionBarEnabled = configManager.config.getBoolean("join.player_actionbar.enabled");
        String playerActionBar_raw = configManager.config.getString("join.player_actionbar.value");
        long playerActionBarStayMillis = configManager.config.getLong("join.player_actionbar.time", 3) * 1000L; // default to 3 seconds

        boolean isGlobalActionBarEnabled = configManager.config.getBoolean("join.global_actionbar.enabled");
        String globalActionBar_raw = configManager.config.getString("join.global_actionbar.value");
        long globalActionBarStayMillis = configManager.config.getLong("join.global_actionbar.time", 3) * 1000L; // default to 3 seconds

        // Boss Bar settings
        boolean isPlayerBossBarEnabled = configManager.config.getBoolean("join.player_bossbar.enabled");
        String bossbar_raw = configManager.config.getString("join.player_bossbar.value");
        String playerBossBarColorStr = configManager.config.getString("join.player_bossbar.color", "PURPLE");
        String playerBossBarStyleStr = configManager.config.getString("join.player_bossbar.style", "PROGRESS");
        long playerBossBarDuration = configManager.config.getLong("join.player_bossbar.duration", 5); // Duration in seconds
        long playerBossBarDurationTicks = playerBossBarDuration * 20L; // Convert seconds to ticks
        String playerBossBarDirection = configManager.config.getString("join.player_bossbar.direction", "RIGHT_TO_LEFT");

        boolean isGlobalBossBarEnabled = configManager.config.getBoolean("join.global_bossbar.enabled");
        String globalBossBar_raw = configManager.config.getString("join.global_bossbar.value");
        String globalBossBarColorStr = configManager.config.getString("join.global_bossbar.color", "PURPLE");
        String globalBossBarStyleStr = configManager.config.getString("join.global_bossbar.style", "PROGRESS");
        long globalBossBarDuration = configManager.config.getLong("join.global_bossbar.duration", 5); // Duration in seconds
        long globalBossBarDurationTicks = globalBossBarDuration * 20L; // Convert seconds to ticks
        String globalBossBarDirection = configManager.config.getString("join.global_bossbar.direction", "RIGHT_TO_LEFT");


        // Sound settings
        boolean isPlayerSoundEnabled = configManager.config.getBoolean("join.sound.player.enabled");
        String playerSoundRaw = configManager.config.getString("join.sound.player.value");
        List<String> pitchVolStr = configManager.config.getStringList("join.sound.player.pitch_volume");
        List<Float> playerSoundPitchVol = pitchVolStr.stream()
                .map(Float::parseFloat)
                .toList();

        boolean isGlobalSoundEnabled = configManager.config.getBoolean("join.sound.global.enabled");
        String globalSoundRaw = configManager.config.getString("join.sound.global.value");
        List<String> globalPitchVolStr = configManager.config.getStringList("join.sound.global.pitch_volume");
        List<Float> globalSoundPitchVol = globalPitchVolStr.stream()
                .map(Float::parseFloat)
                .toList();
        
        // Firework settings
        boolean isFireworkEnabled = configManager.config.getBoolean("join.fireworks.enabled");
        boolean isFireworkRandomized = configManager.config.getBoolean("join.fireworks.randomize");
        List<Color> fireworkColors = configManager.config.getStringList("join.fireworks.color").stream()
                .map(FireworkUtil::hexToColor)
                .collect(Collectors.toList());
        List<Color> fireworkFadeColors = configManager.config.getStringList("join.fireworks.fade_color").stream()
                .map(FireworkUtil::hexToColor)
                .collect(Collectors.toList());
        String fireworkTypeStr = configManager.config.getString("join.fireworks.type", "BALL");
        FireworkEffect.Type fireworkType = FireworkEffect.Type.valueOf(fireworkTypeStr);
        boolean fireworkFlicker = configManager.config.getBoolean("join.fireworks.flicker", false);
        boolean fireworkTrail = configManager.config.getBoolean("join.fireworks.trail", false);
        int fireworkPower = configManager.config.getInt("join.fireworks.power", 1);


        // HologramManager settings
        boolean isHologramEnabled = configManager.config.getBoolean("join.hologram.enabled");
        List<String> raw_hologramText = configManager.config.getStringList("join.hologram.value");
        long hologramStayTicks = configManager.config.getLong("join.hologram.duration", 5) * 20L; // value in ticks
        double hologramDistance = configManager.config.getDouble("join.hologram.distance", 1.5); // default distance
        boolean showFirstJoinHologram = (configManager.config.getString("join.hologram.first_join.enabled", "true")).equalsIgnoreCase("true");
        boolean showOnlyFirstJoinHologram = (configManager.config.getString("join.hologram.first_join.enabled", "false")).equalsIgnoreCase("only");
        List<String> raw_firstJoinHologramText = configManager.config.getStringList("join.hologram.first_join.value");
        boolean isTrackingHologramEnabled = configManager.config.getBoolean("join.hologram.tracking.enabled");
        boolean isTrackingThrobEnabled = configManager.config.getBoolean("join.hologram.tracking.throb");
        boolean isHologramBounceEnabled = configManager.config.getBoolean("join.hologram.bounce.enabled");
        boolean isHologramPitchLockEnabled = configManager.config.getBoolean("join.hologram.pitch_lock.enabled");
        List<String> raw_hologramPitchLock = configManager.config.getStringList("join.hologram.pitch_lock.value");
        List<Float> hologramPitchLockValues = raw_hologramPitchLock.stream()
            .map(Float::parseFloat)
            .toList();

        if (!isHologramPitchLockEnabled) {
            hologramPitchLockValues = List.of(-90f, 90f);
        }



        // Particles settings
        boolean isParticleEnabled = configManager.config.getBoolean("join.particle.enabled");
        String particleTypeStr = configManager.config.getString("join.particle.type", "EXPLOSION").replace("minecraft:", "");
        int particleAmount = configManager.config.getInt("join.particle.amount", 10);
        int particleRadius = configManager.config.getInt("join.particle.radius", 5);
        double particleSpeed = configManager.config.getDouble("join.particle.speed", 0.1);


        // Resource Pack settings
        boolean isResourcePackEnabled = configManager.config.getBoolean("join.resource_pack.enabled", false);
        String resourcePackUrl = configManager.config.getString("join.resource_pack.url");




        // global chat join message
        if (isFirstJoinEnabled && !p.hasPlayedBefore()) {
            for (String line : firstJoinMessage) {
                String formattedLine = PlaceholderAPI.setPlaceholders(p, line);
                formattedLine = PlaceholderUtils.replacePlaceholder(formattedLine, "%player%", p.getName());
                if (isGlobalCentered) {
                    formattedLine = CenteringUtil.getCenteredMessage(formattedLine);
                }
                Component msg = legacyToMiniMessageComponent(formattedLine);
                Bukkit.broadcast(msg);
            }
        }

        // global chat join message
        if (isGlobalEnabled) {
            for (String line : global_raw) {
                String formattedLine = PlaceholderAPI.setPlaceholders(p, line);
                formattedLine = PlaceholderUtils.replacePlaceholder(formattedLine, "%player%", p.getName());
                if (isGlobalCentered) {
                    formattedLine = CenteringUtil.getCenteredMessage(formattedLine);
                }
                Component msg = legacyToMiniMessageComponent(formattedLine);
                e.joinMessage(null);           // suppress vanilla
                Bukkit.broadcast(msg);         // fully parsed component
            }
        } else {
            e.joinMessage(null);               // suppress vanilla
        }


        // player join MOTD
        if (isMOTDEnabled) {
            for (String motd : motdList) {
                String formattedMotd = PlaceholderAPI.setPlaceholders(p, motd);
                formattedMotd = PlaceholderUtils.replacePlaceholder(formattedMotd, "%player%", p.getName());
                if (isMOTDCentered) {
                    formattedMotd = CenteringUtil.getCenteredMessage(formattedMotd);
                }
                // legacy → MM tags, then parse
                Component msg = legacyToMiniMessageComponent(formattedMotd);
                p.sendMessage(msg);           // fully parsed component
            }
        }

        // Hologram
        if (isHologramEnabled) {
            boolean isFirstJoin = !p.hasPlayedBefore();
            List<String> hologramText = raw_hologramText;

            if (showFirstJoinHologram || showOnlyFirstJoinHologram) {
                if (isFirstJoin) {
                    hologramText = raw_firstJoinHologramText;
                } else if (showOnlyFirstJoinHologram) {
                    hologramText = List.of();
                }
            }

            if (!hologramText.isEmpty()) {
                List<String> processedHologramText = hologramText.stream()
                        .map(line -> PlaceholderAPI.setPlaceholders(p, line))
                        .map(line -> PlaceholderUtils.replacePlaceholder(line, "%player%", p.getName()))
                        .collect(Collectors.toList());

                if (isTrackingHologramEnabled) {
                    HologramManager.spawnTrackingHologram(p, processedHologramText, hologramPitchLockValues.get(0), hologramPitchLockValues.get(1), hologramStayTicks, NeON.getPlugin(), hologramDistance, isTrackingThrobEnabled);
                } else if (isHologramBounceEnabled) {
                    HologramManager.spawnBouncingHologram(p, processedHologramText, hologramPitchLockValues.get(0), hologramPitchLockValues.get(1), hologramStayTicks, NeON.getPlugin(), hologramDistance, 0.05, 100);
                } else {
                    HologramManager.spawnHologram(p, processedHologramText, hologramPitchLockValues.get(0), hologramPitchLockValues.get(1), hologramStayTicks, NeON.getPlugin(), hologramDistance);
                }
            }
        }

        // Player Title
        if (isPlayerTitleEnabled) {
            // Apply PlaceholderAPI replacements
            player_title_raw = PlaceholderAPI.setPlaceholders(p, player_title_raw);
            player_subtitle_raw = PlaceholderAPI.setPlaceholders(p, player_subtitle_raw);

            player_title_raw = PlaceholderUtils.replacePlaceholder(player_title_raw, "%player%", p.getName());
            player_subtitle_raw = PlaceholderUtils.replacePlaceholder(player_subtitle_raw, "%player%", p.getName());

            // Parse MiniMessage strings into Components
            Component titleComponent = legacyToMiniMessageComponent(player_title_raw);
            Component subtitleComponent = legacyToMiniMessageComponent(player_subtitle_raw);

            // Optionally, define custom times for the title display
            Title.Times times = Title.Times.times(Duration.ofMillis(1000), Duration.ofMillis(playerTitleStayMillis), Duration.ofMillis(1000));

            // Create and show the title
            Title title = Title.title(titleComponent, subtitleComponent, times);
            p.showTitle(title);
        }

        // Global Title
        if (isGlobalTitleEnabled) {
            // Apply PlaceholderAPI replacements
            String finalGlobalTitle_raw = PlaceholderAPI.setPlaceholders(p, global_title_raw);
            String finalGlobalSubtitle_raw = PlaceholderAPI.setPlaceholders(p, global_subtitle_raw);

            // apply Placeholders replacements for the joining player
            finalGlobalTitle_raw = PlaceholderUtils.replacePlaceholder(finalGlobalTitle_raw, "%joined_player%", p.getName());
            finalGlobalSubtitle_raw = PlaceholderUtils.replacePlaceholder(finalGlobalSubtitle_raw, "%joined_player%", p.getName());

            // Optionally, define custom times for the title display
            Title.Times times = Title.Times.times(Duration.ofMillis(1000), Duration.ofMillis(globalTitleStayMillis), Duration.ofMillis(1000));

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                // Apply Placeholders for the messaged player
                finalGlobalTitle_raw = PlaceholderUtils.replacePlaceholder(finalGlobalTitle_raw, "%global_player%", onlinePlayer.getName());
                finalGlobalSubtitle_raw = PlaceholderUtils.replacePlaceholder(finalGlobalSubtitle_raw, "%global_player%", onlinePlayer.getName());

                // Parse MiniMessage strings into Components
                Component titleComponent = legacyToMiniMessageComponent(finalGlobalTitle_raw);
                Component subtitleComponent = legacyToMiniMessageComponent(finalGlobalSubtitle_raw);

                // Create and show the title to all online players, excluding the joining player if player title is enabled
                Title title = Title.title(titleComponent, subtitleComponent, times);

                if (!isPlayerTitleEnabled || !onlinePlayer.equals(p)) {
                    onlinePlayer.showTitle(title);
                }
            }
        }

        // Action Bar
        if (isPlayerActionBarEnabled) {
            // Apply PlaceholderAPI replacements
            playerActionBar_raw = PlaceholderAPI.setPlaceholders(p, playerActionBar_raw);

            // apply Placeholders replacements for the joining player
            playerActionBar_raw = PlaceholderUtils.replacePlaceholder(playerActionBar_raw, "%player%", p.getName());

            // Parse MiniMessage strings into Components
            Component actionbarComponent = legacyToMiniMessageComponent(playerActionBar_raw);

            // Send the action bar message to the player
            ActionBarUtils.sendActionBar(p, actionbarComponent, playerActionBarStayMillis);
        }

        if (isGlobalActionBarEnabled) {
            // Apply PlaceholderAPI replacements
            String finalGlobalActionBar_raw = PlaceholderAPI.setPlaceholders(p, globalActionBar_raw);

            // apply Placeholders replacements for the joining player
            finalGlobalActionBar_raw = PlaceholderUtils.replacePlaceholder(finalGlobalActionBar_raw, "%joined_player%", p.getName());

            // Parse MiniMessage strings into Components
            Component actionbarComponent = legacyToMiniMessageComponent(finalGlobalActionBar_raw);

            // Send the action bar message to all online players, excluding the joining player if player action bar is enabled
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                // Apply Placeholders for the messaged player
                String processedGlobalActionBar = PlaceholderUtils.replacePlaceholder(finalGlobalActionBar_raw, "%global_player%", onlinePlayer.getName());
                Component finalActionbarComponent = legacyToMiniMessageComponent(processedGlobalActionBar);
                if (!isPlayerActionBarEnabled || !onlinePlayer.equals(p)) {
                    ActionBarUtils.sendActionBar(onlinePlayer, finalActionbarComponent, globalActionBarStayMillis);
                }
            }
        }

        // Boss Bar
        if (isPlayerBossBarEnabled) {
            // Apply PlaceholderAPI replacements
            bossbar_raw = PlaceholderAPI.setPlaceholders(p, bossbar_raw);

            // apply Placeholders replacements for the joining player
            bossbar_raw = PlaceholderUtils.replacePlaceholder(bossbar_raw, "%player%", p.getName());

            // Parse MiniMessage string into Component
            Component bossbarComponent = legacyToMiniMessageComponent(bossbar_raw);

            // Handle the boss bar for the player
            BossBarUtils.handleBossBar(p, bossbarComponent, playerBossBarColorStr, playerBossBarStyleStr, playerBossBarDurationTicks, playerBossBarDirection);
        }

        if (isGlobalBossBarEnabled) {
            // Apply PlaceholderAPI replacements
            String finalGlobalBossBar_raw = PlaceholderAPI.setPlaceholders(p, globalBossBar_raw);

            // apply Placeholders replacements for the joining player
            finalGlobalBossBar_raw = PlaceholderUtils.replacePlaceholder(finalGlobalBossBar_raw, "%joined_player%", p.getName());

            // Send the boss bar message to all online players, excluding the joining player if player boss bar is enabled
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                // Apply Placeholders for the messaged player
                String processedGlobalBossBar = PlaceholderUtils.replacePlaceholder(finalGlobalBossBar_raw, "%global_player%", onlinePlayer.getName());
                // Parse MiniMessage string into Component
                Component bossbarComponent = legacyToMiniMessageComponent(finalGlobalBossBar_raw);
                if (!isPlayerBossBarEnabled || !onlinePlayer.equals(p)) {
                    BossBarUtils.handleBossBar(onlinePlayer, bossbarComponent, globalBossBarColorStr, globalBossBarStyleStr, globalBossBarDurationTicks, globalBossBarDirection);
                }
            }
        }


        // Sound
        if (isPlayerSoundEnabled) {
            assert playerSoundRaw != null;
            Sound sound = SoundUtils.getSoundOrThrow(playerSoundRaw);
            p.playSound(
                    p.getEyeLocation(),
                    sound,
                    playerSoundPitchVol.get(1), // volume
                    playerSoundPitchVol.get(0)  // pitch
            );
        }

        if (isGlobalSoundEnabled) {
            assert globalSoundRaw != null;
            Sound sound = SoundUtils.getSoundOrThrow(globalSoundRaw);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!isPlayerSoundEnabled || !onlinePlayer.equals(p)) {
                    onlinePlayer.playSound(
                            onlinePlayer.getEyeLocation(),
                            sound,
                            globalSoundPitchVol.get(1), // volume
                            globalSoundPitchVol.get(0)  // pitch
                    );
                }

            }
        }

        if (isFireworkEnabled) {
            if (!isFireworkRandomized) {
            FireworkUtil.spawnFirework(p.getLocation(), fireworkColors, fireworkFadeColors, fireworkType, fireworkFlicker, fireworkTrail, fireworkPower);
            } else {
                FireworkUtil.spawnRandomFirework(p.getLocation());
            }
        }



        if (isParticleEnabled) {
            // Particle effect handling
            Particle particleEffect = Particle.valueOf(particleTypeStr);
            Vector direction = p.getEyeLocation().getDirection();
            Location spawnLocation = p.getEyeLocation().add(direction);

            double offsetX = (Math.random() - 0.5) * particleRadius;
            double offsetY = (Math.random() - 0.5) * particleRadius;
            double offsetZ = (Math.random() - 0.5) * particleRadius;

            p.spawnParticle(particleEffect, spawnLocation, particleAmount, offsetX, offsetY, offsetZ, particleSpeed);
        }

        if (isResourcePackEnabled) {
            try {
                String resourcePackHash = ResourcePackUtils.getSHA1ForPack(resourcePackUrl);
                assert resourcePackUrl != null;
                p.setResourcePack(resourcePackUrl, resourcePackHash);
            } catch (Exception ex) {
                ex.printStackTrace();
                // Optionally, fallback or notify player/admin
            }
        }


        // @todo: add/test vanish support -- essentials not working, but essentials vanish is bad anyway bc it sends packets

        // @todo: add more support for join hologram maybe protocollib for 'client-side' holograms)


    }

}


    // Class closing brace