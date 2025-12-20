package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.dataproviders.TestDataProvider;
import com.api.automation.endpoints.Routes;
import com.api.automation.helpers.AllureReportHelper;
import com.api.automation.listeners.ExtentReportListener;
import com.api.automation.models.User;
import com.api.automation.payloads.UserPayload;
import com.api.automation.utils.ResponseValidator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Test class demonstrating data-driven testing with multiple data sources
 */
@Epic("Data Driven Testing")
@Feature("Parameterized Tests")
public class DataDrivenTests extends BaseTest {
    
    @Test(priority = 1, 
          dataProvider = "jsonDataProvider", 
          dataProviderClass = TestDataProvider.class,
          description = "Create users from JSON data")
    @Story("JSON Data Provider")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateUserFromJson(String name, String job) {
        logger.info("Starting testCreateUserFromJson with name: {}, job: {}", name, job);
        
        User newUser = UserPayload.createUser(name, job);
        
        Response response = apiClient
                .withBody(newUser)
                .post(Routes.USERS);
        
        ExtentReportListener.logRequest("POST", Routes.USERS, newUser);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("POST", Routes.USERS, newUser);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 201);
        ResponseValidator.validateFieldExists(response, "id");
        
        logger.info("User created successfully from JSON data");
    }
    
    @Test(priority = 2,
          dataProvider = "inlineDataProvider",
          dataProviderClass = TestDataProvider.class,
          description = "Create users from inline data")
    @Story("Inline Data Provider")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateUserFromInlineData(String name, String job) {
        logger.info("Starting testCreateUserFromInlineData with name: {}, job: {}", name, job);
        
        User newUser = UserPayload.createUser(name, job);
        
        Response response = apiClient
                .withBody(newUser)
                .post(Routes.USERS);
        
        ExtentReportListener.logRequest("POST", Routes.USERS, newUser);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("POST", Routes.USERS, newUser);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 201);
        ResponseValidator.validateFieldExists(response, "id");
        
        logger.info("User created successfully from inline data");
    }
}
