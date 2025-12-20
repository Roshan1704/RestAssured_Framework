package com.api.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Utility class for YAML operations
 */
public class YamlUtil {
    
    private static final Logger logger = LogManager.getLogger(YamlUtil.class);
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    
    /**
     * Parse YAML file to Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseYamlFile(String filePath) {
        try {
            return yamlMapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            logger.error("Failed to parse YAML file: {}", filePath, e);
            throw new RuntimeException("YAML parsing failed", e);
        }
    }
    
    /**
     * Parse YAML file to POJO
     */
    public static <T> T parseYamlFile(String filePath, Class<T> clazz) {
        try {
            return yamlMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            logger.error("Failed to parse YAML file: {}", filePath, e);
            throw new RuntimeException("YAML parsing failed", e);
        }
    }
    
    /**
     * Get value from YAML by key path (e.g., "environments.QA.baseUri")
     */
    @SuppressWarnings("unchecked")
    public static Object getValueByPath(String filePath, String keyPath) {
        Map<String, Object> yamlData = parseYamlFile(filePath);
        String[] keys = keyPath.split("\\.");
        
        Object value = yamlData;
        for (String key : keys) {
            if (value instanceof Map) {
                value = ((Map<String, Object>) value).get(key);
            } else {
                return null;
            }
        }
        return value;
    }
}
