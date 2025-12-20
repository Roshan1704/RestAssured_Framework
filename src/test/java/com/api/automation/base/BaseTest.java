package com.api.automation.base;

import com.api.automation.client.ApiClient;
import com.api.automation.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

/**
 * Base test class providing common setup and configuration for all API tests
 */
public class BaseTest {
    
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected ApiClient apiClient;
    protected ConfigManager config;
    
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    
    @BeforeClass(alwaysRun = true)
    public void setupClass() {
        config = ConfigManager.getInstance();
        
        // Set base URI and path
        RestAssured.baseURI = config.getProperty("base.uri");
        RestAssured.basePath = config.getProperty("base.path");
        
        // Configure request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        
        // Configure response specification
        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
        
        logger.info("Base URI: {}", RestAssured.baseURI);
        logger.info("Base Path: {}", RestAssured.basePath);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupMethod(Method method) {
        apiClient = new ApiClient();
        logger.info("Starting test: {}", method.getName());
    }
}
