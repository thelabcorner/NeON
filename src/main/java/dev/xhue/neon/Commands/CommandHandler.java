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
        // Assuming the command registered in plugin.yml is "neon"
        if (command.getName().equalsIgnoreCase("neon")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                // Check permission
                if (!sender.hasPermission("neon.reload")) {
                    Component noPermMessage = miniMessage.deserialize("<red>You do not have permission to execute this command.</red>");
                    sender.sendMessage(noPermMessage);
                    return true;
                }

                // Reload configuration
                try {
                    plugin.getConfigManager().reloadConfig();
                    // You might need to re-initialize parts of your plugin that depend on the config here
                    Component successMessage = miniMessage.deserialize("<green>NeON configuration reloaded successfully!</green>");
                    sender.sendMessage(successMessage);
                    plugin.getPluginLogger().info("Configuration reloaded by " + sender.getName());
                } catch (Exception e) {
                    Component errorMessage = miniMessage.deserialize("<red>Failed to reload NeON configuration. Check console for errors.</red>");
                    sender.sendMessage(errorMessage);
                    plugin.getPluginLogger().severe("Error reloading configuration: " + e.getMessage());
                    e.printStackTrace();
                }
                return true; // Indicate the command was handled
            } else if (args.length > 0 && args[0].equalsIgnoreCase("firework")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    FireworkUtil.spawnRandomFirework(player.getLocation());
                } else {
                    sender.sendMessage("This command can only be run by a player.");
                }

            } else {
                // Handle base /neon command or invalid arguments, e.g., show help/version
                Component usageMessage = miniMessage.deserialize("<yellow>Usage: /neon reload</yellow>");
                sender.sendMessage(usageMessage);
                return true;
            }
        }
        return false; // Return false if the command is not handled by this executor
    }
}