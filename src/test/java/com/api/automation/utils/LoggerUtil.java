package com.api.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logger utility for consistent logging across framework
 */
public class LoggerUtil {
    
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
    
    public static void logInfo(Logger logger, String message, Object... params) {
        logger.info(message, params);
    }
    
    public static void logDebug(Logger logger, String message, Object... params) {
        logger.debug(message, params);
    }
    
    public static void logError(Logger logger, String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    public static void logWarn(Logger logger, String message, Object... params) {
        logger.warn(message, params);
    }
}
