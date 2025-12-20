package com.api.automation.endpoints;

/**
 * API endpoint constants
 */
public class Routes {
    
    // Base endpoints
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{id}";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    
    // Resource endpoints
    public static final String RESOURCES = "/unknown";
    public static final String RESOURCE_BY_ID = "/unknown/{id}";
    
    // Utility method to build endpoint with ID
    public static String getUserById(int id) {
        return USERS + "/" + id;
    }
    
    public static String getResourceById(int id) {
        return RESOURCES + "/" + id;
    }
}
