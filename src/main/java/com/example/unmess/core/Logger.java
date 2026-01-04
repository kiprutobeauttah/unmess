package com.example.unmess.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Centralized logging system for application-wide event tracking.
 * Thread-safe implementation with multiple log levels.
 * 
 * @author Photo Editor Engineering Team
 * @version 1.0.0
 */
public class Logger {
    
    public enum Level {
        DEBUG, INFO, WARNING, ERROR, CRITICAL
    }
    
    private static final DateTimeFormatter TIMESTAMP_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    private static Level currentLevel = Level.INFO;
    private static boolean enabled = true;
    
    /**
     * Set the minimum logging level
     */
    public static void setLevel(Level level) {
        currentLevel = level;
    }
    
    /**
     * Enable or disable logging
     */
    public static void setEnabled(boolean enable) {
        enabled = enable;
    }
    
    public static void debug(String message) {
        log(Level.DEBUG, message, null);
    }
    
    public static void info(String message) {
        log(Level.INFO, message, null);
    }
    
    public static void warning(String message) {
        log(Level.WARNING, message, null);
    }
    
    public static void error(String message) {
        log(Level.ERROR, message, null);
    }
    
    public static void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }
    
    public static void critical(String message) {
        log(Level.CRITICAL, message, null);
    }
    
    public static void critical(String message, Throwable throwable) {
        log(Level.CRITICAL, message, throwable);
    }
    
    private static synchronized void log(Level level, String message, Throwable throwable) {
        if (!enabled || level.ordinal() < currentLevel.ordinal()) {
            return;
        }
        
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String threadName = Thread.currentThread().getName();
        String logMessage = String.format("[%s] [%s] [%s] %s", 
            timestamp, level, threadName, message);
        
        if (level.ordinal() >= Level.ERROR.ordinal()) {
            System.err.println(logMessage);
            if (throwable != null) {
                throwable.printStackTrace(System.err);
            }
        } else {
            System.out.println(logMessage);
        }
    }
}
