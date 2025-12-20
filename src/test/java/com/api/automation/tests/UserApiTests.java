package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.endpoints.Routes;
import com.api.automation.helpers.AllureReportHelper;
import com.api.automation.listeners.ExtentReportListener;
import com.api.automation.models.User;
import com.api.automation.models.UserListResponse;
import com.api.automation.models.UserResponse;
import com.api.automation.payloads.UserPayload;
import com.api.automation.utils.JsonUtil;
import com.api.automation.utils.ResponseValidator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for User API operations
 */
@Epic("User Management")
@Feature("User CRUD Operations")
public class UserApiTests extends BaseTest {
    
    @Test(priority = 1, description = "Verify getting list of users")
    @Story("Get Users List")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify retrieving paginated list of users")
    public void testGetUsersList() {
        logger.info("Starting testGetUsersList");
        
        Response response = apiClient
                .withQueryParam("page", 2)
                .get(Routes.USERS);
        
        ExtentReportListener.logRequest("GET", Routes.USERS, null);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseTime(response, 3000);
        ResponseValidator.validateFieldExists(response, "data");
        
        UserListResponse userListResponse = JsonUtil.parseResponse(response, UserListResponse.class);
        Assert.assertNotNull(userListResponse.getData(), "User list should not be null");
        Assert.assertTrue(userListResponse.getData().size() > 0, "User list should not be empty");
        
        logger.info("Test completed successfully. Users count: {}", userListResponse.getData().size());
    }
    
    @Test(priority = 2, description = "Verify getting single user by ID")
    @Story("Get User By ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetSingleUser() {
        logger.info("Starting testGetSingleUser");
        
        int userId = 2;
        Response response = apiClient.get(Routes.getUserById(userId));
        
        ExtentReportListener.logRequest("GET", Routes.getUserById(userId), null);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateFieldExists(response, "data.id");
        ResponseValidator.validateFieldValue(response, "data.id", userId);
        
        UserResponse userResponse = JsonUtil.parseResponse(response, UserResponse.class);
        Assert.assertNotNull(userResponse.getData(), "User data should not be null");
        Assert.assertEquals(userResponse.getData().getId(), String.valueOf(userId), "User ID should match");
        
        logger.info("User retrieved: {}", userResponse.getData().getEmail());
    }
    
    @Test(priority = 3, description = "Verify creating a new user")
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUser() {
        logger.info("Starting testCreateUser");
        
        User newUser = UserPayload.createUser("John Doe", "QA Engineer");
        
        Response response = apiClient
                .withBody(newUser)
                .post(Routes.USERS);
        
        ExtentReportListener.logRequest("POST", Routes.USERS, newUser);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("POST", Routes.USERS, newUser);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 201);
        ResponseValidator.validateFieldExists(response, "id");
        ResponseValidator.validateFieldExists(response, "createdAt");
        
        User createdUser = JsonUtil.parseResponse(response, User.class);
        Assert.assertNotNull(createdUser.getId(), "Created user should have an ID");
        Assert.assertEquals(createdUser.getName(), newUser.getName(), "Name should match");
        
        logger.info("User created successfully with ID: {}", createdUser.getId());
    }
    
    @Test(priority = 4, description = "Verify updating an existing user")
    @Story("Update User")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUser() {
        logger.info("Starting testUpdateUser");
        
        int userId = 2;
        User updateUser = UserPayload.updateUser("Jane Smith", "Senior QA Engineer");
        
        Response response = apiClient
                .withBody(updateUser)
                .put(Routes.getUserById(userId));
        
        ExtentReportListener.logRequest("PUT", Routes.getUserById(userId), updateUser);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachRequestDetails("PUT", Routes.getUserById(userId), updateUser);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateFieldExists(response, "updatedAt");
        
        User updatedUser = JsonUtil.parseResponse(response, User.class);
        Assert.assertEquals(updatedUser.getName(), updateUser.getName(), "Updated name should match");
        
        logger.info("User updated successfully");
    }
    
    @Test(priority = 5, description = "Verify deleting a user")
    @Story("Delete User")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteUser() {
        logger.info("Starting testDeleteUser");
        
        int userId = 2;
        Response response = apiClient.delete(Routes.getUserById(userId));
        
        ExtentReportListener.logRequest("DELETE", Routes.getUserById(userId), null);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 204);
        
        logger.info("User deleted successfully");
    }
    
    @Test(priority = 6, description = "Verify user not found scenario")
    @Story("Get User By ID")
    @Severity(SeverityLevel.MINOR)
    public void testUserNotFound() {
        logger.info("Starting testUserNotFound");
        
        int invalidUserId = 999;
        Response response = apiClient.get(Routes.getUserById(invalidUserId));
        
        ExtentReportListener.logRequest("GET", Routes.getUserById(invalidUserId), null);
        ExtentReportListener.logResponse(response);
        AllureReportHelper.attachResponseDetails(response);
        
        ResponseValidator.validateStatusCode(response, 404);
        
        logger.info("User not found test completed successfully");
    }
}
