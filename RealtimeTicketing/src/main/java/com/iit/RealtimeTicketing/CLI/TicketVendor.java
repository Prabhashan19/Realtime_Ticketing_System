package com.iit.RealtimeTicketing.CLI;

import java.math.BigDecimal;

public class TicketVendor implements Runnable {
    private final TicketPool ticketPool; // The TicketPool which is shared among Vendors and Customers
    private final int totalTickets; // Total number of tickets, A Ticket vendor will sell
    private final int ticketReleaseRate; // Frequency of tickets will be added to the pool

    public TicketVendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    // Implement the thread
    // Runnable interface should write the implementation for Runnable interface
    @Override
    public void run(){
        for (int i = 1; i <= totalTickets; i++) { //  is used an id
            Ticket ticket = new Ticket(i, "Sample Event", new BigDecimal(1000));
            ticketPool.addTickets(ticket); // Method in Ticket pool to add tickets

            // The ticket release frequency means the delay
            // We should convert the value in S to ms
            try {
                Thread.sleep(ticketReleaseRate * 1000L);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}