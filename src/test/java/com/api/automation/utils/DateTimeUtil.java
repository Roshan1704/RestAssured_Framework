package com.api.automation.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for date and time operations
 */
public class DateTimeUtil {
    
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    
    /**
     * Get current date as string
     */
    public static String getCurrentDate() {
        return getCurrentDate(DEFAULT_DATE_FORMAT);
    }
    
    /**
     * Get current date with custom format
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
    
    /**
     * Get current date time as string
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(DEFAULT_DATETIME_FORMAT);
    }
    
    /**
     * Get current date time with custom format
     */
    public static String getCurrentDateTime(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }
    
    /**
     * Get timestamp for file naming
     */
    public static String getTimestamp() {
        return getCurrentDateTime(TIMESTAMP_FORMAT);
    }
}
