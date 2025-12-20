package com.api.automation.listeners;

import com.api.automation.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer for failed tests
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    
    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private int maxRetryCount;
    
    public RetryAnalyzer() {
        ConfigManager config = ConfigManager.getInstance();
        this.maxRetryCount = config.getIntProperty("retry.count");
    }
    
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.warn("Retrying test: {} - Attempt {}", result.getMethod().getMethodName(), retryCount);
            return true;
        }
        return false;
    }
}
