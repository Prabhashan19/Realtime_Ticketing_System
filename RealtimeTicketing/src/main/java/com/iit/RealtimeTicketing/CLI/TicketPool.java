package com.iit.RealtimeTicketing.CLI;

import java.util.*;

public class TicketPool {
//    private static List<Ticket> ticketList;
    private final int maximumCapacity;
//    private final TicketList ticketList;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
//        this.ticketList = ticketList;
//        ticketList = Collections.synchronizedList(new ArrayList<>()); // Synchronized List For tickets
        SystemLogger.logInfo("TicketPool initialized with maximum ticket capacity: " + maximumCapacity);
    }

    // Add Ticket method used by the vendors to add tickets
    public synchronized void addTickets(Ticket ticket) {
        try {
            while (TicketList.getTotalTickets() >= maximumCapacity) {
                SystemLogger.logWarning(Thread.currentThread().getName() +
                        " is waiting to add a ticket because the ticket pool is full. Current size: " + TicketList.getTotalTickets());
                wait();
            }

            TicketList.addTicket(ticket); // Add the ticket to the pool
            SystemLogger.logInfo(Thread.currentThread().getName() +
                    " has added a ticket to the ticket pool. Current size: " + TicketList.getTotalTickets());
            notifyAll(); // Notify all waiting threads
        } catch (InterruptedException e) {
            SystemLogger.logError("Thread interrupted while adding tickets", e);
            Thread.currentThread().interrupt(); // Restore the interrupt status
        } catch (Exception e) {
            SystemLogger.logError("Unexpected error while adding tickets", e);
            throw new RuntimeException("Failed to add ticket: " + e.getMessage());
        }
    }

    // Buy Ticket method used by customers (consumers)
    public synchronized Ticket removeTicket() {
        try {
            while (TicketList.getAvailableTickets() == 0) {
                SystemLogger.logWarning(Thread.currentThread().getName() +
                        " is waiting to buy a ticket because the pool is empty.");
                wait();
            }

            Ticket ticket = TicketList.removeTicket(); // Remove ticket from the front of the list
            SystemLogger.logInfo(Thread.currentThread().getName() +
                    " has bought a ticket from the ticket pool. Current size: " + TicketList.getTotalTickets());
            notifyAll(); // Notify all waiting threads
            return ticket;
        } catch (InterruptedException e) {
            SystemLogger.logError("Thread interrupted while buying tickets", e);
            Thread.currentThread().interrupt(); // Restore the interrupt status
            return null; // Return null to indicate failure
        } catch (Exception e) {
            SystemLogger.logError("Unexpected error while buying tickets", e);
            throw new RuntimeException("Failed to buy ticket: " + e.getMessage());
        }
    }
}