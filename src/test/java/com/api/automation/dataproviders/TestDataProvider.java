package com.api.automation.dataproviders;

import com.api.automation.config.ConfigManager;
import com.api.automation.models.User;
import com.api.automation.utils.ExcelUtil;
import com.api.automation.utils.JsonUtil;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

/**
 * TestNG DataProvider class for providing test data from multiple sources
 */
public class TestDataProvider {
    
    private static final ConfigManager config = ConfigManager.getInstance();
    
    /**
     * Data provider for JSON test data
     */
    @DataProvider(name = "jsonDataProvider")
    public static Object[][] getJsonTestData() {
        String jsonFilePath = config.getProperty("test.data.json.path");
        List<User> users = JsonUtil.parseJsonArrayFile(jsonFilePath, User.class);
        
        Object[][] data = new Object[users.size()][2];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i).getName();
            data[i][1] = users.get(i).getJob();
        }
        
        return data;
    }
    
    /**
     * Data provider for Excel test data
     */
    @DataProvider(name = "excelDataProvider")
    public static Object[][] getExcelTestData() {
        String excelFilePath = config.getProperty("test.data.excel.path");
        List<Map<String, String>> data = ExcelUtil.readExcelData(excelFilePath, "UserData");
        
        Object[][] testData = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> row = data.get(i);
            testData[i][0] = row.get("name");
            testData[i][1] = row.get("job");
        }
        
        return testData;
    }
    
    /**
     * Data provider for inline test data
     */
    @DataProvider(name = "inlineDataProvider")
    public static Object[][] getInlineTestData() {
        return new Object[][] {
            {"John Doe", "Software Engineer"},
            {"Jane Smith", "QA Engineer"},
            {"Bob Johnson", "DevOps Engineer"}
        };
    }
}
