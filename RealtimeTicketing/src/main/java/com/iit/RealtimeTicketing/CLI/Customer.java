package com.iit.RealtimeTicketing.CLI;

public class Customer implements Runnable {
    private final TicketPool ticketPool; //This is shared between Vendors and Customers
    private final int totalTickets; // Quantity customer willing to purchase
    private final int customerRetrievalRate; // Frequency which tickets will be removed from the pool

    public Customer(TicketPool ticket, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticket;
        this.totalTickets = quantity;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        for (int i = 0; i < totalTickets; i++) {
            Ticket ticket = ticketPool.removeTicket(); // Remove ticket from the pool
            // Printing details of bought tickets

            // Delay which the ticket will be removed
            try {
                Thread.sleep(customerRetrievalRate * 1000);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}