package com.api.automation.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Custom TestNG listener for logging test execution
 */
public class TestListener implements ITestListener {
    
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("========== Starting Test: {} ==========", result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("========== Test Passed: {} ==========", result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("========== Test Failed: {} ==========", result.getMethod().getMethodName());
        logger.error("Failure Reason: {}", result.getThrowable().getMessage());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("========== Test Skipped: {} ==========", result.getMethod().getMethodName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("========== Test Suite Started: {} ==========", context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("========== Test Suite Finished: {} ==========", context.getName());
        logger.info("Total Tests: {}", context.getAllTestMethods().length);
        logger.info("Passed: {}", context.getPassedTests().size());
        logger.info("Failed: {}", context.getFailedTests().size());
        logger.info("Skipped: {}", context.getSkippedTests().size());
    }
}
