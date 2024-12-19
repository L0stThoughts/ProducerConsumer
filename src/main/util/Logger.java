package util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A custom logger for the Producer-Consumer simulation.
 * This logger writes logs to both the console and a file.
 * It supports different log levels (INFO, ERROR, etc.) and custom log formatting.
 */
public class Logger {

    // Log file path
    private static final String LOG_FILE_PATH = "app.log";
    
    // Enumeration for log levels
    public enum LogLevel {
        INFO,
        ERROR,
        WARN,
        DEBUG
    }

    // Default log level
    private static LogLevel currentLogLevel = LogLevel.INFO;

    /**
     * Sets the global log level for the logger.
     * This method allows controlling which log levels to show.
     *
     * @param logLevel the log level to set.
     */
    public static void setLogLevel(LogLevel logLevel) {
        currentLogLevel = logLevel;
    }

    /**
     * Logs a message with a specific log level to both the console and the log file.
     *
     * @param level the log level (INFO, ERROR, etc.).
     * @param message the message to log.
     */
    public static void log(LogLevel level, String message) {
        // Check if the current log level allows this level to be logged
        if (shouldLog(level)) {
            String timestamp = getCurrentTimestamp();
            String logMessage = String.format("[%s] [%s] %s", timestamp, level, message);
            
            // Print to console
            System.out.println(logMessage);
            
            // Print to log file
            writeToFile(logMessage);
        }
    }

    /**
     * Checks if a message with the given log level should be logged
     * based on the current log level setting.
     *
     * @param level the log level to check.
     * @return true if the message should be logged; false otherwise.
     */
    private static boolean shouldLog(LogLevel level) {
        switch (currentLogLevel) {
            case DEBUG:
                return true;  // Log all levels
            case INFO:
                return level != LogLevel.DEBUG;  // Only INFO, WARN, and ERROR
            case WARN:
                return level == LogLevel.WARN || level == LogLevel.ERROR;  // Only WARN and ERROR
            case ERROR:
                return level == LogLevel.ERROR;  // Only ERROR
            default:
                return false;
        }
    }

    /**
     * Gets the current timestamp in a readable format (e.g., "2024-12-19 14:30:15").
     *
     * @return the current timestamp.
     */
    private static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * Writes the log message to a log file.
     *
     * @param logMessage the message to write to the log file.
     */
    private static void writeToFile(String logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    /**
     * Logs an informational message.
     *
     * @param message the message to log.
     */
    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Logs an error message.
     *
     * @param message the message to log.
     */
    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Logs a warning message.
     *
     * @param message the message to log.
     */
    public static void warn(String message) {
        log(LogLevel.WARN, message);
    }

    /**
     * Logs a debug message.
     *
     * @param message the message to log.
     */
    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
}
