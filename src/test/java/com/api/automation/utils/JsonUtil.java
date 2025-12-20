package com.api.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility class for JSON operations
 */
public class JsonUtil {
    
    private static final Logger logger = LogManager.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Parse JSON response to POJO
     */
    public static <T> T parseResponse(Response response, Class<T> clazz) {
        try {
            return objectMapper.readValue(response.asString(), clazz);
        } catch (IOException e) {
            logger.error("Failed to parse JSON response to {}", clazz.getName(), e);
            throw new RuntimeException("JSON parsing failed", e);
        }
    }
    
    /**
     * Parse JSON file to POJO
     */
    public static <T> T parseJsonFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            logger.error("Failed to parse JSON file: {}", filePath, e);
            throw new RuntimeException("JSON file parsing failed", e);
        }
    }
    
    /**
     * Parse JSON array from file
     */
    public static <T> List<T> parseJsonArrayFile(String filePath, Class<T> clazz) {
        try {
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            return objectMapper.readValue(new File(filePath), listType);
        } catch (IOException e) {
            logger.error("Failed to parse JSON array file: {}", filePath, e);
            throw new RuntimeException("JSON array file parsing failed", e);
        }
    }
    
    /**
     * Convert object to JSON string
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Failed to convert object to JSON string", e);
            throw new RuntimeException("JSON conversion failed", e);
        }
    }
    
    /**
     * Pretty print JSON
     */
    public static String toPrettyJsonString(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Failed to convert object to pretty JSON string", e);
            throw new RuntimeException("JSON pretty print failed", e);
        }
    }
}
