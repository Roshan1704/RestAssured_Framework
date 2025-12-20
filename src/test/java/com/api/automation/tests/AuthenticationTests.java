package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.endpoints.Routes;
import com.api.automation.helpers.AllureReportHelper;
import com.api.automation.listeners.ExtentReportListener;
import com.api.automation.models.AuthRequest;
import com.api.automation.models.AuthResponse;
import com.api.automation.utils.JsonUtil;
import com.api.automation.utils.ResponseValidator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Authentication operations
 */
@Epic("Authentication")
@Feature("User Authentication")
public class AuthenticationTests extends BaseTest {
    
    @Test(priority = 1, description = "Verify successful user registration")
    @Story("User Registration")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessfulRegistration() {
        logger.info("Starting testSuccessfulRegistration");
        
        AuthRequest authRequest = AuthRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        
        Response response = apiClient
                .withBody(authRequest)
                .post(Routes.REGISTER);
        
        ExtentReportListener.logRequest("POST", Routes.REGISTER, authRequest);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("POST", Routes.REGISTER, authRequest);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateFieldExists(response, "id");
        ResponseValidator.validateFieldExists(response, "token");
        
        AuthResponse authResponse = JsonUtil.parseResponse(response, AuthResponse.class);
        Assert.assertNotNull(authResponse.getToken(), "Token should not be null");
        Assert.assertNotNull(authResponse.getId(), "User ID should not be null");
        
        logger.info("Registration successful. User ID: {}, Token: {}", 
                authResponse.getId(), authResponse.getToken());
    }
    
    @Test(priority = 2, description = "Verify registration with missing password")
    @Story("User Registration")
    @Severity(SeverityLevel.NORMAL)
    public void testRegistrationWithMissingPassword() {
        logger.info("Starting testRegistrationWithMissingPassword");
        
        AuthRequest authRequest = AuthRequest.builder()
                .email("sydney@fife")
                .build();
        
        Response response = apiClient
                .withBody(authRequest)
                .post(Routes.REGISTER);
        
        ExtentReportListener.logRequest("POST", Routes.REGISTER, authRequest);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("POST", Routes.REGISTER, authRequest);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 400);
        ResponseValidator.validateFieldExists(response, "error");
        
        AuthResponse authResponse = JsonUtil.parseResponse(response, AuthResponse.class);
        Assert.assertNotNull(authResponse.getError(), "Error message should be present");
        Assert.assertEquals(authResponse.getError(), "Missing password", "Error message should match");
        
        logger.info("Registration failed as expected with error: {}", authResponse.getError());
    }
    
    @Test(priority = 3, description = "Verify successful user login")
    @Story("User Login")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessfulLogin() {
        logger.info("Starting testSuccessfulLogin");
        
        AuthRequest authRequest = AuthRequest.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        
        Response response = apiClient
                .withBody(authRequest)
                .post(Routes.LOGIN);
        
        ExtentReportListener.logRequest("POST", Routes.LOGIN, authRequest);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("POST", Routes.LOGIN, authRequest);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateFieldExists(response, "token");
        
        AuthResponse authResponse = JsonUtil.parseResponse(response, AuthResponse.class);
        Assert.assertNotNull(authResponse.getToken(), "Token should not be null");
        
        logger.info("Login successful. Token: {}", authResponse.getToken());
    }
    
    @Test(priority = 4, description = "Verify login with missing credentials")
    @Story("User Login")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithMissingCredentials() {
        logger.info("Starting testLoginWithMissingCredentials");
        
        AuthRequest authRequest = AuthRequest.builder()
                .email("peter@klaven")
                .build();
        
        Response response = apiClient
                .withBody(authRequest)
                .post(Routes.LOGIN);
        
        ExtentReportListener.logRequest("POST", Routes.LOGIN, authRequest);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("POST", Routes.LOGIN, authRequest);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 400);
        ResponseValidator.validateFieldExists(response, "error");
        
        AuthResponse authResponse = JsonUtil.parseResponse(response, AuthResponse.class);
        Assert.assertNotNull(authResponse.getError(), "Error message should be present");
        
        logger.info("Login failed as expected with error: {}", authResponse.getError());
    }
}
