package dev.xhue.neon.Config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConfigManager {
    private final File configFile;
    public FileConfiguration config;
    private final Logger logger = Logger.getLogger("NeON");

    public ConfigManager(File dataFolder, String fileName) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        this.configFile = new File(dataFolder, fileName);

        if (!configFile.exists() || configFile.length() == 0) {
            try {
                if (!configFile.exists()) {
                    configFile.createNewFile();
                }
                // Add initial comment to the config file
                List<String> header = new ArrayList<>();
                header.add("  ###################################");
                header.add("         ▐ ▄ ▄▄▄ .       ▐ ▄ ");
                header.add("        •█▌▐█▀▄.▀·▪     •█▌▐█");
                header.add("        ▐█▐▐▌▐▀▀▪▄ ▄█▀▄ ▐█▐▐▌");
                header.add("        ██▐█▌▐█▄▄▌▐█▌.▐▌██▐█▌");
                header.add("        ▀▀ █▪ ▀▀▀  ▀█▄▀▪▀▀ █▪ by xHue");
                header.add("  ###################################");

                header.add("Thanks for downloading NeON! This is the configuration file.");
                header.add(" ");
                header.add("Indentation is important in YAML files. Please ensure you maintain the generated layout.");
                header.add("If you encounter issues, please first check the server console for errors.");
                header.add("If that yields no results, try putting your config file through YAML validator, like: https://www.yamllint.com/");
                header.add("If all else fails, please open an issue on the GitHub repository: <github link>");



                header.add(" ");

                config = YamlConfiguration.loadConfiguration(configFile);
                config.options().setHeader(header);
                saveConfig();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.config = YamlConfiguration.loadConfiguration(configFile);
            this.lastLoadedTimestamp = configFile.lastModified(); // Store timestamp on successful load
        }
        reloadConfig(); // Use reloadConfig here to load initially
    }


    // Store the last modified timestamp of the successfully loaded file
    // Initialize to -1 to indicate it hasn't been loaded successfully yet
    private long lastLoadedTimestamp = -1L;


    public void reloadConfig() {
        if (configFile == null) {
            System.out.println("[DEBUG] Cannot reload configuration: file path is not set.");
            return;
        }
        if (!configFile.exists()) {
            System.out.println("[DEBUG] Cannot reload configuration: File not found at expected path: " + configFile.getPath() + ". The existing configuration in memory remains unchanged.");
            this.lastLoadedTimestamp = -1L;
            return;
        }

        YamlConfiguration newConfig = new YamlConfiguration();
        try {
            newConfig.load(configFile);
            this.config = newConfig;
            this.lastLoadedTimestamp = configFile.lastModified();
            System.out.println("[DEBUG] Successfully reloaded configuration from " + configFile.getPath());
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Config Reload failed: Configuration file disappeared unexpectedly: " + configFile.getPath());
            e.printStackTrace();
            this.lastLoadedTimestamp = -1L;
        } catch (IOException e) {
            System.out.println("[ERROR] Config Reload failed: Could not read configuration file: " + configFile.getPath());
            e.printStackTrace();
            this.lastLoadedTimestamp = -1L;
        } catch (InvalidConfigurationException e) {
            System.out.println("[ERROR] Config Reload failed: Invalid configuration format: " + configFile.getPath());
            e.printStackTrace();
            handleInvalidConfigFile(e);
        }
    }


    /**
     * Handles the situation where the configuration file on disk is invalid.
     * Renames the invalid file and attempts to save the current in-memory config.
     *
     * @param e The exception indicating the invalid configuration.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");

    private void handleInvalidConfigFile(InvalidConfigurationException e) {
        // Log the primary error
        logger.log(Level.SEVERE, "Config Reload failed: Invalid configuration format detected in file: " + configFile.getPath() + ". Attempting to backup invalid file and restore last known good configuration.", e);
        if (e.getCause() != null) {
            logger.log(Level.SEVERE, "Underlying cause: ", e.getCause());
        }

        // 1. Rename the invalid file
        String timestamp = DATE_FORMAT.format(new Date());
        String invalidFileName = configFile.getName() + "." + timestamp + ".INVALID";
        File invalidFile = new File(configFile.getParentFile(), invalidFileName);

        try {
            // Use Files.move for better error handling and atomic operation where possible
            Files.move(configFile.toPath(), invalidFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Backed up invalid configuration file to: " + invalidFile.getPath());
        } catch (IOException moveEx) {
            logger.log(Level.SEVERE, "Failed to rename/backup the invalid configuration file: " + configFile.getPath() + " to " + invalidFile.getPath(), moveEx);
            // Decide if you should proceed with saving or stop here
            // For safety, maybe don't overwrite if backup failed?
            logger.warning("Aborting restore of last known good configuration because backup failed.");
            return; // Stop here if backup failed
        }

        // 2. Save the current in-memory config back to the original file path
        logger.info("Attempting to save the last known good configuration back to: " + configFile.getPath());
        saveConfig(); // Call the separate save method
    }


    // Get a configuration value
    public Object get(String key) {
        reloadConfig(); // Reload from disk before getting the value
        return config.get(key);
    }

    // Set a configuration value (adds or updates)
    public void set(String key, Object value) {
        config.set(key, value);
        saveConfig();
    }

    public void addIfAbsent(String key, Object value, String comment) {
        if (!config.contains(key)) {
            logger.info("[DEBUG] Key '" + key + "' is absent. Adding value: " + value);

            // 1\. Backup comments for all existing keys
            Map<String, List<String>> existingComments = new HashMap<>();
            for (String existingKey : config.getKeys(true)) {
                List<String> commentLines = config.getComments(existingKey);
                if (commentLines != null && !commentLines.isEmpty()) {
                    // clone to avoid mutating the original list
                    existingComments.put(existingKey, new ArrayList<>(commentLines));
                }
            }

            // 2\. Add the new key/value
            config.set(key, value);

            // 3\. Apply comment to the new key if provided
            if (comment != null && !comment.isBlank()) {
                logger.info("[DEBUG] Adding comment to key: " + key);
                List<String> commentLines = new ArrayList<>();
                commentLines.add(" ");
                commentLines.add(" ##############################");
                for (String line : comment.split("\r?\n")) {
                    commentLines.add(" " + line);
                }
                commentLines.add(" ##############################");
                config.setComments(key, commentLines);
            }

            // 4\. Restore comments on other keys
            for (Map.Entry<String, List<String>> entry : existingComments.entrySet()) {
                String existingKey = entry.getKey();
                if (!existingKey.equals(key)) {
                    config.setComments(existingKey, entry.getValue());
                }
            }

            // 5\. Save once with everything in place
            saveConfig();
        } else {
            logger.info("[DEBUG] Key '" + key + "' already exists. Skipping addition.");
        }
    }

    public void addIfAbsent(String key, Object value, List<String> comment) {
        if (!config.contains(key)) {
            logger.info("[DEBUG] Key '" + key + "' is absent. Adding value: " + value);

            // 1\. Backup comments for all existing keys
            Map<String, List<String>> existingComments = new HashMap<>();
            for (String existingKey : config.getKeys(true)) {
                List<String> commentLines = config.getComments(existingKey);
                if (commentLines != null && !commentLines.isEmpty()) {
                    // clone to avoid mutating the original list
                    existingComments.put(existingKey, new ArrayList<>(commentLines));
                }
            }

            // Create a new list with prefixed strings
            List<String> prefixedComment = comment.stream()
                    .map(s -> " " + s) // Prepend a space to each string 's'
                    .toList(); // Collect results into a new list

            // 2\. Add the new key/value
            config.set(key, value);

            // 3\. Apply comment to the new key if provided
            if (comment != null && !comment.isEmpty()) {
                logger.info("[DEBUG] Adding comment to key: " + key);
                List<String> commentLines = new ArrayList<>();
                commentLines.add(" ");
                commentLines.add(" ##############################");
                commentLines.addAll(prefixedComment);
                commentLines.add(" ##############################");
                config.setComments(key, commentLines);
            }

            // 4\. Restore comments on other keys
            for (Map.Entry<String, List<String>> entry : existingComments.entrySet()) {
                String existingKey = entry.getKey();
                if (!existingKey.equals(key)) {
                    config.setComments(existingKey, entry.getValue());
                }
            }

            // 5\. Save once with everything in place
            saveConfig();
        } else {
            logger.info("[DEBUG] Key '" + key + "' already exists. Skipping addition.");
        }
    }

    // Remove a configuration value
    public void remove(String key) {
        config.set(key, null);
        saveConfig();
    }

    // Save configuration to file
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
