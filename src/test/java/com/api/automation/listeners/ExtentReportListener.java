package com.api.automation.listeners;

import com.api.automation.utils.DateTimeUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * ExtentReports listener for test reporting
 */
public class ExtentReportListener implements ITestListener {
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    
    @Override
    public void onStart(ITestContext context) {
        String reportPath = "extent-reports/ExtentReport_" + DateTimeUtil.getTimestamp() + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        
        sparkReporter.config().setDocumentTitle("API Test Automation Report");
        sparkReporter.config().setReportName("RestAssured Framework Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        extent.setSystemInfo("Framework", "RestAssured");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Automation Team");
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getTestClass().getName());
        extentTest.set(test);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        extentTest.get().log(Status.FAIL, result.getThrowable());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
        extentTest.get().log(Status.SKIP, result.getThrowable());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
    
    /**
     * Log request details to report
     */
    public static void logRequest(String method, String endpoint, Object body) {
        if (extentTest.get() != null) {
            extentTest.get().info("Request Method: " + method);
            extentTest.get().info("Request Endpoint: " + endpoint);
            if (body != null) {
                extentTest.get().info(MarkupHelper.createCodeBlock(body.toString(), CodeLanguage.JSON));
            }
        }
    }
    
    /**
     * Log response details to report
     */
    public static void logResponse(Response response) {
        if (extentTest.get() != null && response != null) {
            extentTest.get().info("Response Status Code: " + response.getStatusCode());
            extentTest.get().info("Response Time: " + response.getTime() + "ms");
            extentTest.get().info(MarkupHelper.createCodeBlock(response.asPrettyString(), CodeLanguage.JSON));
        }
    }
}
