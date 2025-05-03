package dev.xhue.neon.Commands;

import dev.xhue.neon.NeON;
import dev.xhue.neon.Utils.FireworkUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandHandler implements CommandExecutor {

    private final NeON plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    // Constructor to get the main plugin instance
    public CommandHandler(NeON plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("neon")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("neon.reload")) {
                    Component noPermMessage = miniMessage.deserialize(NeON.prefix + "<red>You do not have permission to execute this command.</red>");
                    sender.sendMessage(noPermMessage);
                    plugin.getPluginLogger().warning(legacyFromMiniMessage(NeON.prefix + "<red>Permission denied for " + sender.getName() + ".</red>"));
                    return true;
                }

                try {
                    if (plugin.getConfigManager().reloadConfig()) {
                        Component successMessage = miniMessage.deserialize(NeON.prefix + "<dark_gray> » <gray>NeON configuration reloaded <green>successfully<gray>!</green>");
                        sender.sendMessage(successMessage);
                        plugin.getPluginLogger().info(legacyFromMiniMessage(NeON.prefix + "<dark_gray> » <gray>Configuration reloaded by " + sender.getName() + ".</gray>"));
                    } else {
                        Component errorMessage = miniMessage.deserialize(NeON.prefix + "<dark_gray> » <red>Failed to reload NeON configuration. Check console for errors.</red>");
                        sender.sendMessage(errorMessage);
                    }

                } catch (Exception e) {
                    Component errorMessage = miniMessage.deserialize(NeON.prefix + "<dark_gray> » <red>Failed to reload NeON configuration. Check console for errors.</red>");
                    sender.sendMessage(errorMessage);
                    plugin.getPluginLogger().severe(legacyFromMiniMessage(NeON.prefix + "<dark_gray> » <red>Error reloading configuration: " + e.getMessage() + "</red>"));
                    e.printStackTrace();
                }
                return true;
            } else if (args.length > 0 && args[0].equalsIgnoreCase("firework")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    FireworkUtil.spawnRandomFirework(player.getLocation());
                    plugin.getPluginLogger().info(legacyFromMiniMessage(NeON.prefix + "<dark_gray> » <gray>Firework launched by " + player.getName() + ".</gray>"));
                } else {
                    sender.sendMessage(legacyFromMiniMessage(NeON.prefix + "<dark_gray> | <red>This command can only be run by a player."));
                }
            } else {
                Component usageMessage = miniMessage.deserialize(NeON.prefix + "<dark_gray> » <yellow>Usage: /neon reload</yellow>");
                sender.sendMessage(usageMessage);
                return true;
            }
        }
        return false;
    }

    // Utility method to convert MiniMessage to legacy color codes for logger
    private String legacyFromMiniMessage(String miniMsg) {
        return net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().serialize(
                miniMessage.deserialize(miniMsg)
        );
    }
}