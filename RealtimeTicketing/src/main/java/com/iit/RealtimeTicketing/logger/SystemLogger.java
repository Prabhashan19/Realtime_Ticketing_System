package com.iit.RealtimeTicketing.logger;


import java.util.logging.*;
import com.iit.RealtimeTicketing.CLI.Ticket;

public class SystemLogger {
    private static final Logger logger = Logger.getLogger(SystemLogger.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("system.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (Exception e) {
            System.out.println("Failed to set up logger: " + e.getMessage());
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
    // New method to log ticket info
    public static void logTicketInfo(Ticket ticket) {
        if (ticket != null) {
            logger.info("Ticket Info: " + ticket.toString());
        } else {
            logger.warning("Attempted to log a null Ticket object.");
        }
    }
}
