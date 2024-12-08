package com.iit.CLI;

import java.util.*;

public class TicketPool {
    private static List<Ticket> ticketList;
    private int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        this.ticketList = Collections.synchronizedList(new ArrayList<>()); // Synchronized List For tickets
    }
    //Add Ticket method which is used by the vendors to addTickets
    public synchronized void addTickets(Ticket ticket) {
        while (ticketList.size() >= maximumCapacity) {
            try{
                wait();
            }
            catch (InterruptedException e) {
                //Immediately after try block the catch or finally block should come
                e.printStackTrace(); // We can use this for CLI
                throw new RuntimeException(e.getMessage()); // If it's Client Server application the error should be thrown to client
            }
        }
        this.ticketList.add(ticket); // Adding the ticket to the Queue
        notifyAll(); // Notify all the waiting threads
        // Print the message to show the thread name who added and the current size of the pool
        System.out.println(Thread.currentThread().getName() + " has added a ticket to the pool. Current size is " + ticketList.size());
    }

    // Buy Ticket method is used by customer when buying Tickets (THIS WILL BE CALLED BY CUSTOMER (CONSUMER))
    public synchronized Ticket buyTicket() {
        while (ticketList.isEmpty()) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        Ticket ticket = ticketList.remove(0); // Remove ticket from List (Front)
        notifyAll(); // Notify all waiting threads
        System.out.println(Thread.currentThread().getName() + " has bought a ticket from the pool. Current size is " + ticketList.size());
        return ticket;
    }
}