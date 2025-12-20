package com.api.automation.client;

import com.api.automation.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Fluent API client for building and executing HTTP requests
 */
public class ApiClient {
    
    private static final Logger logger = LogManager.getLogger(ApiClient.class);
    private RequestSpecification requestSpec;
    private Map<String, String> headers;
    private Map<String, Object> queryParams;
    private Object requestBody;
    
    public ApiClient() {
        this.requestSpec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        this.headers = new HashMap<>();
        this.queryParams = new HashMap<>();
    }
    
    /**
     * Add a header to the request
     */
    public ApiClient withHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }
    
    /**
     * Add multiple headers to the request
     */
    public ApiClient withHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }
    
    /**
     * Add a query parameter to the request
     */
    public ApiClient withQueryParam(String key, Object value) {
        queryParams.put(key, value);
        return this;
    }
    
    /**
     * Add multiple query parameters to the request
     */
    public ApiClient withQueryParams(Map<String, Object> queryParams) {
        this.queryParams.putAll(queryParams);
        return this;
    }
    
    /**
     * Set the request body
     */
    public ApiClient withBody(Object body) {
        this.requestBody = body;
        return this;
    }
    
    /**
     * Set authentication token
     */
    public ApiClient withAuth(String token) {
        headers.put("Authorization", "Bearer " + token);
        return this;
    }
    
    /**
     * Set basic authentication
     */
    public ApiClient withBasicAuth(String username, String password) {
        requestSpec.auth().preemptive().basic(username, password);
        return this;
    }
    
    /**
     * Build the request with all configurations
     */
    private RequestSpecification buildRequest() {
        if (!headers.isEmpty()) {
            requestSpec.headers(headers);
        }
        if (!queryParams.isEmpty()) {
            requestSpec.queryParams(queryParams);
        }
        if (requestBody != null) {
            requestSpec.body(requestBody);
        }
        return requestSpec;
    }
    
    /**
     * Execute GET request
     */
    public Response get(String endpoint) {
        logger.info("Executing GET request to: {}", endpoint);
        return buildRequest().get(endpoint);
    }
    
    /**
     * Execute POST request
     */
    public Response post(String endpoint) {
        logger.info("Executing POST request to: {}", endpoint);
        return buildRequest().post(endpoint);
    }
    
    /**
     * Execute PUT request
     */
    public Response put(String endpoint) {
        logger.info("Executing PUT request to: {}", endpoint);
        return buildRequest().put(endpoint);
    }
    
    /**
     * Execute PATCH request
     */
    public Response patch(String endpoint) {
        logger.info("Executing PATCH request to: {}", endpoint);
        return buildRequest().patch(endpoint);
    }
    
    /**
     * Execute DELETE request
     */
    public Response delete(String endpoint) {
        logger.info("Executing DELETE request to: {}", endpoint);
        return buildRequest().delete(endpoint);
    }
}
