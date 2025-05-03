package dev.xhue.neon.Config;

import java.util.logging.Logger;

public class ConfigGenerator {

    public static void generateConfig(ConfigManager configManager, Logger logger) {
        for (ConfigKey key : ConfigKey.values()) {
            try {
                configManager.addIfAbsent(key.getPath(), key.getDefaultValue(), key.getComment());
            } catch (Exception e) {
                logger.severe("Failed to set default config value for " + key.getPath() + ": " + e.getMessage());
            }
        }
    }
}
