package com.api.automation.listeners;

import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Allure listener for test reporting
 */
public class AllureListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        // Test started
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        // Test passed
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        saveTextLog("Test failed: " + result.getThrowable().getMessage());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        saveTextLog("Test skipped: " + result.getThrowable().getMessage());
    }
    
    @Override
    public void onStart(ITestContext context) {
        // Test suite started
    }
    
    @Override
    public void onFinish(ITestContext context) {
        // Test suite finished
    }
    
    @Attachment(value = "Test Log", type = "text/plain")
    public String saveTextLog(String message) {
        return message;
    }
}
