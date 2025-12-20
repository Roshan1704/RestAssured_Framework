package com.api.automation.helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.response.Response;

/**
 * Helper class for Allure reporting
 */
public class AllureReportHelper {
    
    /**
     * Attach request details to Allure report
     */
    public static void attachRequestDetails(String method, String endpoint, Object body) {
        Allure.step("Request: " + method + " " + endpoint);
        if (body != null) {
            attachJson("Request Body", body.toString());
        }
    }
    
    /**
     * Attach response details to Allure report
     */
    public static void attachResponseDetails(Response response) {
        if (response != null) {
            Allure.step("Response Status: " + response.getStatusCode());
            Allure.step("Response Time: " + response.getTime() + "ms");
            attachJson("Response Body", response.asPrettyString());
        }
    }
    
    /**
     * Attach JSON to report
     */
    @Attachment(value = "{0}", type = "application/json")
    public static String attachJson(String name, String json) {
        return json;
    }
    
    /**
     * Attach text to report
     */
    @Attachment(value = "{0}", type = "text/plain")
    public static String attachText(String name, String text) {
        return text;
    }
}
