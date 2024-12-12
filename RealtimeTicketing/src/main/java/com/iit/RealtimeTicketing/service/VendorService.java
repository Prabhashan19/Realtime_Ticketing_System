package com.iit.RealtimeTicketing.service;


import com.iit.RealtimeTicketing.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VendorService implements Runnable{
    @Autowired
    private TicketPoolService ticketPool;

    private int ticketsToProduce;

//    public VendorService(int ticketsToProduce) {
//        this.ticketsToProduce = ticketsToProduce;
//    }

    @Override
    public void run() {
        for (int i = 0; i < ticketsToProduce; i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketName("Ticket-" + System.currentTimeMillis());
            ticket.setSold(false);
            ticketPool.addTicket(ticket);
            try {
                Thread.sleep(100); // Simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
