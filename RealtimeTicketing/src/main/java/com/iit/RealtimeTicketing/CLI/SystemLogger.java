package com.iit.RealtimeTicketing.CLI;

import java.util.logging.*;

public class SystemLogger {
    private static final Logger logger = Logger.getLogger(SystemLogger.class.getName());

    static {
        try {
            // Set up the logger
            FileHandler fileHandler = new FileHandler("system.log", true); // Log to a file
            fileHandler.setFormatter(new SimpleFormatter()); // Use simple format
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); // Log all levels
        } catch (Exception e) {
            System.out.println("Failed to set up logger file: " + e.getMessage());
        }
    }
    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warning(message);
    }

    public static void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}