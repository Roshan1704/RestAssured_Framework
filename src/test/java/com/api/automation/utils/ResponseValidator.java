package com.api.automation.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.File;

/**
 * Utility class for response validation
 */
public class ResponseValidator {
    
    private static final Logger logger = LogManager.getLogger(ResponseValidator.class);
    
    /**
     * Validate status code
     */
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        logger.info("Validating status code. Expected: {}, Actual: {}", expectedStatusCode, actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode, 
                "Status code validation failed");
    }
    
    /**
     * Validate response time
     */
    public static void validateResponseTime(Response response, long maxTimeInMillis) {
        long actualTime = response.getTime();
        logger.info("Validating response time. Max: {}ms, Actual: {}ms", maxTimeInMillis, actualTime);
        Assert.assertTrue(actualTime <= maxTimeInMillis, 
                "Response time validation failed. Expected <= " + maxTimeInMillis + "ms, but was " + actualTime + "ms");
    }
    
    /**
     * Validate field exists in response
     */
    public static void validateFieldExists(Response response, String fieldPath) {
        Object value = response.jsonPath().get(fieldPath);
        logger.info("Validating field exists: {}", fieldPath);
        Assert.assertNotNull(value, "Field does not exist: " + fieldPath);
    }
    
    /**
     * Validate field value
     */
    public static void validateFieldValue(Response response, String fieldPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(fieldPath);
        logger.info("Validating field value. Field: {}, Expected: {}, Actual: {}", 
                fieldPath, expectedValue, actualValue);
        Assert.assertEquals(actualValue, expectedValue, 
                "Field value validation failed for: " + fieldPath);
    }
    
    /**
     * Validate content type
     */
    public static void validateContentType(Response response, String expectedContentType) {
        String actualContentType = response.getContentType();
        logger.info("Validating content type. Expected: {}, Actual: {}", 
                expectedContentType, actualContentType);
        Assert.assertTrue(actualContentType.contains(expectedContentType), 
                "Content type validation failed");
    }
    
    /**
     * Validate JSON schema
     */
    public static void validateJsonSchema(Response response, String schemaFilePath) {
        logger.info("Validating JSON schema: {}", schemaFilePath);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaFilePath)));
    }
    
    /**
     * Validate header exists
     */
    public static void validateHeaderExists(Response response, String headerName) {
        String headerValue = response.getHeader(headerName);
        logger.info("Validating header exists: {}", headerName);
        Assert.assertNotNull(headerValue, "Header does not exist: " + headerName);
    }
    
    /**
     * Validate header value
     */
    public static void validateHeaderValue(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        logger.info("Validating header value. Header: {}, Expected: {}, Actual: {}", 
                headerName, expectedValue, actualValue);
        Assert.assertEquals(actualValue, expectedValue, 
                "Header value validation failed for: " + headerName);
    }
}
