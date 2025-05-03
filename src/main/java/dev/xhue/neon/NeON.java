package dev.xhue.neon;

import dev.xhue.neon.Config.ConfigGenerator;
import dev.xhue.neon.Config.ConfigManager;
import dev.xhue.neon.Listeners.FireworkDamageListener;
import dev.xhue.neon.Listeners.onPlayerJoin;
import dev.xhue.neon.Listeners.onPlayerLeave;
import dev.xhue.neon.Metrics.Metrics;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;


public final class NeON extends JavaPlugin {

    private final Logger logger = getLogger();

    private ConfigManager configManager;

    private PluginManager pluginManager;

    private static NeON instance;

    public static NeON getPlugin() { return instance; }

    public Logger getPluginLogger() { return logger; }


    public boolean papiEnabled;


    public ConfigManager getConfigManager() { return configManager; }


    @Override
    public void onEnable() {

        instance = this;

        pluginManager = getServer().getPluginManager();

        logger.info("NeON is starting up...");
        logger.setLevel(Level.ALL);

        Metrics metrics = new Metrics(this,  25711);

        // Check if PlaceholderAPI is enabled
        if (pluginManager.isPluginEnabled("PlaceholderAPI")) {
            logger.info("PlaceholderAPI found, enabling support.");
            papiEnabled = true;
        } else {
            logger.warning("PlaceholderAPI not found, disabling support.");
            papiEnabled = false;
        }

        // Plugin startup logic
        configManager = new ConfigManager(getDataFolder(), "config.yml");
        ConfigGenerator.generateConfig(configManager, logger);


        // Register event listeners
        pluginManager.registerEvents(new onPlayerJoin(), instance);
        pluginManager.registerEvents(new onPlayerLeave(), instance);
        pluginManager.registerEvents(new FireworkDamageListener(), instance);


        // Register command handlers
        getCommand("neon").setExecutor(new dev.xhue.neon.Commands.CommandHandler(this));


    }
}