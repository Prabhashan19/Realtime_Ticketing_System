package com.iit.CLI;

public class Customer implements Runnable {
    private TicketPool ticketPool; //This is shared between Vendors and Customers
    private int customerRetrievalRate; // Frequency which tickets will be removed from the pool
    private int quantity; // Quantity customer willing to purchase

    public Customer(TicketPool ticket, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticket;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = ticketPool.buyTicket(); // Remove ticket from the pool
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