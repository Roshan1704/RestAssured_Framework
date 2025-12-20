# Framework Usage Guide

## Quick Start Guide

### 1. Writing Your First Test

```java
package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.endpoints.Routes;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class MyFirstTest extends BaseTest {
    
    @Test
    public void testGetUsers() {
        Response response = apiClient.get(Routes.USERS);
        ResponseValidator.validateStatusCode(response, 200);
    }
}
```

### 2. Creating Request Payloads

```java
// Using payload builder
User user = UserPayload.createUser("John Doe", "Engineer");

// Using Lombok builder
User user = User.builder()
    .name("John Doe")
    .job("Engineer")
    .email("john@test.com")
    .build();
```

### 3. Making API Calls

```java
// GET request
Response response = apiClient.get("/users/1");

// POST request with body
Response response = apiClient
    .withBody(user)
    .post("/users");

// PUT request with headers
Response response = apiClient
    .withHeader("Authorization", "Bearer token")
    .withBody(user)
    .put("/users/1");

// DELETE request
Response response = apiClient.delete("/users/1");
```

### 4. Validating Responses

```java
// Status code validation
ResponseValidator.validateStatusCode(response, 200);

// Field validation
ResponseValidator.validateFieldExists(response, "data.id");
ResponseValidator.validateFieldValue(response, "data.name", "John");

// Response time validation
ResponseValidator.validateResponseTime(response, 3000);

// Schema validation
ResponseValidator.validateJsonSchema(response, "user-schema.json");
```

### 5. Parsing Responses

```java
// Parse to POJO
User user = JsonUtil.parseResponse(response, User.class);

// Parse to list
UserListResponse userList = JsonUtil.parseResponse(response, UserListResponse.class);

// Access data
String userName = user.getName();
String email = user.getEmail();
```

### 6. Adding Test Data

#### JSON File:
```java
List<User> users = JsonUtil.parseJsonArrayFile("src/test/resources/testdata/users.json", User.class);
```

#### Excel File:
```java
List<Map<String, String>> data = ExcelUtil.readExcelData("src/test/resources/testdata/testdata.xlsx", "UserData");
```

#### YAML File:
```java
Map<String, Object> config = YamlUtil.parseYamlFile("src/test/resources/testdata/config.yml");
```

### 7. Using Data Providers

```java
@Test(dataProvider = "jsonDataProvider", dataProviderClass = TestDataProvider.class)
public void testWithData(String name, String job) {
    User user = UserPayload.createUser(name, job);
    Response response = apiClient.withBody(user).post(Routes.USERS);
    ResponseValidator.validateStatusCode(response, 201);
}
```

### 8. Database Validation

```java
DatabaseUtil dbUtil = new DatabaseUtil();
dbUtil.connect();

// Verify user in database
List<Map<String, Object>> results = dbUtil.executeQuery(
    "SELECT * FROM users WHERE email = 'john@test.com'"
);
Assert.assertTrue(results.size() > 0, "User should exist in database");

dbUtil.disconnect();
```

### 9. Adding Allure Annotations

```java
@Epic("User Management")
@Feature("User Operations")
@Story("Create User")
@Severity(SeverityLevel.CRITICAL)
@Description("Test to verify user creation functionality")
@Test
public void testCreateUser() {
    // Test implementation
}
```

### 10. Logging to Reports

```java
// Log request
ExtentReportListener.logRequest("POST", "/users", userPayload);
AllureReportHelper.attachRequestDetails("POST", "/users", userPayload);

// Log response
ExtentReportListener.logResponse(response);
AllureReportHelper.attachResponseDetails(response);
```

## Advanced Usage

### Custom Headers

```java
Map<String, String> headers = new HashMap<>();
headers.put("Authorization", "Bearer token123");
headers.put("Content-Type", "application/json");

Response response = apiClient
    .withHeaders(headers)
    .get("/users");
```

### Query Parameters

```java
Response response = apiClient
    .withQueryParam("page", 2)
    .withQueryParam("per_page", 10)
    .get("/users");
```

### Authentication

```java
// Bearer token
Response response = apiClient
    .withAuth("your-token-here")
    .get("/protected-endpoint");

// Basic auth
Response response = apiClient
    .withBasicAuth("username", "password")
    .get("/protected-endpoint");
```

### Retry Failed Tests

```java
@Test(retryAnalyzer = RetryAnalyzer.class)
public void testWithRetry() {
    // This test will retry on failure
}
```

## Complete Test Example

```java
@Epic("User Management")
@Feature("User CRUD")
public class CompleteUserTest extends BaseTest {
    
    @Test(priority = 1, description = "Create and verify user")
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    public void testCompleteUserFlow() {
        // Step 1: Create user
        User newUser = UserPayload.createRandomUser();
        
        Response createResponse = apiClient
            .withBody(newUser)
            .post(Routes.USERS);
        
        ExtentReportListener.logRequest("POST", Routes.USERS, newUser);
        ExtentReportListener.logResponse(createResponse);
        AllureReportHelper.attachRequestDetails("POST", Routes.USERS, newUser);
        AllureReportHelper.attachResponseDetails(createResponse);
        
        ResponseValidator.validateStatusCode(createResponse, 201);
        ResponseValidator.validateFieldExists(createResponse, "id");
        
        User createdUser = JsonUtil.parseResponse(createResponse, User.class);
        String userId = createdUser.getId();
        
        logger.info("User created with ID: {}", userId);
        
        // Step 2: Get user
        Response getResponse = apiClient.get(Routes.getUserById(Integer.parseInt(userId)));
        
        ResponseValidator.validateStatusCode(getResponse, 200);
        
        // Step 3: Update user
        User updateUser = UserPayload.updateUser("Updated Name", "Updated Job");
        
        Response updateResponse = apiClient
            .withBody(updateUser)
            .put(Routes.getUserById(Integer.parseInt(userId)));
        
        ResponseValidator.validateStatusCode(updateResponse, 200);
        
        // Step 4: Delete user
        Response deleteResponse = apiClient.delete(Routes.getUserById(Integer.parseInt(userId)));
        
        ResponseValidator.validateStatusCode(deleteResponse, 204);
        
        logger.info("Complete user flow test passed");
    }
}
```

## Tips and Tricks

1. **Use BaseTest**: Always extend BaseTest for common setup
2. **Chain Methods**: Leverage fluent API for cleaner code
3. **Reuse Utilities**: Use provided utilities instead of writing custom code
4. **Log Everything**: Add logs for better debugging
5. **Validate Early**: Validate responses immediately after requests
6. **Use POJO**: Parse responses to POJO for type safety
7. **Externalize Data**: Keep test data in external files
8. **Add Annotations**: Use Allure annotations for better reports
9. **Handle Exceptions**: Use try-catch for better error handling
10. **Clean Code**: Follow Java best practices and naming conventions

## Common Patterns

### Pattern 1: GET and Validate

```java
Response response = apiClient.get("/users/1");
ResponseValidator.validateStatusCode(response, 200);
User user = JsonUtil.parseResponse(response, User.class);
Assert.assertNotNull(user.getEmail());
```

### Pattern 2: POST and Extract ID

```java
User newUser = UserPayload.createUser("John", "Engineer");
Response response = apiClient.withBody(newUser).post("/users");
String userId = response.jsonPath().getString("id");
```

### Pattern 3: Update and Verify

```java
User updateData = UserPayload.updateUser("New Name", "New Job");
Response response = apiClient.withBody(updateData).put("/users/1");
User updated = JsonUtil.parseResponse(response, User.class);
Assert.assertEquals(updated.getName(), "New Name");
```

### Pattern 4: Data-Driven Testing

```java
@Test(dataProvider = "jsonDataProvider", dataProviderClass = TestDataProvider.class)
public void testMultipleUsers(String name, String job) {
    User user = UserPayload.createUser(name, job);
    Response response = apiClient.withBody(user).post("/users");
    ResponseValidator.validateStatusCode(response, 201);
}
```

This framework provides everything you need for robust REST API testing. Happy Testing! 🚀
