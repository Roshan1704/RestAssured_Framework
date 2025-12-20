package com.api.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for Excel operations
 */
public class ExcelUtil {
    
    private static final Logger logger = LogManager.getLogger(ExcelUtil.class);
    
    /**
     * Read data from Excel and return as List of Maps
     */
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                logger.error("Sheet not found: {}", sheetName);
                return data;
            }
            
            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < columnCount; j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell cell = row.getCell(j);
                    
                    String header = getCellValueAsString(headerCell);
                    String value = getCellValueAsString(cell);
                    
                    rowData.put(header, value);
                }
                data.add(rowData);
            }
            
            logger.info("Successfully read {} rows from Excel sheet: {}", data.size(), sheetName);
            
        } catch (IOException e) {
            logger.error("Failed to read Excel file: {}", filePath, e);
            throw new RuntimeException("Excel read failed", e);
        }
        
        return data;
    }
    
    /**
     * Get cell value as string
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * Read data from Excel as 2D Object array for TestNG DataProvider
     */
    public static Object[][] readExcelDataAsArray(String filePath, String sheetName) {
        List<Map<String, String>> dataList = readExcelData(filePath, sheetName);
        
        if (dataList.isEmpty()) {
            return new Object[0][0];
        }
        
        Object[][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = new Object[]{dataList.get(i)};
        }
        
        return data;
    }
}
