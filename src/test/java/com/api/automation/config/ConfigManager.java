package com.api.automation.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton configuration manager for loading and accessing properties
 */
public class ConfigManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties properties;
    
    private static final String CONFIG_FILE = "src/test/resources/config.properties";
    
    private ConfigManager() {
        loadProperties();
    }
    
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully from: {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", CONFIG_FILE, e);
            throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
        }
    }
    
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found for key: {}", key);
        }
        return value;
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Invalid integer value for key: {}", key);
            throw e;
        }
    }
    
    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }
}
