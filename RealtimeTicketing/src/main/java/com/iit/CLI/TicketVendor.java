package com.iit.CLI;

import java.math.BigDecimal;

public class TicketVendor implements Runnable {
    private TicketPool ticketPool; // The TicketPool which is shared among Vendors and Customers
    private int totalTickets; // Total number of tickets, A Ticket vendor will sell
    private int ticketReleaseRate; // Frequency of tickets will be added to the pool

    public TicketVendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    // Implement the thread
    // Runnable interface should write the implementation for Runnable interface
    @Override
    public void run(){
        for (int i = 1; i <= totalTickets; i++) { // i is used an an id
            Ticket ticket = new Ticket(i, "Sample Event", new BigDecimal(1000));
            ticketPool.addTickets(ticket); // Method in Ticket pool to add tickets

            // The ticket release frequency means the delay
            // We should convert the value in S to ms
            try {
                Thread.sleep(ticketReleaseRate * 1000);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}