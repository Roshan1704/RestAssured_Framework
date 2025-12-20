package com.api.automation.utils;

import com.api.automation.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for database operations
 */
public class DatabaseUtil {
    
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private Connection connection;
    private final ConfigManager config;
    
    public DatabaseUtil() {
        this.config = ConfigManager.getInstance();
    }
    
    /**
     * Connect to database
     */
    public void connect() {
        try {
            String url = config.getProperty("db.url");
            String username = config.getProperty("db.username");
            String password = config.getProperty("db.password");
            String driver = config.getProperty("db.driver");
            
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established");
            
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to connect to database", e);
            throw new RuntimeException("Database connection failed", e);
        }
    }
    
    /**
     * Disconnect from database
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed");
            }
        } catch (SQLException e) {
            logger.error("Failed to close database connection", e);
        }
    }
    
    /**
     * Execute SELECT query and return results
     */
    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                results.add(row);
            }
            
            logger.info("Query executed successfully. Rows returned: {}", results.size());
            
        } catch (SQLException e) {
            logger.error("Failed to execute query: {}", query, e);
            throw new RuntimeException("Query execution failed", e);
        }
        
        return results;
    }
    
    /**
     * Execute INSERT, UPDATE, DELETE queries
     */
    public int executeUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(query);
            logger.info("Update executed successfully. Rows affected: {}", rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            logger.error("Failed to execute update: {}", query, e);
            throw new RuntimeException("Update execution failed", e);
        }
    }
    
    /**
     * Execute prepared statement
     */
    public List<Map<String, Object>> executePreparedQuery(String query, Object... params) {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), resultSet.getObject(i));
                    }
                    results.add(row);
                }
            }
            
            logger.info("Prepared query executed successfully. Rows returned: {}", results.size());
            
        } catch (SQLException e) {
            logger.error("Failed to execute prepared query: {}", query, e);
            throw new RuntimeException("Prepared query execution failed", e);
        }
        
        return results;
    }
}
